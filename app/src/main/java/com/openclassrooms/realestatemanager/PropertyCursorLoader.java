package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.database.Cursor;
import androidx.loader.content.AsyncTaskLoader;

public class PropertyCursorLoader extends AsyncTaskLoader<Cursor> {
    private final String[] projection;
    private final String selection;
    private final String[] selectionArgs;
    private final String sortOrder;
    private final boolean loadImages;

    // Property loader
    public PropertyCursorLoader(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context);
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
        this.loadImages = false;
    }

    // Images loader
    public PropertyCursorLoader(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder, boolean loadImages) {
        super(context);
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
        this.loadImages = loadImages;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        if (loadImages) {
            return PropertyProvider.queryImagesForProperty(getContext(), selection, selectionArgs, sortOrder);
        } else {
            return PropertyProvider.queryProperties(getContext(), projection, selection, selectionArgs, sortOrder);
        }
    }

}
