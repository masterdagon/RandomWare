package com.example.muggi.randombebop;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Dennis on 12-01-2016.
 */
public class ListNotes3 {
    public ArrayList<Note> notes = new ArrayList();
    public ArrayList<Category> categories = new ArrayList();
    public FileWriter3 fw = new FileWriter3();
    public int newID = 0;

    public ListNotes3() {
        categories=fw.loadCategories();
        newID=fw.loadId();
    }

    public Note getNote(int id) {
        for (Note n : notes) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    public Note loadFromPosition(int position) {
        return loadNote(notes.get(position));
    }


    public void saveNewNote(Note note) {
        note.setId(newID);
        newID++;
        fw.saveId(newID);
        this.notes.add(note);
        fw.saveNote(note, true);
    }

    public void saveOldNote(Note note) {
        fw.saveNote(note, false);

    }

    public ArrayList<Note> loadAll() {
        System.out.println("i ran this");
        ArrayList<Note> temp = fw.loadAllFiles();
        System.out.println("i ran this to "+temp.get(1).getId());
        notes.addAll(temp);
        for (Note n: notes) {
            System.out.print("test id"+ n.getId());
            n = fw.loadNote(n);
        }
        return notes;
    }

    public Note loadNote(Note note) {
        note = fw.loadNote(note);
        return note;
    }

    public ArrayList<Category> getCategories() {
        fw.loadCategories();
        return categories;
    }

    public int getSize() {
        return notes.size();
    }

    public String[] getTitles() {
        String[] astr = new String[notes.size()];
        System.out.print("astr "+astr.length);
        System.out.print("notes "+notes.size());

        for (int i = 0; i < notes.size(); i++) {
            astr[i] = (notes.get(i).getName());
        }
        return astr;
    }

    public boolean deleteNote(int position) {
        boolean deleted= fw.deleteNote(notes.get(position));
        if(deleted){
            notes.remove(position);
        }
        return deleted;
    }

    public boolean updateNote(Note note) {

        int index = 0;
        boolean found = false;
        for (int i = 0; i < notes.size(); i++) {
            Note n = notes.get(i);
            if (n.getId() == note.getId()) {
                index = i;
                found = true;
                break;
            }
        }
        if (found) {
            notes.remove(index);
            notes.add(index, note);
        }
        return found;
    }
}
