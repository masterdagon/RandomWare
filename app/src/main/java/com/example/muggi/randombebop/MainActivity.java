package com.example.muggi.randombebop;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListNotes3 listNotes;
    public ViewPager viewPager;
    public ViewAdapter viewAdapter;
    public int fileNumber=0;
    private static Context context;
    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        listNotes = new ListNotes3();
        listNotes.loadAll();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        if (((FragmentTwo) viewAdapter.f2).lastListItemSelected < 0) {
            makeToast("No note selected");
        } else {
            Note note = listNotes.getNoteByListPlacement(((FragmentTwo) viewAdapter.f2).lastListItemSelected);
            String [] values = listNotes.getCategories();
            Intent intentEdit = new Intent(this, ActivityDetails.class);
            intentEdit.putExtra("selectedNote", note);
            intentEdit.putExtra("categories",values);
            startActivityForResult(intentEdit, 13372);
        }
    }

    public void deleteNote(View view) {
        ((FragmentTwo) viewAdapter.f2).deleteNote(view);

    }

    public void saveNote(View view) {
        ((FragmentOne) viewAdapter.f1).saveNote();
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
        fileNumber = listNotes.fw.getNewPId();
        String filename = "image_" + fileNumber + ".jpg";
        File image = new File(imagesFolder, filename);
        boolean approved = false;
        while (!approved) {
            if (image.exists()) {
                fileNumber++;
                image = new File(imagesFolder, "image_" + fileNumber + ".jpg");
                listNotes.fw.saveId(fileNumber,false);
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

    public void showPictureInGallery(View view) {
        // get picture URI

        String pictureURI;
        boolean selected = ((FragmentTwo) viewAdapter.f2).somthingSelected();
        if(selected){
            Note note = listNotes.getNoteByListPlacement(((FragmentTwo) viewAdapter.f2).lastListItemSelected);
            pictureURI = note.getPicture();
            if(!pictureURI.equals("NOTSET")){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                File image = new File(pictureURI);
                intent.setDataAndType(Uri.fromFile(image),"image/*");

                startActivity(intent);
            }
        }
        /*
        String pictureURI;
        Note note = listNotes.getNoteByListPlacement(((FragmentTwo) viewAdapter.f2).lastListItemSelected);
        pictureURI = note.getPicture();
        makeToast(pictureURI);
        Intent showImageIntent = new Intent();
        showImageIntent.setAction(Intent.ACTION_VIEW);
        showImageIntent.setDataAndType(Uri.parse(pictureURI), "image/jpeg");
        startActivity(showImageIntent);
        */
    }

    public void sendViaBluetooth(View v) {
//        ((FragmentTwo) viewAdapter.f2).bluetoothsend();
        boolean selected = ((FragmentTwo) viewAdapter.f2).somthingSelected();
        if (selected) {
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            if (btAdapter == null) {
                Toast.makeText(this, "Bluetooth is not Supported", Toast.LENGTH_LONG).show();
            } else {
                enableBluetooth();
            }
        } else {
            Toast.makeText(this, "You need to select a note to Send", Toast.LENGTH_LONG).show();
        }

    }

    public void enableBluetooth() {
        Intent discoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoverIntent, REQUEST_BLU);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (reqCode == 1337) {
            if (resultCode == RESULT_OK) {
                fileNumber++;
                listNotes.fw.setNewPId(fileNumber);
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
                Note note = data.getParcelableExtra("result");

                try {
                    listNotes.updateNote(note);
                    ((FragmentTwo) viewAdapter.f2).initList();
                    ((FragmentTwo) viewAdapter.f2).runThisWhenReturningFromEditView();
                    makeToast("Note edited and saved... hopefully");
                } catch (Exception e) {
                    makeToast("Exception thrown");
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                makeToast("Edit note cancelled");
            } else {
                makeToast("Something went wrong");
            }
        }
        if (resultCode == DISCOVER_DURATION && reqCode == REQUEST_BLU) {
            String fileName = ((FragmentTwo) viewAdapter.f2).bluetoothsend();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("application/zip");
            File f = new File(Environment.getExternalStorageDirectory(), "notes/" + fileName);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

            PackageManager pm = getPackageManager();
            List<ResolveInfo> appList = pm.queryIntentActivities(intent, 0);

            if (appList.size() > 0) {
                String packageName = null;
                String className = null;
                boolean found = false;

                for (ResolveInfo info : appList) {
                    packageName = info.activityInfo.packageName;
                    if (packageName.equals("com.android.bluetooth")) {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }

                }
                if (!found) {
                    Toast.makeText(this, "Bluetooth havnt been found", Toast.LENGTH_LONG).show();
                } else {
                    intent.setClassName(packageName, className);
                    startActivity(intent);
                }
                ((FragmentTwo) viewAdapter.f2).deletebluetoothfile(fileName);
            }
        }
    }

}

