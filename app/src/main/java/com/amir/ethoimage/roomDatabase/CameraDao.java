package com.amir.ethoimage.roomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CameraDao {

    @Query("SELECT * FROM cameradatabase")
    List<CameraDatabase> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CameraDatabase task);
}
