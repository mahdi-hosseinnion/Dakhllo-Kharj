package com.example.dakhllokharj.asyncTask;

import android.os.AsyncTask;

import com.example.dakhllokharj.database.Dao;
import com.example.dakhllokharj.database.Receipt;
import com.example.dakhllokharj.interfaces.TaskDelegate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GetAmountsAsyncTask extends AsyncTask<Receipt, Void, Integer> {
    private Dao dao;
    private WeakReference<TaskDelegate> taskDelegate;

    public GetAmountsAsyncTask(Dao dao, TaskDelegate taskDelegate) {
        this.taskDelegate = new WeakReference<>(taskDelegate);
        this.dao = dao;
    }

    @Override
    protected Integer doInBackground(Receipt... receipts) {
        List<Integer> amounts = new ArrayList<>(dao.getAllAmounts());
        int sum = 0;
        if (amounts !=null && amounts.size() > 0) {

            for (Integer amount : amounts) {
                sum += amount;
            }
            amounts.clear();
        }
        return sum;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        taskDelegate.get().onAmountsRetrieved(integer);
        super.onPostExecute(integer);
    }
//    @Override
//    protected Void doInBackground(Receipt... receipts) {
//        dao.insertReceipt(receipts);
//        return null;
//    }
}
