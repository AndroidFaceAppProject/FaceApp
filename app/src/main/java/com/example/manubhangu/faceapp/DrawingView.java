package com.example.manubhangu.faceapp;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by Manu Bhangu on 2016-11-08.
 */



public class DrawingView extends View {
    // === VARIABLES === //
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint;
    private Paint canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private float brushSize;
    private float lastBrushSize;
    private boolean erase = false;


    public DrawingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing()
    {
        // initalize the brush size to be medium
        brushSize = getResources().getInteger(R.integer.medium_size);

        // keep a temp record of whatever the last size used was so we
        // can reference back to it after an erase call
        lastBrushSize = brushSize;

        //get drawing area setup for interaction
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setDrawCanvas(Bitmap bp)
    {
        canvasBitmap = bp;
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);
        //canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX(); // user touch coordinates x
        float touchY = event.getY(); // user touch coordinates y

        switch (event.getAction()) {
            // User presses down on screen here
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            // Draws a continous line from when the screen is touched to when the finger is lifted
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            // When the user lifts their finger off the screen
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();   // cause onDraw to execute
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        //set color
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newSize)
    {
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public float getLastBrushSize()
    {
        return lastBrushSize;
    }

    public void setLastBrushSize(float lastSize)
    {
        lastBrushSize = lastSize;
    }

    public void setErase(boolean isErase)
    {
        erase = isErase;
        if(erase)
        {
            drawPaint.setColor(Color.WHITE);
        }
    }
}
