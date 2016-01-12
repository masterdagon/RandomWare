package com.example.muggi.randombebop;


import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
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

/**
 * Created by Michael on 07/12/15.
 */
public class FragmentTwo extends Fragment {


    public TextView showText;
    public ListNotes3 listNotes;
    public ListView notelist;
    public int lastListItemSelected = -1;
    public EditText inputText;
    public EditText inputTitle;

    public static final String ARG_OBJECT = "FRAGMENT2";
    public static MainActivity activity;
    public ImageView imageView;

    public View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null) {
            activity = ((MainActivity) getActivity());
            rootView = inflater.inflate(R.layout.fragment_two, container, false);
            notelist = (ListView) rootView.findViewById(R.id.list);
            showText = (TextView) rootView.findViewById(R.id.responseText);
            Bundle args = getArguments();
            TextView tw = (TextView) rootView.findViewById(R.id.fragmentTitle);
            //tw.setText(args.getString("msg"));
            initList();
            imageView = (ImageView) rootView.findViewById(R.id.imagef2);
        }else{
//            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup)rootView.getParent()).removeView(rootView);
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

    public void updateList() {
        listNotes = activity.listNotes;
        if (listNotes.getSize() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listNotes.getTitles());
            notelist.setAdapter(adapter);

        }
    }

    public void initList() {
        listNotes = activity.listNotes;
        if (listNotes.getSize() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listNotes.getTitles());
            notelist.setAdapter(adapter);

        }

        notelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                lastListItemSelected=position;
                System.out.println("this is Posistion "+ position);
                Note node = listNotes.loadFromPosition(position);
                String str = node.getMessage();
                String path = node.getPicture();
                showText.setText(str);

                if(!path.equals("NOTSET")){
                    File imgFile = new  File(path);

                    if(imgFile.exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        imageView.setImageBitmap(myBitmap);

                    }else{
                        imageView.setImageBitmap(null);
                    }
                }else{
                    imageView.setImageBitmap(null);
                }
            }
        });
    }

    public void deleteNote(View view) {
        if (lastListItemSelected != -1) {
            listNotes.deleteNote(lastListItemSelected);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, listNotes.getTitles());
            notelist.setAdapter(adapter);
            lastListItemSelected = -1;
            showText.setText("");
            activity.makeToast("Madam/Sir, your note has been deleted!");
        } else {
            activity.makeToast("Hmm, nothing selected, cancelling delete to prevent anarchy and nihilistic tendencies");
        }


    }
}
