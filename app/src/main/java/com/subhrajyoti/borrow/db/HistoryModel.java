package com.subhrajyoti.borrow.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class HistoryModel {

    @PrimaryKey
    @NonNull
    private String songName;
    private String songURL;
    private String coverURL;
    private String artistName;


    public String getCoverURL() {
        return coverURL;
    }

    public String getArtistName() {
        return artistName;
    }


    public HistoryModel(String songName, String songURL, String coverURL, String artistName) {
        this.songName = songName;
        this.songURL = songURL;
        this.coverURL = coverURL;
        this.artistName = artistName;

    }

    public String getSongName() {
        return songName;
    }

    public String getSongURL() {
        return songURL;
    }


}
