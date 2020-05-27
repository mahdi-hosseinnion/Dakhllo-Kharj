package com.example.dakhllokharj.asyncTask;

import android.os.AsyncTask;

import com.example.dakhllokharj.database.Dao;
import com.example.dakhllokharj.database.Receipt;

public class InsertAsyncTask extends AsyncTask<Receipt,Void,Void> {
    private Dao dao;

    public InsertAsyncTask(Dao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Receipt... receipts) {
        dao.insertReceipt(receipts);
        return null;
    }
}
