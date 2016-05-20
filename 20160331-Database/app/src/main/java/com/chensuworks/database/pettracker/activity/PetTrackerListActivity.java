package com.chensuworks.database.pettracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.chensuworks.database.R;
import com.chensuworks.database.pettracker.database.PetTrackerDatabase.PetType;
import com.chensuworks.database.pettracker.database.PetTrackerDatabase.Pets;

public class PetTrackerListActivity extends PetTrackerActivity {

    private SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
    private String asColumnsToReturn[] = {
            Pets.PETS_TABLE_NAME + "." + Pets.PET_NAME,
            Pets.PETS_TABLE_NAME + "." + Pets._ID,
            PetType.PETTYPE_TABLE_NAME + "." + PetType.PET_TYPE_NAME
    };
    private ListAdapter adapter = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_tracker_show_pets);

        fillPetList();

        final Button buttonEnterMorePets = (Button) findViewById(R.id.button_enter_more_pets);

        buttonEnterMorePets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillPetList() {
        queryBuilder.setTables(Pets.PETS_TABLE_NAME + ", " + PetType.PETTYPE_TABLE_NAME);
        queryBuilder.appendWhere(Pets.PETS_TABLE_NAME + "." + Pets.PET_TYPE_ID + "=" + PetType.PETTYPE_TABLE_NAME + "." + PetType._ID);

        mCursor = queryBuilder.query(mDB, asColumnsToReturn, null, null, null, null, Pets.DEFAULT_SORT_ORDER);
        adapter = new SimpleCursorAdapter(this, R.layout.item_pet, mCursor, new String[] {Pets.PET_NAME, PetType.PET_TYPE_NAME}, new int[] {R.id.text_view_pet_name, R.id.text_view_pet_type}, 1);

        listView = (ListView) findViewById(R.id.pet_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long deletePetId = id;

                RelativeLayout itemLayout = (RelativeLayout) view;
                TextView petNameTextView = (TextView) itemLayout.findViewById(R.id.text_view_pet_name);
                String name = petNameTextView.getText().toString();

                new AlertDialog.Builder(PetTrackerListActivity.this)
                        .setMessage("Delete Pet Record for " + name + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete that pet
                                deletePet(deletePetId);

                                // Refresh the data in our cursor and therefore our list
                                mCursor = queryBuilder.query(mDB, asColumnsToReturn, null, null, null, null, Pets.DEFAULT_SORT_ORDER, null);
                                adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_pet, mCursor, new String[] {Pets.PET_NAME, PetType.PET_TYPE_NAME}, new int[] {R.id.text_view_pet_name, R.id.text_view_pet_type}, 1);
                                listView.setAdapter(adapter);
                            }
                        })
                        .show();
            }
        });

    }

    private void deletePet(Long id) {
        mDB.delete(Pets.PETS_TABLE_NAME, Pets._ID + "=?", new String[] {id.toString()});
    }

}
