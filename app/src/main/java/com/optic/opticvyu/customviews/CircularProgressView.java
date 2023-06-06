package com.optic.opticvyu.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.optic.interior.R;


public class CircularProgressView extends View {
    private final int arcWidth;
    private final Paint backgroundPaint;
    private final Paint foregroundPaint;
    private final int arcMargin;
    private int markerColor;
    private float progressValue;
    private int largestValue = -1;
    private RectF backgroundRect;
    private RectF foregroundRect;
    private ValueAnimator animation;

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
        final int arcWidth = (int) typedArray.getDimension(R.styleable.CircularProgressView_arc_width, 5);
        arcMargin = (int) typedArray.getDimension(R.styleable.CircularProgressView_arc_margin, 3);
        typedArray.recycle();
        this.arcWidth = arcWidth;

        markerColor = getResources().getColor(R.color.custom_yellow);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(markerColor);
        backgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(arcWidth);

        foregroundPaint = new Paint();
        foregroundPaint.setColor(markerColor);
        foregroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(arcWidth * 3);
    }

    public void setProgressValue(final int value) {
        if (largestValue < value)
            largestValue = value;

        if (animation != null) {
            animation.cancel();
        }
        animation = ValueAnimator.ofFloat(progressValue, value);
        animation.setDuration(1500);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animation.start();
    }


    public void setLargestValue(int value) {
        largestValue = value;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        backgroundRect = new RectF(arcWidth / 2 + arcMargin, arcWidth / 2 + arcMargin, getWidth() - arcWidth / 2 - arcMargin, getHeight() - arcWidth / 2 - arcMargin);
        foregroundRect = new RectF(arcWidth + arcMargin + arcWidth / 2, arcWidth + arcMargin + arcWidth / 2, getWidth() - arcWidth - arcMargin - arcWidth / 2, getHeight() - arcWidth - arcMargin - arcWidth / 2);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(backgroundRect, 0, 360, false, backgroundPaint);

        final int startAngle = -90;
        if (largestValue > 0) {
            final float currentAngle = (progressValue * 360) / largestValue;
            canvas.drawArc(foregroundRect, startAngle, currentAngle, false, foregroundPaint);
        }
    }

    public void setDrawColor(int drawColor) {
        markerColor = drawColor;
        backgroundPaint.setColor(markerColor);
        foregroundPaint.setColor(markerColor);
        invalidate();
    }
}