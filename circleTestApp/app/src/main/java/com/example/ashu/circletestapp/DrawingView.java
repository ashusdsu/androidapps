package com.example.ashu.circletestapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;


public class DrawingView extends View implements View.OnTouchListener {

    private boolean swipeInProgress = false;
    private boolean isMove = false;
    private float startX;
    private float startY;
    private float midX = 0;
    private float midY = 0;
    private float canvasH;
    private float canvasW;
    private float canvasUpperH;
    private float canvasLeftW;
    private float canvasLowerH;
    private float canvasRightW;
    private float endX;
    private float endY;
    private float velX;
    private float velY;
    private float movX;
    private float movY;
    private float movRadius;
    private float limitHeight;
    public static int modeSelect = 1;
    public static String colorValue = "BLACK" ;
    private float r1;
    VelocityTracker v;


    //private int mode = 1;
    private boolean upSwitch = false;
    private List<Circle> circleSet = new ArrayList<>();
    private List<Circle> circleMove = new ArrayList<>();

    private final Paint paint = new Paint();
    private final Paint movpaint = new Paint();


    private float radius;
    public DrawingView(Context context) {
        super(context);
    }

    public DrawingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public void setModeAndColor()
    {
        MainActivity ma = new MainActivity();
        colorValue = ma.getcolorValue();
        modeSelect = ma.getmode();
    }
    public boolean onTouch(View v, MotionEvent event)
    {

        setModeAndColor();

        Log.i("Touchmode", String.valueOf(modeSelect));
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                return handleActionDown(event);
            case MotionEvent.ACTION_MOVE:
                return handleActionMove(event);
            case MotionEvent.ACTION_UP:
                return handleActionUp(event);
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                swipeInProgress = false;
                return false;
        }
        return false;

    }

    private boolean handleActionDown(MotionEvent event) {
        //Log.i("Action", "Action Down Function");
        //Log.i("mode",String.valueOf(modeSelect));
        if (modeSelect == 1) {
            swipeInProgress = true;
            startX = event.getX();
            startY = event.getY();

            canvasUpperH = startY;
            canvasLeftW = startX;
            canvasLowerH = canvasH - startY;
            canvasRightW = canvasW - startX;

        }
        else if(modeSelect == 2)
        {
            //Log.i("actionDown", "Delete");
            startX = event.getX();
            startY = event.getY();
            Iterator<Circle> circleIt = circleSet.iterator();
            while (circleIt.hasNext())
            {
                Circle c = circleIt.next();
                float d =(float) Math.sqrt(Math.pow(c.startX-startX,2) + Math.pow(startY-c.startY,2));
                   //Log.i("DistanceUp", String.valueOf(d));
                if (d <= c.radius)
                {
                    circleIt.remove();

                }
            }
            invalidate();


        }
        else if (modeSelect == 3)
        {
            v = VelocityTracker.obtain();
            v.addMovement(event);
            startX = event.getX();
            startY = event.getY();

            Iterator <Circle> circleIt = circleSet.iterator();

            while (circleIt.hasNext())
            {
                Circle c = circleIt.next();
                double d = Math.pow(c.startX-startX,2) + Math.pow(startY-c.startY,2);
                d = Math.sqrt(d);
                float finalDist = (float) d;
                //Log.i("DistanceUp", String.valueOf(finalDist));
                if (finalDist < c.radius)
                {
                    circleMove.add(c);
                }
            }

            //Log.i("actionDownMove", String.valueOf(circleMove.size()));

            //Log.i("actionDownMove","Move");
        }

        return true;
    }
    private boolean handleActionMove (MotionEvent event)
    {
        //Log.i("MoveAction", String.valueOf(modeSelect));

        if(modeSelect == 1) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4f);
            paint.setColor(Color.parseColor(colorValue));
            swipeInProgress = true;
            midX = event.getX();
            midY = event.getY();
            r1 = (float)Math.sqrt(Math.pow(startX-midX,2)+Math.pow(startY-midY,2));

            invalidate();
        }
        else if (modeSelect == 2)
        {
            swipeInProgress = true;
          //  Log.i("actionMove","Delete");

        }
        else if (modeSelect == 3)
        {
            v.addMovement(event);
            swipeInProgress = true;

//            Log.i("actionMove", "Move");



        }

        return true;
    }

    private boolean handleActionUp(MotionEvent event) {
        if (!swipeInProgress) return false;
//        Log.i("UpAction", String.valueOf(modeSelect));


        if (modeSelect == 1) {
            endX = event.getX();
            endY = event.getY();
  //          Log.i("rew", "x swipe distance " + (endX - startX));
    //        Log.i("rew", "y swipe distance " + (endY - startY));
            float dx = endX - startX;
            float dy = endY - startY;

            double rad = Math.sqrt((double) (dx * dx) + (double) (dy * dy));
            radius = (float) rad;
            if (!(rad<canvasLowerH && r1<canvasLowerH && r1 < canvasRightW && r1<canvasUpperH && r1 < canvasLeftW))
            {

                radius = min(canvasLowerH, canvasUpperH, canvasLeftW, canvasRightW);
            }

            circleSet.add(new Circle(startX, startY, radius, colorValue));

            swipeInProgress = false;
            invalidate();
        }
        else if (modeSelect == 2)
        {
            endX = event.getX();
            endY = event.getY();
//            Log.i("UpAction", "Delete");
            swipeInProgress = false;
            Iterator<Circle> circleIt = circleSet.iterator();
            while (circleIt.hasNext())
            {
                Circle c = circleIt.next();
                double d = Math.pow(c.startX-startX,2) + Math.pow(startY-c.startY,2);
                d = Math.sqrt(d);
                float finalDist = (float) d;
  //              Log.i("DistanceUp", String.valueOf(finalDist));
                if (finalDist < c.radius)
                {
                    circleIt.remove();
                }
            }
            invalidate();

        }
        else if (modeSelect == 3)
        {
    //        Log.i("UpAction", "Move");
            v.computeCurrentVelocity(5);
      //      Log.i("rew", "Xvel"+ v.getXVelocity()+ "Yvel" + v.getYVelocity());
            velX = v.getXVelocity();
            velY = v.getYVelocity();


            Iterator<Circle> movIt = circleMove.iterator();
            while(movIt.hasNext()) {
                Iterator<Circle> cirIt = circleSet.iterator();
                Circle m = movIt.next();
                while (cirIt.hasNext())
                {
                    Circle c = cirIt.next();
                    if (c.radius == m.radius)
                    {

                        c.velX = velX;
                        c.velY = velY;
                    }

                }
                movIt.remove();

            }
            isMove = true;
            v.recycle();
            v = null;
            invalidate();
        }
        return true;
    }


  protected void onDraw(Canvas canvas) {
      //canvas.drawColor(Color.RED);
      canvasH = canvas.getHeight();
      canvasW = canvas.getWidth();
      if (modeSelect == 1) {
//          Log.i("mode1","inside mode 1");
          if(swipeInProgress) {
              if (r1 < canvasLowerH && r1 < canvasLowerH && r1 < canvasRightW && r1 < canvasUpperH && r1 < canvasLeftW) {
                  canvas.drawCircle(startX, startY, r1, paint);
              } else {
                  r1 = min(canvasLowerH, canvasUpperH, canvasLeftW, canvasRightW);
                  canvas.drawCircle(startX, startY, r1, paint);
              }

          }
      }

      for (Circle circle : circleSet) {
          //      Log.i("multiple circles", String.valueOf(circle.startX)+" "+String.valueOf(circle.startY));
          circle.drawOn(canvas);
      }

      if (modeSelect == 3) {
  //        Log.i("inside mode", String.valueOf(modeSelect));

          move();
          setModeAndColor();
    //      Log.i("setting mode", "set");
          r1 = 0;
      }
           else {
      //        Log.i("else mode = 3", "inside");
              Iterator<Circle> movrIt = circleMove.iterator();
              while (movrIt.hasNext()) {
                  Circle movc = movrIt.next();
                  movrIt.remove();
              }
              /*Iterator<Circle> cirIt = circleSet.iterator();
              while (cirIt.hasNext()) {
                  Circle cir = cirIt.next();
                  cir.velX = 0;
                  cir.velY = 0;
              }*/

          }







      //Log.i("onDraw", String.valueOf(circleSet.size()));
    }


        public float min(float x1, float x2, float x3, float x4)
        {
            float m = 100000;
            if (m>x1)
            {
                m = x1;
            }
            if (m>x2)
            {
                m = x2;
            }
            if (m>x3)
            {
                m = x3;
            }
            if (m>x4)
            {
                m = x4;
            }

            return m;
        }

        private void collisionChange(Circle c)
    {

        /*if (xIsOutOfBounds(c)) {
            c.velX = (c.velX/2) * -1;
            Log.i("velX and velY ", String.valueOf(c.velX) + " "+ String.valueOf(c.velY));
        }
        if (yIsOutOfBounds(c)) {
            c.velY = (c.velY/2) * -1;
            Log.i("velX and velY ", String.valueOf(c.velX) + " "+ String.valueOf(c.velY));
        }*//*
        if (xIsOutOfBounds(c) || yIsOutOfBounds(c))
        {
            c.velX = (c.velX/2)*(-1);
        c.velY = c.velY/2);

        }*/

        //Log.i("collision", "collision application" );
        if (xIsOutOfBounds(c)) c.velX = c.velX* -1;
        if (yIsOutOfBounds(c)) c.velY = c.velY* -1;
    }

    private boolean xIsOutOfBounds(Circle c)
    {   float x = c.startX;
        if (x - c.radius <= 0) {
            Log.i("leftedge",String.valueOf(x));
            return true;
        }
        if (x+c.radius >= canvasW)
        {
            Log.i("rightedge",String.valueOf(x));
            return true;}
        return false;
    }

    private boolean yIsOutOfBounds(Circle c)
    {
        float y = c.startY;
        if (y - c.radius <= 0) {
            Log.i("top", String.valueOf(y));
            return true;

        }
        if (y + c.radius >= canvasH){
            Log.i("Bottom", String.valueOf(y));
            return true;}
        return false;
    }


        private void move()
        {
            Iterator<Circle> circleIt = circleSet.iterator();
            while (circleIt.hasNext())
            {
                Circle c = circleIt.next();
                collisionChange(c);

                c.startX += c.velX;
                c.startY += c.velY;

              Log.i(" Start X and Start Y",String.valueOf(c.startX) + " " + String.valueOf(c.startY));

                if (modeSelect != 3)
                {
                    Log.i("cancel", "cancel");
                    return ;

                }

                invalidate();
            }
        }


}

