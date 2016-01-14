package com.example.muggi.randombebop;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Michael on 14/01/2016.
 */
public class NoteZipper {

    private final String basePath = Environment.getExternalStorageDirectory().getPath() + "/Notes/";
    private final int BUFFER = 2048;
    private final String ZIP_NOTE = "#note.txt";
    private final String ZIP_PICTURE = "#picture.jpg";

    public Boolean zip(Note note, String zipFileName) {

        int noteId = note.getId();
        Boolean hasPicture = note.hasPicture();
        String picturePath;

        try {
            BufferedInputStream origin = null;
            File zipFile = new File(basePath,zipFileName);
            FileOutputStream dest = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            /*
            *
            *   Saving the notefile
            * */
            FileInputStream fi = new FileInputStream(new File(basePath, noteId + ".txt"));
            origin = new BufferedInputStream(fi, BUFFER);

            ZipEntry entry = new ZipEntry(ZIP_NOTE);

            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();

            /*
            *
            *   Now to save the picture
            * */
            if (hasPicture) {
                picturePath = note.getPicture();
                FileInputStream pfi = new FileInputStream(new File(picturePath));
                origin = new BufferedInputStream(pfi, BUFFER);

                ZipEntry picEntry = new ZipEntry(ZIP_PICTURE);

                out.putNextEntry(picEntry);

                int picCount;
                while ((picCount = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, picCount);
                }
                origin.close();
            }

            out.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return false;
        } catch (IOException IOe) {
            IOe.printStackTrace();
            return false;
        }
        return true;
    }

    public void unzip(String zipFileName) {
        byte data[] = new byte[BUFFER];
        try {
            File zipFile = new File(basePath,zipFileName);
            FileInputStream fin = new FileInputStream(zipFile);
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fin, BUFFER));

            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                FileOutputStream fout;
                if (ze.getName().equals(ZIP_NOTE)) {

                    fout = new FileOutputStream(basePath + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    zin.closeEntry();
                    fout.close();
                } else if (ze.getName().equals(ZIP_PICTURE)) {

                    File img = new File(basePath + "NotePictures/" + ze.getName());
                    fout = new FileOutputStream(img);
                    BufferedOutputStream dest = new BufferedOutputStream(fout, BUFFER);

                    int picCount;
                    while ((picCount = zin.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, picCount);
                    }

                    zin.closeEntry();
                    dest.close();
                    fout.close();
                }
            }
            zin.close();
            fin.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException IOe) {
            IOe.printStackTrace();
        }
    }
}
