package com.example.muggi.randombebop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Michael on 07/12/15.
 */
public class FragmentOne extends Fragment {

    public MainActivity activity;
    public static final String ARG_OBJECT = "FRAGMENT1";
    public EditText inputText;
    public EditText inputTitle;
    public Spinner inputCategory;
    public boolean attachToPicture = false;
    public Uri lastUri;
    public ListNotes3 listNotes;
    public ImageView imageView;
    public static Fragment f;
    public String[] test = new String[]{"test1", "test2"};
    public View rootView;
    private int lastSpinnerSelection = -1;

    public FragmentOne() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();

        activity = ((MainActivity) getActivity());
        listNotes = activity.listNotes;
//        ((TextView) activity.findViewById(R.id.fragmentTitle)).setText("");
        inputTitle = (EditText) activity.findViewById(R.id.noteTitle);
        imageView = (ImageView) activity.findViewById(R.id.imagef1);
        inputText = (EditText) activity.findViewById(R.id.noteMsg);

        String[] values = listNotes.getCategories();
        inputCategory = (Spinner) activity.findViewById(R.id.spinner2);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        inputCategory.setAdapter(LTRadapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = ((MainActivity) getActivity());
        rootView = inflater.inflate(R.layout.fragment_one, container, false);
        Bundle args = getArguments();
        TextView tw = (TextView) rootView.findViewById(R.id.fragmentTitle);
        listNotes = activity.listNotes;
        inputTitle = (EditText) rootView.findViewById(R.id.noteTitle);
        imageView = (ImageView) rootView.findViewById(R.id.imagef1);
        inputText = (EditText) rootView.findViewById(R.id.noteMsg);
        inputTitle.setText("");
        inputCategory = (Spinner) rootView.findViewById(R.id.spinner2);
        imageView.setImageBitmap(null);
        inputText.setText("");
/**
 //THIS CODE WAS SUPPOSED TO REMEMBER SELECTION ON SPINNER; BUT FOR SOME REASON THE FRAGMENT STILL COUNTS
 //AS ACTIVE WHEN SWITCHING TO ANOTHER FRAGMENT, AND THE SELECTION IS SET TO ZERO

         inputCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
//                 activity.makeToast("" + inputCategory.getSelectedItemPosition());
                 if (inputCategory.getSelectedItemPosition() > 0) {
                     lastSpinnerSelection = inputCategory.getSelectedItemPosition();
                 } else if (inputCategory.getSelectedItemPosition() == 0) {
                     if (isVisible()) {
                         lastSpinnerSelection = 0;
                         activity.makeToast("Last selection set to zero");
                     }
                 }
                 inputCategory.setSelection(lastSpinnerSelection);
             }

             @Override
             public void onNothingSelected(AdapterView<?> arg0) {
             }
         });
 */
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
        FragmentOne f = new FragmentOne();
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
        System.out.println(str);
        if (str.length() != 0) {
            Note note = new Note(-1);
            note.setMessage(str);
            if (title.length() > 0) {
                note.setName(title);
            }
            if (attachToPicture) {
                note.setPicture(lastUri.getPath());
                attachToPicture = false;
            }
            note.setCategory(inputCategory.getSelectedItem().toString());
            listNotes.saveNewNote(note);
            inputText.setText("");
            inputTitle.setText("");
            inputCategory.setSelection(0);
            imageView.setImageBitmap(null);
            activity.makeToast("I exist to obey. Your note has been saved");
        } else if (str.length() == 0 && title.length() != 0) {
            activity.makeToast("I am terribly sorry for the interruption, but it seems like you forgot to write your note");
        } else if (title.length() == 0 && str.length() == 0) {
            activity.makeToast("Hmm. Are you really sure you have something to remember?");
        }
    }
}
