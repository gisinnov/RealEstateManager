package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.database.Cursor;
import androidx.loader.content.AsyncTaskLoader;

public class PropertyCursorLoader extends AsyncTaskLoader<Cursor> {
    private final String[] projection;
    private final String selection;
    private final String[] selectionArgs;
    private final String sortOrder;

    public PropertyCursorLoader(Context context, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context);
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        return getContext().getContentResolver().query(
                PropertyContentProvider.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );
    }
}
