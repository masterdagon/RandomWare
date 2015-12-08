package com.example.muggi.randombebop;

/**
 * Created by Muggi on 05-12-2015.
 */
public class Note {

    private String message;
    private String name = "Untitled";
    private String category = "Unassigned";
    private String picture = "NOTSET";

    public Note(String message){
        this.message = message;
    }

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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
