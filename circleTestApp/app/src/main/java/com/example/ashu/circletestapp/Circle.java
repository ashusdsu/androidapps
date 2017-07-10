package com.example.ashu.circletestapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.SparseArray;

/**
 * Created by ashu on 23/2/17.
 */

public class Circle {
    public float startX ;
    public float startY;
    public float radius;
    public Paint paint ;
    public float velX = 0;
    public float velY = 0;
    String color;
    //public SparseArray <Circle> circleSet = new SparseArray<Circle> ();


    public Circle (float sX, float sY, float r, String colorValue)
    {
        color = colorValue;
        startX = sX;
        startY = sY;
        radius = r;
        paint = new Paint();
        paint.setColor(Color.parseColor(colorValue));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);
    }

    public void drawOn(Canvas canvas)
    {
        Log.i("CenterX", Float.toString(startX));
        Log.i("CenterY", Float.toString(startY));

        canvas.drawCircle(startX, startY, radius, paint);
    }
}
