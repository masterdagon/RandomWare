package com.example.muggi.randombebop;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Muggi on 07-12-2015.
 */
public class ListNotes2 {

    public ArrayList<Note> notes = new ArrayList();
    public FileWriter2 fw = new FileWriter2();
    public Context context = null;

    public ListNotes2(Context context) {
        this.context = context;
    }

    public void createListItem(String note){
        Note n = new Note(note,0);
        this.notes.add(n);
        savefile(notes);
    }

    public void addNoteToList(Note note) {
        this.notes.add(note);
        savefile(notes);
    }

    public ArrayList<Note> getNotes() {
        loadfile();
        return notes;
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

    public int findIndex(Note note) {
        for (int i = 0; i< notes.size(); i++) {
            if(note == notes.get(i)){
                return i;
            }
        }
        return -2;
    }

    public void deleteNote(int position) {
        notes.remove(position);
        savefile(notes);
    }

    public void savefile(ArrayList<Note> notes) {
        fw.save(context, notes);
    }

    public void loadfile() {
        notes = fw.load(context);
    }
}
