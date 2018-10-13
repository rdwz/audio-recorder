package com.wirehall.audiorecorder.visualizer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.audiofx.Visualizer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * base class that contains common implementation for all visualizers.
 */

abstract public class BaseVisualizer extends View {
    protected byte[] bytes;
    protected Paint paint = new Paint();
    protected Visualizer visualizer;
    protected int color = Color.BLUE;

    public BaseVisualizer(Context context) {
        super(context);
        init(null);
    }

    public BaseVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * set color to visualizer with color resource id.
     *
     * @param color color resource id.
     */
    public void setColor(int color) {
        this.color = color;
        this.paint.setColor(this.color);
    }

    public void setPlayer(int audioSessionId) {
        visualizer = new Visualizer(audioSessionId);
        visualizer.setEnabled(false);
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                BaseVisualizer.this.bytes = bytes;
                invalidate();
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        visualizer.setEnabled(true);
    }

    public void release() {
        visualizer.release();
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    protected abstract void init(@Nullable AttributeSet attributeSet);
}