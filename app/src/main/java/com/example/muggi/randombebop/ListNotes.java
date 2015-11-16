package com.example.muggi.randombebop;

import java.util.ArrayList;

/**
 * Created by Muggi on 16-11-2015.
 */
public class ListNotes {

    public ArrayList<String[]> notes = new ArrayList();

    public ListNotes(){
    }

    public void createListItem(String title, String note){
        String[] array = new String[2];
        array[0]=title;
        array[1]=note;
        this.notes.add(array);
    }

    public ArrayList<String[]> getNotes(){
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


}
