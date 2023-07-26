
package com.openclassrooms.realestatemanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface PropertyDao {
    @Query("SELECT * FROM properties")
    Flowable<List<Property>> getAll();

    @Insert
    Completable insert(Property property);

    @Update
    Completable update(Property property);

    @Delete
    Completable delete(Property property);
}
