package com.example.muggi.randombebop;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public ListNotes3 listNotes;
    public ViewPager viewPager;
    public ViewAdapter viewAdapter;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        listNotes = new ListNotes3();
        listNotes.loadAll();

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
        viewPager = (ViewPager) findViewById(R.id.pager);
        try {
            viewAdapter = new ViewAdapter(getSupportFragmentManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(viewAdapter);
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

    public void editButtonClick(View view) {
        //((FragmentTwo)viewAdapter.f2).startEditView();
        if (((FragmentTwo) viewAdapter.f2).lastListItemSelected < 0) {
            makeToast("No note selected");
        } else {
            Note note = listNotes.getNote(((FragmentTwo) viewAdapter.f2).lastListItemSelected);
            note = listNotes.loadNote(note);
            int index = ((FragmentTwo) viewAdapter.f2).lastListItemSelected;
            Intent intentEdit = new Intent(this, ActivityDetails.class);
            intentEdit.putExtra("selectedNote", note);
            intentEdit.putExtra("index", index);
            makeToast("index = " + index);
            startActivityForResult(intentEdit, 13372);
        }
    }

    public void deleteNote(View view) {
        ((FragmentTwo) viewAdapter.f2).deleteNote(view);

    }


    public void saveNote(View view) {
        ((FragmentOne) viewAdapter.f1).saveNote(-1);
        ((FragmentTwo) viewAdapter.f2).initList();
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
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Notes/NotePictures");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        int fileNumber = 1;
        String filename = "image_" + fileNumber + ".jpg";
        File image = new File(imagesFolder, filename);
        boolean approved = false;
        while (!approved) {
            if (image.exists()) {
                fileNumber++;
                image = new File(imagesFolder, "image_" + fileNumber + ".jpg");
            }
            if (!image.exists()) {
                approved = true;
            }
        }


        Uri uriSavedImage = Uri.fromFile(image);
        ((FragmentOne) viewAdapter.f1).setLastUri(uriSavedImage);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(imageIntent, 1337);
    }

    public void showPictureInGallery(View view){
        // get picture URI
        String pictureURI;
        Note note = listNotes.getNote(((FragmentTwo) viewAdapter.f2).lastListItemSelected);
        pictureURI = note.getPicture();

        Intent showImageIntent = new Intent(Intent.ACTION_VIEW);
        startActivity(showImageIntent);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent intent) {
        if (reqCode == 1337) {
            if (resultCode == RESULT_OK) {
                makeToast("Image saved. Write your note");
                ((FragmentOne) viewAdapter.f1).setAttachToPicture(true);
            } else if (resultCode == RESULT_CANCELED) {
                makeToast("Image capture cancelled");
            } else {
                makeToast("Something went wrong");
            }
        }
        if (reqCode == 13372) {
            if (resultCode == RESULT_OK) {
                Note note = intent.getParcelableExtra("selectedNote");
                int index = intent.getIntExtra("index", -1);

                try {
                    listNotes.notes.remove(index);
                    listNotes.notes.add(index, note);
                    listNotes.saveOldNote((note));
                    ((FragmentTwo) viewAdapter.f2).initList();
                    makeToast("Note edited and saved... hopefully");
                } catch (Exception e) {
                    makeToast("Something is wrong with the index returned");
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                makeToast("Edit note cancelled");
            } else {
                makeToast("Something went wrong");
            }
        }
    }


}
