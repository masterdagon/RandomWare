package com.example.muggi.randombebop;

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
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class ActivityDetails extends AppCompatActivity {

    public EditText title;
    public EditText message;
    public EditText category;
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
        category = (EditText) findViewById(R.id.detailsCategory);
        imageView = (ImageView) findViewById(R.id.detailsImageView);
        Intent intent = getIntent();
        note = intent.getParcelableExtra("selectedNote");
        title.setText(note.getName());
        message.setText(note.getMessage());
        category.setText(note.getCategory());
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


    public void save(View view) {
            note.setName(title.getText().toString());
            note.setMessage(message.getText().toString());
            note.setCategory(category.getText().toString());
            Intent intentToReturn = new Intent(this, MainActivity.class);
            intentToReturn.putExtra("result", note);
            setResult(RESULT_OK, intentToReturn);
            finish();
        }

    }
