package com.example.dakhllokharj.asyncTask;

import android.os.AsyncTask;

import com.example.dakhllokharj.database.Category;
import com.example.dakhllokharj.database.Dao;
import com.example.dakhllokharj.database.Receipt;

public class InsertCategoryAsyncTask extends AsyncTask<Category,Void,Void> {
    private Dao dao;

    public InsertCategoryAsyncTask(Dao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Category... categories) {
        dao.insertCategory(categories);
        return null;
    }
}
