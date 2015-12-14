package com.example.muggi.randombebop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public EditText inputTitle;
    public EditText inputText;
    public TextView showText;
    public ListNotes2 listNotes;
    public ListView notelist;
    public int lastListItemSelected = -1;
    private static Context context;

    Uri lastUri;
    boolean attachToPicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        listNotes = new ListNotes2(context);
        listNotes.loadfile();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        inputTitle = (EditText) findViewById(R.id.noteTitle);
        inputText = (EditText) findViewById(R.id.noteInput);
        showText = (TextView) findViewById(R.id.responseText);

        notelist = (ListView) findViewById(R.id.list);
        notelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                lastListItemSelected = position;
                String str = listNotes.getNotes().get(position).getMessage();
                showText.setText(str);
            }
        });
        if (listNotes.getSize() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotes.getTitles());
            notelist.setAdapter(adapter);

        }


    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void DeleteNote(View view) {
        if (lastListItemSelected != -1) {
            listNotes.deleteNote(lastListItemSelected);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotes.getTitles());
            notelist.setAdapter(adapter);
            lastListItemSelected = -1;
            showText.setText("");
            makeToast("Madam/Sir, your note has been deleted!");
        } else {
            makeToast("Hmm, nothing selected, cancelling delete to prevent anarchy and nihilistic tendencies");
        }


    }

    public void saveNote(View view) {
        String str = inputText.getText().toString();
        String title = inputTitle.getText().toString();
        if (str.length() != 0) {
            Note note = new Note(str);
            if(title.length()>0){
                note.setName(title);
            }
            if(attachToPicture){
                note.setPicture(lastUri.toString());
                attachToPicture = false;
            }
            listNotes.addNoteToList(note);
            if (listNotes.getSize() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotes.getTitles());
                notelist.setAdapter(adapter);

            }
            inputText.setText("");
            inputTitle.setText("");
            makeToast("I exist to obey. Your note has been saved");
        } else if (str.length() == 0 && title.length() != 0) {
            makeToast("I am terribly sorry for the interruption, but it seems like you forgot to write your note");
        }
//         else if (title.length() == 0 && str.length() != 0) {
//           makeToast("Sorry to disturb, but it seems like you forgot to give your note a name");
//        }
    else if (title.length() == 0 && str.length() == 0) {
            makeToast("Hmm. Are you really sure you have something to remember?");
        }
    }

    public void makeToast(String info) {
        Context context = getApplicationContext();
        CharSequence text = info;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void takePicture(View view) {
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(),"Notes/NotePictures");
        if(!imagesFolder.exists()){
            imagesFolder.mkdirs();
        }
        int fileNumber = 1;
        String filename = "image_" + fileNumber + ".jpg";
        File image = new File(imagesFolder,filename);
        boolean approved = false;
        while(!approved) {
            if (image.exists()) {
                fileNumber++;
                filename = "image_" + fileNumber + ".jpg";
                image = new File(imagesFolder, "image_" + fileNumber + ".jpg");
            }
            if(!image.exists()){
                approved = true;
            }
        }


        Uri uriSavedImage = Uri.fromFile(image);
        lastUri = uriSavedImage;
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT,uriSavedImage);
        startActivityForResult(imageIntent, 1337);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent intent) {
        if (reqCode == 1337) {
            if (resultCode == RESULT_OK) {
                makeToast("Image saved. Write your note");
                attachToPicture = true;
            }else if(resultCode == RESULT_CANCELED){
                makeToast("Image capture cancelled");
            }else{
                makeToast("Something went wrong");
            }
        }


        //add something do attach note to file, filename is saved in var lastFilename.
    }


}
