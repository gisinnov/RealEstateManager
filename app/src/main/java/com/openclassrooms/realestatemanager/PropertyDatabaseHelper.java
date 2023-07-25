package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PropertyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "property.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_IMAGES = "Images";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_PROPERTY_ID = "property_id";

    public static final String COLUMN_IMAGE_ID = "image_id";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PropertyContract.PropertyEntry.TABLE_NAME + " (" +
                    PropertyContract.PropertyEntry._ID + " INTEGER PRIMARY KEY," +
                    PropertyContract.PropertyEntry.COLUMN_PROPERTY_NAME + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_PROPERTY_PRICE + " REAL," +
                    PropertyContract.PropertyEntry.COLUMN_AVAILABILITY_DATE + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_ESTATE_TYPE + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_ROOMS + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_BEDROOMS + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_BATHROOMS + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_AGENT + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_DESCRIPTION + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_STATUS + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_PROPERTY_AREA + " REAL," +
                    PropertyContract.PropertyEntry.COLUMN_PROPERTY_ADDRESS + " TEXT," +
                    PropertyContract.PropertyEntry.COLUMN_IS_SCHOOL_NEARBY + " INTEGER," +
                    PropertyContract.PropertyEntry.COLUMN_ARE_SHOPS_NEARBY + " INTEGER," +
                    PropertyContract.PropertyEntry.COLUMN_IS_PARK_NEARBY + " INTEGER)";

    private static final String SQL_CREATE_IMAGES_TABLE =
            "CREATE TABLE " + TABLE_NAME_IMAGES + " (" +
                    COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_IMAGE_PATH + " TEXT," +
                    COLUMN_PROPERTY_ID + " INTEGER," +
                    "FOREIGN KEY(" + COLUMN_PROPERTY_ID + ") REFERENCES " +
                    PropertyContract.PropertyEntry.TABLE_NAME + "(" + PropertyContract.PropertyEntry._ID + "))";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PropertyContract.PropertyEntry.TABLE_NAME;

    public PropertyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_IMAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
