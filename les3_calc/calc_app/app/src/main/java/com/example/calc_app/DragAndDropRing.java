package com.example.calc_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

public class DragAndDropRing implements View.OnTouchListener {
    public static final int accelPerNum = 25;
    MotionEvent.PointerCoords p1, p2;
    ImageView ringImage;
    TextView expressionText;
    float rotation = 0.0f;
    float startK, endK;
    int startQ = 1, endQ = 1;
    final int threshold = 20;
    int prevN = 0;
    Context appContext;

    //280 - is the 3 quarters of an XY coord axis.
    // 10 - is the count of numbers
    final int angleSize = 280 / 10;

    DragAndDropRing(ImageView _ringImage, TextView expressionView, Context context)
    {
        ringImage = _ringImage;
        expressionText = expressionView;
        p1 = new MotionEvent.PointerCoords();
        p2 = new MotionEvent.PointerCoords();
        int coords[] = new int[2];
        ringImage.getLocationInWindow(coords);
        Log.d("image", "image xy == " + coords[0] + ":" + coords[1]);
        appContext = context;
    }

    private int getNumFromRotation(float rotation)
    {
//        Log.d("Rotation", "cur rotation == " + rotation);
        Log.d("num", "prevN == " + prevN + " curN == " + (((((int)rotation - threshold) / angleSize) + 1) % 10));
        return (((((int)rotation - threshold) / angleSize) + 1) % 10);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        float x1, x2, y1, y2, _x, _y;
        float[] f = new float[9];

        ringImage.getImageMatrix().getValues(f);
        final Drawable d = ringImage.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y] * 1.20f; // i don't know why, but it's necessary :(

        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        _x = event.getX() - (actW / 2);
        _y = (-event.getY() + (actH));

//        Log.d("Coord", " " + _x + " : " + _y);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                p1.x = p2.x = _x;
                p1.y = p2.y = _y;
                rotation = 0;
                prevN = -1;

                startQ = getQuarter(_x, _y);
//                Log.d("Quarter", "Start quarter is at " + startQ);

                startK = _y / _x;

                if (startQ == 4)
                    return true;

                break;
            case MotionEvent.ACTION_MOVE:
                if (startQ == 4)
                {
                    p1.x = p2.x = _x;
                    p1.y = p2.y = _y;

                    startQ = getQuarter(_x, _y);

                    startK = _y / _x;
                    return true;
                }
                p2.x = _x;
                p2.y = _y;
                break;
            case MotionEvent.ACTION_UP:
//                Log.d("Motion", "Got touch release");

                if (handleRelease()) return true;

                break;
            default:
//                Log.d("Motion", "Got drag event default " + event.getAction());
                break;
        }
        x1 = p1.x;
        x2 = p2.x;
        y1 = p1.y;
        y2 = p2.y;

        endQ = getQuarter(x2, y2);
//        Log.d("Quarter", "Quarter is " + endQ);
        endK = p2.y / p2.x;

        rotation = (float) Math.acos(((x1*x2) + (y1*y2)) / (Math.sqrt((x1*x1) + (y1*y1)) * Math.sqrt((x2*x2) + (y2*y2))));
        // From Rads to Degrees
        rotation = (rotation / (float)Math.PI) * 180;

        if (Float.isNaN(rotation)) {
            return true; // something is wrong. Go try once more
        }

        switch(startQ) {
            case 1:
            {
                if (endQ == 1) {
                    if (endK > startK)
                        rotation = 0;
                }
                if (endQ == 3 || endQ == 2)
                    if (endK < startK)
                        rotation = 180 + (180 - rotation);
                break;
            }
            case 2:
            {
                if (endQ == 2) {
                    if (endK > startK)
                        rotation = 0;
                }
                if (endQ == 3)
                {
                    if (endK > startK)
                        rotation = 180 + (180 - rotation);
                }
                if (endQ == 4) {
                    if (endK < startK)
                        rotation = 180 + (180 - rotation);
                }
                break;
            }

            case 3:
                if (endQ == 3) {
                    if (endK > startK)
                        rotation = 0;
                }
                if (endQ == 1 || endQ == 4) {
                    if (endK < startK)
                        rotation = 180 + (180 - rotation);
                }
                break;
            case 4:
                if (endQ == 4) {
                    if (endK > startK)
                        rotation = 0;
                }
                if (endQ == 1)
                {
                    if (endK > startK)
                        rotation = 180 + (180 - rotation);
                }
                if (endQ == 2) {
                    if (endK < startK)
                        rotation = 180 + (180 - rotation);
                }
                break;
        }

//        Log.d("angle", "Rotation == " + rotation);

        if (rotation > 280) {
            rotation = 280;
        }

        if(rotation > threshold)
        {
            if (prevN != getNumFromRotation(rotation))
            {
                Vibrator vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(90);
            }

            prevN = getNumFromRotation(rotation);
        }

        ringImage.setRotation(rotation);
        return true;
    }

    private boolean handleRelease() {
        RollbackAnimation rb = new RollbackAnimation(ringImage);
        if (Float.isNaN(rotation))
        {
            rb.setDuration(5);
            ringImage.startAnimation(rb);
            return true;
        }
        if (rotation < threshold)
        {
            rb.setDuration(150);
            ringImage.startAnimation(rb);
            Log.d("Info", "No number");
        }
        else
        {
            int inputNum = getNumFromRotation(rotation);
            if (inputNum != 0)
                rb.setDuration(150 + inputNum * accelPerNum);
            else
                rb.setDuration(150 + 10 * accelPerNum); // 0 is the last
            ringImage.startAnimation(rb);
            expressionText.append("" + inputNum);
            Log.d("Info", "Number == " + inputNum);
        }
        return false;
    }

    private int getQuarter(float _x, float _y) {
        if (_x < 0 && _y < 0)
            return 3;
        if (_x > 0 && _y < 0)
            return 4;
        if (_x < 0 && _y > 0)
            return 2;

//        if (_x > 0 && _y > 0)
        return 1;
    }

}
