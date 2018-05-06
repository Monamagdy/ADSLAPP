package com.hdk.adsltrial;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by monamagdy on 5/5/18.
 */
public class MySlidingPanelLayout extends SlidingPaneLayout {

    private boolean enableScroll;

    public MySlidingPanelLayout(Context context) {
        super(context);
        init();
    }

    public MySlidingPanelLayout(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MySlidingPanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setDisableScroll() {
        enableScroll = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enableScroll) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (enableScroll) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    private void init() {

        enableScroll = true;

//        setOnTouchListener((v, event) -> {
//            enableScroll = true;
//            return false;
//        });
//
   }

}