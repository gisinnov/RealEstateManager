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
    public static final Uri IMAGES_CONTENT_URI = Uri.parse(BASE_CONTENT_URI + "/" + PropertyDatabaseHelper.TABLE_NAME_IMAGES);

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

    public static void insertImage(Context context, String imagePath, long propertyId) {
        ContentValues values = new ContentValues();
        values.put(PropertyDatabaseHelper.COLUMN_IMAGE_PATH, imagePath);
        values.put(PropertyDatabaseHelper.COLUMN_PROPERTY_ID, propertyId);

        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.insert(IMAGES_CONTENT_URI, values);
    }

    public static List<String> queryImagesForProperty(Context context, long propertyId) {
        String[] projection = {PropertyDatabaseHelper.COLUMN_IMAGE_PATH};
        String selection = PropertyDatabaseHelper.COLUMN_PROPERTY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(propertyId)};

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(IMAGES_CONTENT_URI, projection, selection, selectionArgs, null);

        List<String> images = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                images.add(cursor.getString(cursor.getColumnIndexOrThrow(PropertyDatabaseHelper.COLUMN_IMAGE_PATH)));
            }
            cursor.close();
        }

        return images;
    }

    public static void deleteImagesForProperty(Context context, long propertyId) {
        String selection = PropertyDatabaseHelper.COLUMN_PROPERTY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(propertyId)};

        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.delete(IMAGES_CONTENT_URI, selection, selectionArgs);
    }

    public static Cursor queryProperties(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    public static Cursor queryImagesForProperty(Context context, String selection, String[] selectionArgs, String sortOrder) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.query(IMAGES_CONTENT_URI, null, selection, selectionArgs, sortOrder);
    }

}