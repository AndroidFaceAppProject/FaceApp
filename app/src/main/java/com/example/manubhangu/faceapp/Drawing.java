package com.example.manubhangu.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Drawing extends AppCompatActivity {
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        iv = (ImageView) findViewById(R.id.imageView);
        Intent i = getIntent();
        Uri myUri = Uri.parse(i.getStringExtra("imageUri"));

        // declare a stream to read the image data from the sd card.
        InputStream inputStream;

        // when reading stream of data it can faill.... alot.
        // we are getting an input stream, based on the uri of the image.
        try {
            inputStream = getContentResolver().openInputStream(myUri);

            // get a bitmap from the stream
            Bitmap image = BitmapFactory.decodeStream(inputStream);

            // show the image to the user
            iv.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
        }
    }
}