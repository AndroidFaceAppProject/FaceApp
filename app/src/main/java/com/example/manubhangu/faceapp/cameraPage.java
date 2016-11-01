
package com.example.manubhangu.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.R.attr.bitmap;
import static android.R.attr.data;

public class cameraPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);
    }

    public void drawingPageLoad(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(resultCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");

        Intent i = new Intent(this, Drawing.class);
        i.putExtra("bitmapimage", bp);
        startActivity(i);

    }
}
