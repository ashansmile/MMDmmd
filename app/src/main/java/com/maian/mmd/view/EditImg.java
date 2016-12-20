package com.maian.mmd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by admin on 2016/12/19.
 */

public class EditImg extends View {

    private  float clickX,clickY;
    boolean isMove;
    public EditImg(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            isMove = false;
            invalidate();
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){

            isMove = true;
            invalidate();
            return true;
        }

        return super.onTouchEvent(event);
    }
}
