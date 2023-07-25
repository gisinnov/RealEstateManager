package com.openclassrooms.realestatemanager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PropertyContentProvider extends ContentProvider {
    private PropertyDatabaseHelper dbHelper;

    public static final Uri CONTENT_URI_PROPERTY = Uri.parse("content://com.openclassrooms.realestatemanager.propertyprovider/" + PropertyContract.PropertyEntry.TABLE_NAME);
    public static final Uri CONTENT_URI_IMAGE = Uri.parse("content://com.openclassrooms.realestatemanager.propertyprovider/" + PropertyDatabaseHelper.TABLE_NAME_IMAGES);

    @Override
    public boolean onCreate() {
        dbHelper = new PropertyDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        if (uri.equals(CONTENT_URI_PROPERTY)) {
            cursor = db.query(
                    PropertyContract.PropertyEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
        } else if (uri.equals(CONTENT_URI_IMAGE)) {
            cursor = db.query(
                    PropertyDatabaseHelper.TABLE_NAME_IMAGES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Not used in this implementation
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId;
        if (uri.equals(CONTENT_URI_PROPERTY)) {
            newRowId = db.insert(PropertyContract.PropertyEntry.TABLE_NAME, null, values);
        } else if (uri.equals(CONTENT_URI_IMAGE)) {
            newRowId = db.insert(PropertyDatabaseHelper.TABLE_NAME_IMAGES, null, values);
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.withAppendedPath(uri, String.valueOf(newRowId));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        if (uri.equals(CONTENT_URI_PROPERTY)) {
            rowsDeleted = db.delete(PropertyContract.PropertyEntry.TABLE_NAME, selection, selectionArgs);
        } else if (uri.equals(CONTENT_URI_IMAGE)) {
            rowsDeleted = db.delete(PropertyDatabaseHelper.TABLE_NAME_IMAGES, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null && rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;
        if (uri.equals(CONTENT_URI_PROPERTY)) {
            rowsUpdated = db.update(PropertyContract.PropertyEntry.TABLE_NAME, values, selection, selectionArgs);
        } else if (uri.equals(CONTENT_URI_IMAGE)) {
            rowsUpdated = db.update(PropertyDatabaseHelper.TABLE_NAME_IMAGES, values, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null && rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
