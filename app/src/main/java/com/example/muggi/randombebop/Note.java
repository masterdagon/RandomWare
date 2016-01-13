package com.example.muggi.randombebop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Muggi on 05-12-2015.
 */
public class Note implements Parcelable{

    private int id;
    private String message = "*Empty*";
    private String title = "*Empty*";
    private String category = "*Empty*";
    private String picture = "NOTSET";

    public Note(int id){
    }

    public Note(){
    }
    
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(picture);
    }

    private Note(Parcel in){
        this.id = in.readInt();
        this.message = in.readString();
        this.title = in.readString();
        this.category = in.readString();
        this.picture = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>(){

        @Override
        public Note createFromParcel(Parcel source){
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size){
            return new Note[size];
        }
    };
}
