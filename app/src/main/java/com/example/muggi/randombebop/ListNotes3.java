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
        this.notes.add(note);
        fw.saveNote(note, true);
    }

    public void saveOldNote(Note note) {
        fw.saveNote(note, false);

    }

    public ArrayList<Note> loadAll() {
        notes = fw.loadAllFiles();
        for (Note n:notes) {
            n=fw.loadNote(n);

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
        for (int i = 0; i < notes.size(); i++) {
            astr[i] = (notes.get(i).getName());
        }
        return astr;
    }

    public void deleteNote(int position) {
        notes.remove(position);
        fw.deleteNote(notes.get(position));
    }

}
