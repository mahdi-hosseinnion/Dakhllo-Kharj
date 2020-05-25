package com.example.dakhllokharj.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mainTransaction")
public class Receipt implements Parcelable {
    //if does not run use column info
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String person;
    private String title;
    private String subTitle;
    private String category;
    private String subCategory;
    private String timeStamp;
    private int amount;

    public Receipt( String person, String title, String subTitle, String category, String subCategory, String timeStamp, int amount) {
        this.person = person;
        this.title = title;
        this.subTitle = subTitle;
        this.category = category;
        this.subCategory = subCategory;
        this.timeStamp = timeStamp;
        this.amount = amount;
    }

    protected Receipt(Parcel in) {
        id = in.readInt();
        person = in.readString();
        title = in.readString();
        subTitle = in.readString();
        category = in.readString();
        subCategory = in.readString();
        timeStamp = in.readString();
        amount = in.readInt();
    }

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(person);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(timeStamp);
        dest.writeInt(amount);
    }
}
