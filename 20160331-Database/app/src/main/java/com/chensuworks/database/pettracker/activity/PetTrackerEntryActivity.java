package com.chensuworks.database.pettracker.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.chensuworks.database.R;

import com.chensuworks.database.pettracker.database.PetTrackerDatabase.PetType;
import com.chensuworks.database.pettracker.database.PetTrackerDatabase.Pets;

import java.util.Locale;

public class PetTrackerEntryActivity extends PetTrackerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_tracker_entry);

        fillAutoCompleteFromDatabase();

        final Button buttonSave = (Button) findViewById(R.id.button_save);
        final Button buttonShowPets = (Button) findViewById(R.id.button_show_pets);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextPetName = (EditText) findViewById(R.id.edit_text_name);
                final EditText editTextPetType = (EditText) findViewById(R.id.edit_text_species);

                mDB.beginTransaction();

                try {

                    // Check if species type exists already
                    long rowId = 0;
                    String petType = editTextPetType.getText().toString().toLowerCase(Locale.getDefault());

                    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                    queryBuilder.setTables(PetType.PETTYPE_TABLE_NAME);
                    queryBuilder.appendWhere(PetType.PET_TYPE_NAME + "='" + petType + "'");

                    Cursor cursor = queryBuilder.query(mDB, null, null, null, null, null, null);

                    if (cursor.getCount() == 0) {
                        ContentValues typeRecordToAdd = new ContentValues();
                        typeRecordToAdd.put(PetType.PET_TYPE_NAME, petType);
                        rowId = mDB.insert(PetType.PETTYPE_TABLE_NAME, PetType.PET_TYPE_NAME, typeRecordToAdd);

                        // update autocomplete with new record
                        fillAutoCompleteFromDatabase();

                    } else {
                        cursor.moveToFirst();
                        rowId = cursor.getLong(cursor.getColumnIndex(PetType._ID));
                    }

                    cursor.close();

                    // Always insert new pet records, even if the names clash
                    ContentValues petRecordToAdd = new ContentValues();
                    petRecordToAdd.put(Pets.PET_NAME, editTextPetName.getText().toString());
                    petRecordToAdd.put(Pets.PET_TYPE_ID, rowId);
                    mDB.insert(Pets.PETS_TABLE_NAME, Pets.PET_NAME, petRecordToAdd);

                    mDB.setTransactionSuccessful();

                } finally {
                    mDB.endTransaction();
                }

                // reset form
                editTextPetName.setText(null);
                editTextPetType.setText(null);
            }
        });

        buttonShowPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetTrackerEntryActivity.this, PetTrackerListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Fill AutoComplete word list from database.
     */
    void fillAutoCompleteFromDatabase() {
        mCursor = mDB.query(PetType.PETTYPE_TABLE_NAME, new String[] {PetType.PET_TYPE_NAME, PetType._ID}, null, null, null, null, PetType.DEFAULT_SORT_ORDER);

        int numSpeciesTypes = mCursor.getCount();
        String strAutoTextOptions[] = new String[numSpeciesTypes];

        if (numSpeciesTypes > 0 && mCursor.moveToFirst()) {
            for (int i = 0; i < numSpeciesTypes; i++) {
                strAutoTextOptions[i] = mCursor.getString(mCursor.getColumnIndex(PetType.PET_TYPE_NAME));
                mCursor.moveToNext();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, strAutoTextOptions);
            AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.edit_text_species);
            editText.setAdapter(adapter);
        }
    }

}
