package com.example.dakhllokharj.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    //what category is sub of with id
    private int countOfUsage;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCountOfUsage(int countOfUsage) {
        this.countOfUsage = countOfUsage;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getCountOfUsage() {
        return countOfUsage;
    }

    public Category(String category) {
        this.category = category;
    }
    @Ignore
    public Category(String category, int countOfUsage) {

        this(category);
        this.countOfUsage = countOfUsage;
    }
}
