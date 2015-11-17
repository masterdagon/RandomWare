package com.example.muggi.randombebop;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Muggi on 16-11-2015.
 */
public class FileWriter {

    public void save(Context context, ArrayList<String[]> notes){
        String content = "";
        FileOutputStream fop = null;
        File file;
        try{
            file = new File(context.getFilesDir(),"notes.txt");
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            //if (!file.exists()) {
                file.createNewFile();
            //}
        for(String[] arr : notes){
            content = content + arr[0]+","+arr[1]+";";
        }
            // get the content in bytes
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        }catch(Exception e){

        }

        finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<String[]> load(Context context) {
        File file = new File(context.getFilesDir(), "notes.txt");
        FileInputStream fis = null;
        ArrayList<String[]> list = new ArrayList();

        try {
            fis = new FileInputStream(file);

            System.out.println("Total file size to read (in bytes) : "
                    + fis.available());

            int content;
            String data = "";
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                data = data + (char) content;
                System.out.print((char) content);
            }
            String[] entries = data.split(";");

            for (String str : entries) {
                String[] titlename = str.split(",");
                list.add(titlename);
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
            return list;
        }

    }}
