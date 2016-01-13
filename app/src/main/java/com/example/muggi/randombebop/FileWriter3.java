package com.example.muggi.randombebop;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 12-01-2016.
 */
public class FileWriter3 {

    File filedir;
    File filedir2;
    private int id = 0;

    public FileWriter3() {
        try {
            filedir = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!filedir.exists()) {
                filedir.mkdirs();
            }
        } catch (Exception e) {

        }
        try {
            filedir2 = new File(Environment.getExternalStorageDirectory(), "Notes/settings");
            if (!filedir2.exists()) {
                filedir2.mkdirs();
            }
        } catch (Exception e) {

        }

    }

    public ArrayList<Note> loadAllFiles() {
        ArrayList<Note> inFiles = new ArrayList<Note>();
        File[] files = filedir.listFiles();
        for (File file : files) {
            System.out.println(file.getName());
            if (file.isDirectory()) {
                //inFiles.addAll(getAllFiles(file));
            } else {
                if (file.getName().endsWith(".txt")) {
                    Note note = new Note("NotLoaded", 0);
                    note.setName(file.getName().substring(0, file.getName().length() - 4));
                    inFiles.add(note);
                }
            }
        }
        return inFiles;
    }

    public Note loadNote(Note note) {
        File file = new File(filedir, note.getName() + ".txt");
        FileInputStream fis = null;

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
            System.out.println(data.toString());
            if (!data.equals("")) {
                String[] entry = data.split(",");
                note.setId(Integer.parseInt(entry[0]));
                note.setName(entry[1]);
                note.setMessage(entry[2]);
                note.setCategory(entry[3]);
                note.setPicture(entry[4]);
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
        return note;
    }

//    public Note loadNote(Note note) {
//        File sdcard = Environment.getExternalStorageDirectory();
//        File file = new File(sdcard, note.getName()+".txt");
//        StringBuilder text = new StringBuilder();
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                text.append(line);
//                text.append('\n');
//            }
//            br.close();
//        } catch (IOException e) {
//            //You'll need to add proper error handling here
//        }
//
//        if (!text.equals("")) {
//            String[] entry = text.toString().split(",");
//            int test = 0;
//            for (String s:entry) {
//                System.out.println(test+" "+s);
//                test++;
//            }
//            note.setId(Integer.parseInt(entry[0]));
//            note.setName(entry[1]);
//            note.setMessage(entry[2]);
//            note.setCategory(entry[3]);
//            note.setPicture(entry[4]);
//            return note;
//        }
//        return null;
//    }

    public boolean saveNote(Note note, boolean newNote) {
        boolean succes = false;
        String content = "";
        FileOutputStream fop = null;
        File file;
        int name = note.getId();
        try {
            file = new File(filedir, name + ".txt");
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (newNote) {
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    succes = false;
                }
            } else {
                file.createNewFile();
            }

            StringBuilder sb = new StringBuilder();
            sb.append(note.getId());
            sb.append(",");
            sb.append(note.getName());
            sb.append(",");
            sb.append(note.getMessage());
            sb.append(",");
            sb.append(note.getCategory());
            sb.append(",");
            sb.append(note.getPicture());
            content=sb.toString();

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");
            succes = true;
        } catch (Exception e) {

        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
                return succes;
            } catch (IOException e) {
                e.printStackTrace();
                return succes;
            }
        }
    }

    public boolean deleteNote(Note note) {
        return false;
    }

    public boolean saveCategories(ArrayList<Category> cat) {
        boolean succes = false;
        String content = "";
        FileOutputStream fop = null;
        File file;
        try {
            file = new File(filedir2, "categories.txt");
            fop = new FileOutputStream(file);

            file.createNewFile();

            StringBuilder sb = new StringBuilder();
            for(int i=0;i<cat.size();i++)
                if(cat.size()-1!=i){
                    sb.append(cat.get(i).getCategory());
                    sb.append("");
                }else{
                    sb.append(cat.get(i).getCategory());
                }


            content=sb.toString();

            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");
            succes = true;
        } catch (Exception e) {

        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
                return succes;
            } catch (IOException e) {
                e.printStackTrace();
                return succes;
            }
        }
    };

    public ArrayList<Category> loadCategories() {
        ArrayList<Category> cat = new ArrayList<Category>();
        File file = new File(filedir2, "categories.txt");
        FileInputStream fis = null;

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
            System.out.println(data.toString());
            if(!data.equals("")) {
                String[] entry = data.split(",");
                for (String s:entry) {
                    Category category = new Category(s);
                    cat.add(category);
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
        return cat;
    }

    public int loadID(){
        return id;
    }
}