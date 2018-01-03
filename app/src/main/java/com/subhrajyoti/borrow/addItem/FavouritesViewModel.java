package com.subhrajyoti.borrow.addItem;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.subhrajyoti.borrow.db.AppDatabase;
import com.subhrajyoti.borrow.db.HistoryModel;


public class FavouritesViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public FavouritesViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addBorrow(final HistoryModel borrowModel) {
        new addAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class addAsyncTask extends AsyncTask<HistoryModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final HistoryModel... params) {
            db.songModel().addSong(params[0]);
            return null;
        }

    }
}
