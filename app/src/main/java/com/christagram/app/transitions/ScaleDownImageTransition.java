package com.christagram.app.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.christagram.app.R;

public class ScaleDownImageTransition extends Transition {
    private static final int DEFAULT_SCALE_DOWN_FACTOR = 8;
    private static final String PROPNAME_SCALE_X = "transitions:scale_down:scale_x";
    private static final String PROPNAME_SCALE_Y = "transitions:scale_down:scale_y";
    private Bitmap bitmap;
    private Context context;

    private int targetScaleFactor = DEFAULT_SCALE_DOWN_FACTOR;

    public ScaleDownImageTransition(final Context context) {
        this.context = context;
        setInterpolator(new DecelerateInterpolator());
    }

    public ScaleDownImageTransition(final Context context, final Bitmap bitmap) {
        this(context);
        this.bitmap = bitmap;
    }

    public ScaleDownImageTransition(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleDownImageTransition);
        try {
            targetScaleFactor = array.getInteger(R.styleable.ScaleDownImageTransition_factor, DEFAULT_SCALE_DOWN_FACTOR);
        } finally {
            array.recycle();
        }
    }

    public void setBitmap(final Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setScaleFactor(final int factor) {
        targetScaleFactor = factor;
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, final TransitionValues startValues, final TransitionValues endValues) {
        if (null == endValues) {
            return null;
        }
        final View view = endValues.view;
        if (view instanceof ImageView) {
            if (bitmap != null)
                view.setBackground(new BitmapDrawable(context.getResources(), bitmap));

            float scaleX = 0F;
            float scaleY = 0F;

            if (startValues != null) {
                scaleX = (float) startValues.values.get(PROPNAME_SCALE_X);
                scaleY = (float) startValues.values.get(PROPNAME_SCALE_Y);
            }

            float targetScaleX = (float) endValues.values.get(PROPNAME_SCALE_X);
            float targetScaleY = (float) endValues.values.get(PROPNAME_SCALE_Y);

            ObjectAnimator scaleXAnimator = ObjectAnimator
                .ofFloat(view, View.SCALE_X, targetScaleX, scaleX);
            ObjectAnimator scaleYAnimator = ObjectAnimator
                .ofFloat(view, View.SCALE_Y, targetScaleY, scaleY);
            AnimatorSet parallelSet = new AnimatorSet();
            parallelSet.playTogether(scaleXAnimator, scaleYAnimator, ObjectAnimator
                .ofFloat(view, View.ALPHA, 0f, 1f));
            AnimatorSet sequentialSet = new AnimatorSet();
            sequentialSet.playSequentially(parallelSet, ObjectAnimator
                .ofFloat(view, View.ALPHA, 1f, 0f));
            return sequentialSet;
        }
        return null;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues, transitionValues.view.getScaleX(), transitionValues.view.getScaleY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues, targetScaleFactor, targetScaleFactor);
    }

    private void captureValues(final TransitionValues values, final float scaleX, final float scaleY) {
        values.values.put(PROPNAME_SCALE_X, scaleX);
        values.values.put(PROPNAME_SCALE_Y, scaleY);
    }
}