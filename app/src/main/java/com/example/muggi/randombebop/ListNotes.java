package com.example.muggi.randombebop;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Muggi on 16-11-2015.
 */
public class ListNotes {

    public ArrayList<String[]> notes = new ArrayList();
    public FileWriter fw = new FileWriter();
    public Context context = null;

    public ListNotes(Context context){
        this.context = context;
    }

    public void createListItem(String title, String note){
        String[] array = new String[2];
        array[0]=title;
        array[1]=note;
        this.notes.add(array);
        savefile(notes);
    }

    public ArrayList<String[]> getNotes(){
        loadfile();
        return notes;
    }

    public int getSize(){
        return notes.size();
    }

    public String[] getTitles(){
        String[] astr = new String[notes.size()];
        for(int i = 0; i<notes.size();i++){
            astr[i]=(notes.get(i)[0]);
        }
        return astr;
    }

    public void deleteNote(int position){
        notes.remove(position);
        savefile(notes);
    }

    public void savefile(ArrayList<String[]> notes){
        fw.save(context,notes);
    }

    public void loadfile(){
        notes = fw.load(context);
    }


}
