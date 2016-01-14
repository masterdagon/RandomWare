package com.example.muggi.randombebop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

public class ActivityDetails extends AppCompatActivity {

    public EditText title;
    public EditText message;
    public Spinner category;
    public ImageView imageView;
    public Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = (EditText) findViewById(R.id.detailsTitle);
        message = (EditText) findViewById(R.id.detailsMessage);
        category = (Spinner) findViewById(R.id.spinner1);
        imageView = (ImageView) findViewById(R.id.detailsImageView);
        Intent intent = getIntent();
        note = intent.getParcelableExtra("selectedNote");
        title.setText(note.getName());
        message.setText(note.getMessage());
        String[] values = intent.getStringArrayExtra("categories");
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        category.setAdapter(LTRadapter);
        boolean notDone = true;
        while(notDone){
            for(int i = 0; i<values.length;i++){
                if(values[i].equals(note.getCategory())){
                    category.setSelection(i);
                    notDone = false;
                }
            }
        }
        String path = note.getPicture();
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

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();

    }

    public void makeToast(String info) {
        Context context = getApplicationContext();
        CharSequence text = info;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void save(View view) {
        note.setName(title.getText().toString());
        note.setMessage(message.getText().toString());
        note.setCategory(category.getSelectedItem().toString());
        makeToast(""+category.getSelectedItem().toString());
        Intent intentToReturn = new Intent(this, MainActivity.class);
        intentToReturn.putExtra("result", note);
        setResult(RESULT_OK, intentToReturn);
        finish();
    }

}
