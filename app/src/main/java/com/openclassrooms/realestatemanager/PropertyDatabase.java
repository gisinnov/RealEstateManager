
package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Property.class}, version = 1, exportSchema = false)
public abstract class PropertyDatabase extends RoomDatabase {

    private static PropertyDatabase instance;

    public abstract PropertyDao propertyDao();

    public static synchronized PropertyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PropertyDatabase.class, "property_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
