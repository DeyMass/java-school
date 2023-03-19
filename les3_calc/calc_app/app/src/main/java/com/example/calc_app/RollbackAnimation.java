package com.example.calc_app;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class RollbackAnimation extends Animation {
    ImageView ringImage;
    float startAngle;

    RollbackAnimation(ImageView v)
    {
        startAngle = v.getRotation();
        ringImage = v;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float rotation_val = startAngle - (startAngle * interpolatedTime);

        ringImage.setRotation(rotation_val);
        ringImage.postInvalidate();
    }
}
