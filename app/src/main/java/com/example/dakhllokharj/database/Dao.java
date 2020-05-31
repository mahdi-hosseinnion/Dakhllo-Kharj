package com.example.dakhllokharj.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {
    @Insert
    long[] insertReceipt(Receipt... receipts);
    @Query("SELECT * FROM mainTransaction")
    LiveData<List<Receipt>>getAllReceipt();
    @Query("SELECT amount FROM mainTransaction")
    List<Integer>getAllAmounts();
    @Update
    int updateReceipt(Receipt... receipts);
    @Delete
    int deleteReceipt(Receipt... receipts);
}
