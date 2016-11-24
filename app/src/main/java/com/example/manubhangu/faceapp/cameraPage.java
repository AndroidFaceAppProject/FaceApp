
package com.example.manubhangu.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.bitmap;
import static android.R.attr.data;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class cameraPage extends AppCompatActivity {
    private String pictureImagePath = "";
    private static String TAG = "cameraPage";
    ImageView imgPic2;
    Bitmap bp;
    Uri imageUriToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);
        imgPic2 = (ImageView) findViewById(R.id.imgPic2);
    }

    String mCurrentPhotoPath;

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory("Android/data/com.example.manubhangu.faceapp/files/Pictures");
        storageDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        Log.v(TAG, ""+image.getAbsolutePath());
        mCurrentPhotoPath =  image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.manubhangu.faceapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.v(TAG, "made it");
        if(requestCode == 1)
        {
            Log.v(TAG, ""+mCurrentPhotoPath);
            File imgFile = new File(mCurrentPhotoPath);
            Uri imageUri = Uri.fromFile(imgFile);

            // declare a stream to read the image data from the sd card.
            InputStream inputStream;

            // when reading stream of data it can faill.... alot.
            // we are getting an input stream, based on the uri of the image.
            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                // get a bitmap from the stream
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                // show the image to the user
                imgPic2.setImageBitmap(image);

                imageUriToSend = imageUri;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void sendImage(View view)
    {
        if(imageUriToSend == null){
            Toast.makeText(this, "No Image Selected!", Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, DrawingTest.class);
            i.putExtra("imageUri", imageUriToSend.toString());
            startActivity(i);
        }

    }
}
