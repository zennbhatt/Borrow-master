package com.subhrajyoti.borrow.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface HistoryModelDao {

    @Query("select * from HistoryModel")
    LiveData<List<HistoryModel>> getAllSongs();

    @Query("select * from HistoryModel where songName = :songName")
    HistoryModel getSongByName(String songName);

    @Insert(onConflict = REPLACE)
    void addSong(HistoryModel borrowModel);

    @Delete
    void deleteSong(HistoryModel borrowModel);

}
