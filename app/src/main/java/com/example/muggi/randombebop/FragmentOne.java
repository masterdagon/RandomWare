package com.example.muggi.randombebop;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.muggi.randombebop.R;

import java.io.File;
import java.net.URI;

/**
 * Created by Michael on 07/12/15.
 */
public class FragmentOne extends Fragment {

    public MainActivity activity;
    public static final String ARG_OBJECT = "FRAGMENT1";
    public EditText inputText;
    public EditText inputTitle;
    public boolean attachToPicture = false;
    public Uri lastUri;
    public ListNotes2 listNotes;
    public ImageView imageView;

    public View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        listNotes = ((MainActivity)getActivity()).listNotes;
//
//        inputText = (EditText) getView().findViewById(R.id.noteInput);
//        inputTitle = (EditText) getView().findViewById(R.id.noteTitle);
//        imageView = (ImageView) getView().findViewById(R.id.imagef1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null) {
        activity = ((MainActivity) getActivity());
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        Bundle args = getArguments();
        TextView tw = (TextView) rootView.findViewById(R.id.fragmentTitle);
        listNotes = activity.listNotes;

        inputTitle = (EditText) rootView.findViewById(R.id.noteTitle);
        imageView = (ImageView) rootView.findViewById(R.id.imagef1);
        inputText = (EditText) rootView.findViewById(R.id.noteInput);
        }else{
//            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    public static Fragment newInstance(String text) {

        Fragment f = new FragmentOne();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void setAttachToPicture(boolean state) {
        attachToPicture = state;
        if (state) {
            File imgFile = new File(lastUri.getPath());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);

            }
        }
    }

    public void setLastUri(Uri uri) {
        lastUri = uri;
    }

    public void saveNote() {


        String str = inputText.getText().toString();
        String title = inputTitle.getText().toString();
        if (str.length() != 0) {
            Note note = new Note(str);
            if (title.length() > 0) {
                note.setName(title);
            }
            if (attachToPicture) {
                note.setPicture(lastUri.getPath());
                attachToPicture = false;
            }
            listNotes.addNoteToList(note);
            inputText.setText("");
            inputTitle.setText("");
            imageView.setImageBitmap(null);
            activity.makeToast("I exist to obey. Your note has been saved");
        } else if (str.length() == 0 && title.length() != 0) {
            activity.makeToast("I am terribly sorry for the interruption, but it seems like you forgot to write your note");
        }
        else if (title.length() == 0 && str.length() == 0) {
            activity.makeToast("Hmm. Are you really sure you have something to remember?");
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        listNotes = activity.listNotes;
//        inputText = (EditText) this.getView().findViewById(R.id.noteInput);
//        inputTitle = (EditText) this.getView().findViewById(R.id.noteTitle);
//    }
}
