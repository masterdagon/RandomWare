package com.example.muggi.randombebop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dennis on 12-01-2016.
 */
public class Category implements Parcelable {

    private String category = "Unassigned";

    public Category(String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setMessage(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
    }

    private Category(Parcel in){
        this.category = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>(){

        @Override
        public Category createFromParcel(Parcel source){
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size){
            return new Category[size];
        }
    };
}
