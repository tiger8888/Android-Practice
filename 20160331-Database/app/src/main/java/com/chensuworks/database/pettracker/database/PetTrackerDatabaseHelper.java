package com.chensuworks.database.pettracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chensuworks.database.pettracker.database.PetTrackerDatabase.PetType;
import com.chensuworks.database.pettracker.database.PetTrackerDatabase.Pets;

public class PetTrackerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pet_tracker.db";
    private static final int DATABASE_VERSION = 1;

    public PetTrackerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PetType.PETTYPE_TABLE_NAME + "("
                    + PetType._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PetType.PET_TYPE_NAME + " TEXT"
                    + ");");

        db.execSQL("CREATE TABLE " + Pets.PETS_TABLE_NAME + "("
                + Pets._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Pets.PET_NAME + " TEXT,"
                + Pets.PET_TYPE_ID + " INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
