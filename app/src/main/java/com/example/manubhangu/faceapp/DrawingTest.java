package com.example.manubhangu.faceapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

public class DrawingTest extends Activity implements OnClickListener {

    private DrawingView drawView;
    private ImageButton currPaint;
    private ImageButton drawBtn;
    private ImageButton eraseBtn;
    private ImageButton saveBtn;
    private float smallBrush;
    private float mediumBrush;
    private float largeBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_test);

        //retrieve the Linear Layout it is contained within
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);

        // Get the first button and store it as the instance variable
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        // defining our brush sizes stored in dimens.xml
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        // our custom layout element of class where the drawing will be done
        drawView = (DrawingView)findViewById(R.id.drawing);
        drawView.setBrushSize(mediumBrush);


        // getting our drawing button (the brush in the app)
        drawBtn = (ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);

        // getting our erase button
        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
    }

    /*
    This function will handle when the user clicks on a new paint button
    It will check if the paint color select is not the current one and
    will then change the color to the selected ones
     */
    public void paintClicked(View view){

        //user has clicked a paint color that is not the currently selected one
        if(view!=currPaint){
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();

            drawView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;

            drawView.setErase(false);
            drawView.setBrushSize(drawView.getLastBrushSize());
        }
    }

    @Override
    public void onClick(View view)
    {
        // if draw button is clicked (the brush)
        if(view.getId() == R.id.draw_btn)
        {
            // open up a dialog box that lets the user select the brush size
            final Dialog brushEraseDialog = new Dialog(this);
            brushEraseDialog.setTitle("Brush size: ");
            brushEraseDialog.setContentView(R.layout.brush_chooser);

            ImageButton smallBtn = (ImageButton) brushEraseDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    drawView.setErase(false);
                    brushEraseDialog.dismiss();
                }
            });

            ImageButton mediumBtn = (ImageButton) brushEraseDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    drawView.setErase(false);
                    brushEraseDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton) brushEraseDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    drawView.setErase(false);
                    brushEraseDialog.dismiss();
                }
            });

            brushEraseDialog.show();
        }

        if(view.getId() == R.id.erase_btn)
        {
            final Dialog brushEraseDialog = new Dialog(this);
            brushEraseDialog.setTitle("Eraser size: ");
            brushEraseDialog.setContentView(R.layout.brush_chooser);

            final ImageButton smallBtn = (ImageButton) brushEraseDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushEraseDialog.dismiss();
                }
            });

            ImageButton mediumBtn = (ImageButton) brushEraseDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushEraseDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton) brushEraseDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushEraseDialog.dismiss();
                }
            });

            brushEraseDialog.show();
        }

        if(view.getId() == R.id.save_btn)
        {
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save drawing?");
            saveDialog.setMessage("Save drawing to device Gallery?");

            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which)
                {
                    drawView.setDrawingCacheEnabled(true);

                    String imgSaved = MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            drawView.getDrawingCache(),
                            UUID.randomUUID().toString()+ ".png",
                            "drawing");

                    if(imgSaved != null)
                    {
                        Toast savedToast = Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT);
                        savedToast.show();
                    } else {
                        Toast failedToast = Toast.makeText(getApplicationContext(), "Image could not be saved!", Toast.LENGTH_SHORT);
                        failedToast.show();
                    }

                    drawView.destroyDrawingCache();
                }
            });

            saveDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }
}
