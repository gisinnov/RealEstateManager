package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class PropertyProvider {
    private static final String BASE_CONTENT_URI = "content://com.openclassrooms.realestatemanager.propertyprovider";
    public static final Uri CONTENT_URI = Uri.parse(BASE_CONTENT_URI);

    public static void insertProperty(Context context, String propertyName, double propertyPrice, String availabilityDate,
                                      String estateType, String rooms, String bedrooms, String bathrooms, String agent, String description, String status,
                                      double propertyArea, String propertyAddress, boolean isSchoolNearby, boolean areShopsNearby, boolean isParkNearby) {
        ContentValues values = new ContentValues();
        values.put(PropertyContract.PropertyEntry.COLUMN_PROPERTY_NAME, propertyName);
        values.put(PropertyContract.PropertyEntry.COLUMN_PROPERTY_PRICE, propertyPrice);
        values.put(PropertyContract.PropertyEntry.COLUMN_AVAILABILITY_DATE, availabilityDate);
        values.put(PropertyContract.PropertyEntry.COLUMN_ESTATE_TYPE, estateType);
        values.put(PropertyContract.PropertyEntry.COLUMN_ROOMS, rooms);
        values.put(PropertyContract.PropertyEntry.COLUMN_BEDROOMS, bedrooms);
        values.put(PropertyContract.PropertyEntry.COLUMN_BATHROOMS, bathrooms);
        values.put(PropertyContract.PropertyEntry.COLUMN_AGENT, agent);
        values.put(PropertyContract.PropertyEntry.COLUMN_DESCRIPTION, description);
        values.put(PropertyContract.PropertyEntry.COLUMN_STATUS, status);
        values.put(PropertyContract.PropertyEntry.COLUMN_PROPERTY_AREA, propertyArea);
        values.put(PropertyContract.PropertyEntry.COLUMN_PROPERTY_ADDRESS, propertyAddress);
        values.put(PropertyContract.PropertyEntry.COLUMN_IS_SCHOOL_NEARBY, isSchoolNearby);
        values.put(PropertyContract.PropertyEntry.COLUMN_ARE_SHOPS_NEARBY, areShopsNearby);
        values.put(PropertyContract.PropertyEntry.COLUMN_IS_PARK_NEARBY, isParkNearby);

        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.insert(CONTENT_URI, values);
    }

    public static Cursor queryProperties(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    public static void updateProperty(Context context, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.update(uri, values, selection, selectionArgs);
    }

    public static void deleteProperty(Context context, Uri uri, String selection, String[] selectionArgs) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.delete(uri, selection, selectionArgs);
    }
}
