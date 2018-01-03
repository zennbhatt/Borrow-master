package com.subhrajyoti.borrow.listItems;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.subhrajyoti.borrow.db.AppDatabase;
import com.subhrajyoti.borrow.db.HistoryModel;

import java.util.ArrayList;
import java.util.List;


public class BorrowedListViewModel extends AndroidViewModel {

    private final LiveData<List<HistoryModel>> songList;

    private AppDatabase appDatabase;

    public BorrowedListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        songList = appDatabase.songModel().getAllSongs();
    }


    public LiveData<List<HistoryModel>> getSongList() {
        return songList;
    }

    public void deleteItem(HistoryModel historyModel) {
        new deleteAsyncTask(appDatabase).execute(historyModel);
    }

    private static class deleteAsyncTask extends AsyncTask<HistoryModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final HistoryModel... params) {
            db.songModel().deleteSong(params[0]);
            return null;
        }

    }

}
