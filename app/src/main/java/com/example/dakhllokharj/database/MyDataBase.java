package com.example.dakhllokharj.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Receipt.class, Category.class},version = 2)
public abstract class MyDataBase extends RoomDatabase {
    public static final String DATABASE_NAME="mainDB";
    private static MyDataBase instance;
    public abstract Dao dao();

    public static MyDataBase getInstance(final Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class,
                    DATABASE_NAME)
                    .build();
        }
        return instance;
    }

}
