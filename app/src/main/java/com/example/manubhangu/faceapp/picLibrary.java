package com.example.manubhangu.faceapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.R.attr.bitmap;

public class picLibrary extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imgPicture;
    public Uri imageUriToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_library);

        // get a reference to an imageview
        imgPicture = (ImageView) findViewById(R.id.imgPicture);


    }

    public void onUseImageClick(View v){

        if(imageUriToSend == null){
            Toast.makeText(this, "No Image Selected!", Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, DrawingTest.class);
            i.putExtra("imageUri", imageUriToSend.toString());
            startActivity(i);
        }

    }

    public void onOpenGalleryClick(View v){
        // invoke image gallery using implicit intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        //where we want to find the data
        File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirPath = pictureDir.getPath();

        // get uri representation
        Uri data = Uri.parse(pictureDirPath);

        // set data and type, get all image types with wildcard
        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_GALLERY_REQUEST){
            // everything processed successfully...
            if(resultCode == RESULT_OK){
                // we are hearing back from image gallery

                // address path on the phones sd card
                Uri imageUri = data.getData();

                // declare a stream to read the image data from the sd card.
                InputStream inputStream;

                // when reading stream of data it can faill.... alot.
                // we are getting an input stream, based on the uri of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    // show the image to the user
                    imgPicture.setImageBitmap(image);

                    imageUriToSend = imageUri;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
