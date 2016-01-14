package com.example.muggi.randombebop;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

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

    private NoteZipper nz = new NoteZipper();
    private File filedir;
    private File filedir2;
    private File filedir3;
    private int newID = 0;
    private int newPId=0;

    public boolean zipFiles(Note note){
        return nz.zip(note,"#"+note.getId()+".zip");
    }

    public void unzipFiles(String fileName){
        nz.unzip(fileName);
    }

    public int getNewPId(){
        return newPId;
    }

    public void setNewPId(int id){
        newPId=id;
        saveId(newPId,false);
    }

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
        try {
            filedir3 = new File(Environment.getExternalStorageDirectory(), "Notes/NotePictures");
            if (!filedir3.exists()) {
                filedir3.mkdirs();
            }
        } catch (Exception e) {

        }
        newID = loadId(true);
    }

    public ArrayList<Note> loadAllFiles() {
        ArrayList<Note> inFiles = new ArrayList<>();
        File[] files = filedir.listFiles();
        for (File f :files) {
            if(f.getName().endsWith(".zip")){
                System.out.println("ZIP FILE REGISTRET");
                unzipFiles(f.getName());
                f.delete();
            }
        }
        files = filedir.listFiles();
        System.out.println("ZIP FILE REGISTRET");
        for (File f :files) {
            if (f.isDirectory()) {

            }else if (f.getName().startsWith("#note")) {
                System.out.println("See note has an #");
                f.renameTo(new File(filedir,newID+".txt"));
                Note note = loadNote(new Note(newID));
                saveId(newID,true);
                    if(!note.getPicture().equals("NOTSET")){
                        File[] fileP = filedir3.listFiles();
                        for (File fp :fileP) {
                            if(fp.getName().startsWith("#picture")){
                                String imgPath;
                                imgPath = "image_"+newPId+".jpg";
                                newPId++;
                                saveId(newPId, false);
                                fp.renameTo(new File(filedir3,imgPath));
                                note.setPicture(Environment.getExternalStorageDirectory().getPath()+"/Notes/NotePictures/"+imgPath);
                            }
                        }
                    }
                    saveNote(note, true,false);
                    inFiles.add(note);

            }else{
                if (f.getName().endsWith(".txt")) {
                    inFiles.add(new Note(Integer.parseInt(f.getName().substring(0, f.getName().length() - 4))));
                }
            }
        }

        return inFiles;
    }

    public Note loadNote(Note note) {
        File file = new File(filedir, note.getId() + ".txt");
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

    public Note saveNote(Note note, boolean newNote,boolean blue) {
        String content = "";
        FileOutputStream fop = null;
        File file;
        System.out.println("error" + note.toString());
        if (newNote) {
            note.setId(newID);
            newID++;
            saveId(newID,true);
        }
        int name = note.getId();
        try {
            if(blue){
                file = new File(filedir, "#RandomBebop"+name + ".txt");
            }else{
                file = new File(filedir, name + ".txt");
            }
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it

            file.createNewFile();


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
            content = sb.toString();

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
                return note;
            } catch (IOException e) {
                e.printStackTrace();
                return note;
            }
        }
    }

    public boolean deleteNote(Note note,boolean blue) {
        File file;
        if(blue){
            file = new File(filedir.getPath() + "/#note" + note.getId() + ".txt");
        }else{
            file = new File(filedir.getPath() + "/" + note.getId() + ".txt");
        }
        boolean deleted = file.delete();
        return deleted;
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
            for (int i = 0; i < cat.size(); i++)
                if (cat.size() - 1 != i) {
                    sb.append(cat.get(i).getCategory());
                    sb.append(",");
                } else {
                    sb.append(cat.get(i).getCategory());
                }


            content = sb.toString();

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

    ;

    public ArrayList<Category> loadCategories() {
        ArrayList<Category> cat = new ArrayList<Category>();
        File file = new File(filedir2, "categories.txt");
        FileInputStream fis = null;
        if (!file.exists()) {
            String[] temp = {"No Category", "Sports", "Hobby", "School", "Work", "Fun"};
            for (String s : temp) {
                cat.add(new Category(s));
            }
            return cat;
        } else {
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
                    for (String s : entry) {
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
    }

    public int loadId(boolean noteId) {
        int id = 0;
        File file;
        if(noteId){
            file = new File(filedir2, "nId.txt");
        }else{
            file = new File(filedir2, "pId.txt");
        }
        FileInputStream fis = null;
        if (!file.exists()) {
            return id;
        } else {
            try {
                fis = new FileInputStream(file);
                int content;
                String data = "";
                while ((content = fis.read()) != -1) {
                    // convert to char and display it
                    data = data + (char) content;
                    //Log.i("MESSAGE LOADED: ", data);
                }
                id = Integer.parseInt(data.toString());
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
            return id;
        }
    }

    public boolean saveId(int id,boolean noteId) {
        boolean succes = false;
        String content = "";
        FileOutputStream fop = null;
        File file;
        try {
            if(noteId){
                file = new File(filedir2, "nId.txt");
            }else{
                file = new File(filedir2, "pId.txt");
            }

            fop = new FileOutputStream(file);
            file.createNewFile();
            content = "" + id;
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
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
}