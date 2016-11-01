package com.example.manubhangu.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takePicutre(View view)
    {
        Intent picintent = new Intent(this, cameraPage.class);
        startActivity(picintent);

    }

    public void picLibrary(View view)
    {
        Intent picLibrary = new Intent(this, picLibrary.class);
        startActivity(picLibrary);
    }
}
