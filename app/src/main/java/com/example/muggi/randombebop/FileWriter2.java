package com.example.muggi.randombebop;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Muggi on 07-12-2015.
 */
public class FileWriter2 {

    File filedir;

    public FileWriter2(){
        try{
            filedir = new File(Environment.getExternalStorageDirectory(),"Notes");
            if(!filedir.exists()){
                filedir.mkdirs();
            }
        }catch(Exception e){

        }

    }

    public void save(Context context, ArrayList<Note> notes) {
        String content = "";
        FileOutputStream fop = null;
        File file;
        try {
            file = new File(filedir,"notes.txt");
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            //if (!file.exists()) {
            file.createNewFile();
            //}
            for (Note note : notes) {
                content = content + note.getName() + "," + note.getMessage() + "," + note.getCategory() + "," + note.getPicture() + ";";
            }
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (Exception e) {

        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Note> load(Context context) {
        File file = new File(filedir, "notes.txt");
        FileInputStream fis = null;
        ArrayList<Note> list = new ArrayList();

        try {
            fis = new FileInputStream(file);

            System.out.println("Total file size to read (in bytes) : "
                    + fis.available());

            int content;
            String data = "";
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                data = data + (char) content;
                //Log.i("MESSAGE LOADED: ", data);
            }
            if(!data.equals("")) {
                String[] entries = data.split(";");

                for (String str : entries) {
                    String[] entry = str.split(",");
                    Note note = new Note(entry[2],Integer.parseInt(entry[0]));
                    note.setName(entry[1]);
                    note.setMessage(entry[2]);
                    note.setCategory(entry[3]);
                    note.setPicture(entry[4]);
                    list.add(note);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        Log.i("Returning list size: ",String.valueOf(list.size()));
        return list;
    }
}
