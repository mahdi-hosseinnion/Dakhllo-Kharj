package com.example.dakhllokharj.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String category;
    //what category is sub of with id
    private int subOf;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubOf(int subOf) {
        this.subOf = subOf;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getSubOf() {
        return subOf;
    }



    public Category( String category, int subOf) {

        this.category = category;
        this.subOf = subOf;
    }
}
