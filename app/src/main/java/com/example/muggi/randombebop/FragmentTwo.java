package com.example.muggi.randombebop;


import android.bluetooth.BluetoothAdapter;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muggi.randombebop.R;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by Michael on 07/12/15.
 */
public class FragmentTwo extends Fragment {


    public TextView showText;
    public ListNotes3 listNotes;
    public ListView notelist;
    public int lastListItemSelected = -1;
    public static final String ARG_OBJECT = "FRAGMENT2";
    public static MainActivity activity;
    public ImageView imageView;

    public View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = ((MainActivity) getActivity());
        rootView = inflater.inflate(R.layout.fragment_two, container, false);

        Bundle args = getArguments();
//        TextView tw = (TextView) rootView.findViewById(R.id.fragmentTitle);
//        tw.setText(args.getString(""));
        notelist = (ListView) rootView.findViewById(R.id.list);
        showText = (TextView) rootView.findViewById(R.id.responseText);
        imageView = (ImageView) rootView.findViewById(R.id.imagef2);
        listNotes = activity.listNotes;
        initList();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = ((MainActivity) getActivity());
        notelist = (ListView) rootView.findViewById(R.id.list);
        showText = (TextView) rootView.findViewById(R.id.responseText);
        imageView = (ImageView) rootView.findViewById(R.id.imagef2);
        listNotes = activity.listNotes;
        initList();
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    public static Fragment newInstance(String text) {
        Fragment f = new FragmentTwo();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public String bluetoothsend() {
        Note note = listNotes.getNotePosition(lastListItemSelected);
        lastListItemSelected = -1;
        listNotes.fw.zipFiles(note);
        return "#" + note.getId() + ".zip";
    }

    public boolean deletebluetoothfile(String name) {
        return listNotes.fw.deleteZip(name);
    }

    public boolean somthingSelected() {
        if (lastListItemSelected == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void initList() {
        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listNotes.getTitles());

        notelist.setAdapter(adapter);

        notelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                lastListItemSelected = position;
                Note node = listNotes.getNotePosition(position);
                String str = node.getMessage();
                String path = node.getPicture();
                showText.setText(str);

                if (!path.equals("NOTSET")) {
                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        imageView.setImageBitmap(myBitmap);

                    } else {
                        imageView.setImageBitmap(null);
                    }
                } else {
                    imageView.setImageBitmap(null);
                }
            }
        });
        System.out.println("Size of array: " + listNotes.notes.size());
    }

    public void runThisWhenReturningFromEditView() {
        Note node = listNotes.getNoteByListPlacement(lastListItemSelected);
        String str = node.getMessage();
        showText.setText(str);
    }

    public void deleteNote(View view) {
        if (lastListItemSelected != -1) {
            boolean deleted = listNotes.deleteNote(lastListItemSelected, false);
            initList();
            lastListItemSelected = -1;
            showText.setText("");
            if (deleted) {
                activity.makeToast("Madam/Sir, your note has been deleted!");
            } else {
                activity.makeToast("Madam/Sir, Somthing went wrong!");
            }
        } else {
            activity.makeToast("Hmm, nothing selected, cancelling delete to prevent anarchy and nihilistic tendencies");
        }


    }
}
