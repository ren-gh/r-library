
package com.r.library.demo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.r.library.common.util.LogUtils;

public class MenuView extends RelativeLayout {
    private final String TAG = "MenuView";
    private AnimatorSet animatorSetOpen, animatorSetClose;
    private MenuBtn mbtnFirst, mbtnSecond, mbtnThird;
    private ImageView imgDown;
    private int height, lowHeight;
    private boolean closed = false;

    public static final int FIRST_BTN_ID = R.id.mbtn_shutdown;
    public static final int SECOND_BTN_ID = R.id.mbtn_delay;
    public static final int THIRD_BTN_ID = R.id.mbtn_screen;

    private int lastFocusBtnId = FIRST_BTN_ID;

    public MenuView(Context context) {
        super(context);
        init(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MenuBtn firstBtn() {
        return mbtnFirst;
    }

    public MenuBtn secondBtn() {
        return mbtnSecond;
    }

    public MenuBtn thirdBtn() {
        return mbtnThird;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setLastFocusBtnId(int id) {
        this.lastFocusBtnId = id;
    }

    public int getLastFocusBtnId() {
        return lastFocusBtnId;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mbtnFirst.setOnClickListener(onClickListener);
        mbtnSecond.setOnClickListener(onClickListener);
        mbtnThird.setOnClickListener(onClickListener);
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mbtnFirst.setOnFocusChangeListener(onFocusChangeListener);
        mbtnSecond.setOnFocusChangeListener(onFocusChangeListener);
        mbtnThird.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void disableFocus() {
        mbtnFirst.setFocusable(false);
        mbtnSecond.setFocusable(false);
        mbtnThird.setFocusable(false);
        mbtnFirst.clearFocus();
        mbtnSecond.clearFocus();
        mbtnThird.clearFocus();
    }

    public void revertFocus() {
        mbtnFirst.setFocusable(true);
        mbtnSecond.setFocusable(true);
        mbtnThird.setFocusable(true);
        switch (lastFocusBtnId) {
            case MenuView.FIRST_BTN_ID:
                mbtnFirst.requestFocus();
                break;
            case MenuView.SECOND_BTN_ID:
                mbtnSecond.requestFocus();
                break;
            case MenuView.THIRD_BTN_ID:
                mbtnThird.requestFocus();
                break;
        }
    }

    public void onFocusChange(View v, boolean hasFocus, boolean canClose) {
        switch (v.getId()) {
            case MenuView.FIRST_BTN_ID:
            case MenuView.SECOND_BTN_ID:
            case MenuView.THIRD_BTN_ID: {
                if (hasFocus) {
                    setLastFocusBtnId(v.getId());
                    open();
                }
            }
            break;
            default: {
                if (canClose) {
                    close();
                }
            }
            break;
        }
    }

    public void open() {
        open(500);
    }

    public void close() {
        close(500);
    }

    public void open(int duration) {
        LogUtils.i(TAG, "open() " + duration + ", closed: " + closed);
        if (!closed) {
            return;
        }
        if (initViewHeight()) {
            return;
        }
        initAnimatorSet();
        closed = false;
        LogUtils.i(TAG, "open() lowHeight=" + lowHeight + ", height=" + height);
        LogUtils.i(TAG, "shutdown x=" + firstBtn().text().getX());
        animatorSetOpen.setDuration(duration).start();
    }

    public void close(int duration) {
        LogUtils.i(TAG, "close() " + duration + ", closed: " + closed);
        if (closed) {
            return;
        }
        if (initViewHeight()) {
            return;
        }
        initAnimatorSet();
        closed = true;
        LogUtils.i(TAG, "close() lowHeight=" + lowHeight + ", height=" + height);
        LogUtils.i(TAG, "shutdown x=" + firstBtn().text().getX());
        animatorSetClose.setDuration(duration).start();
    }

    public void resumeAnimators() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (null != animatorSetOpen && animatorSetOpen.isPaused()) {
                animatorSetOpen.resume();
            }
            if (null != animatorSetClose && animatorSetClose.isPaused()) {
                animatorSetClose.resume();
            }
        }
    }

    public void pauseAnimators() {
        if (null != animatorSetOpen && animatorSetOpen.isRunning()) {
            if (Build.VERSION.SDK_INT >= 19) {
                animatorSetOpen.pause();
            } else {
                animatorSetOpen.cancel();
            }
        }
        if (null != animatorSetClose && animatorSetClose.isRunning()) {
            if (Build.VERSION.SDK_INT >= 19) {
                animatorSetClose.pause();
            } else {
                animatorSetClose.cancel();
            }
        }
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_menu, this);

        mbtnFirst = findViewById(FIRST_BTN_ID);
        mbtnSecond = findViewById(SECOND_BTN_ID);
        mbtnThird = findViewById(THIRD_BTN_ID);
        imgDown = findViewById(R.id.img_down);
    }

    private boolean initViewHeight() {
        if (0 == height) {
            height = getHeight();
        }
        if (0 != height) {
            lowHeight = height - (int) getResources().getDimension(R.dimen.dp_70);
            return false;
        }
        return true;
    }

    private void initAnimatorSet() {
        if (null == animatorSetOpen) {
            // open
            ValueAnimator valueAnimator = ValueAnimator.ofInt(lowHeight, height);
            valueAnimator.addUpdateListener(animation -> {
                int height = (int) animation.getAnimatedValue();
                LogUtils.i(TAG, "open() onAnimationUpdate() height=" + height);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = height;
                setLayoutParams(layoutParams);
            });
            ObjectAnimator[] shutdownBtnAnimators = getBtnOpenAnimators(mbtnFirst);
            ObjectAnimator[] delayBtnAnimators = getBtnOpenAnimators(mbtnSecond);
            ObjectAnimator[] screenBtnAnimators = getBtnOpenAnimators(mbtnThird);
            ObjectAnimator downIconAnimationAlpha = ObjectAnimator.ofFloat(imgDown,
                    "alpha",
                    0, 1);

            Animator[] animators = new Animator[]{
                    valueAnimator,
                    shutdownBtnAnimators[0], shutdownBtnAnimators[1], shutdownBtnAnimators[2],
                    delayBtnAnimators[0], delayBtnAnimators[1], delayBtnAnimators[2],
                    screenBtnAnimators[0], screenBtnAnimators[1], screenBtnAnimators[2],
                    downIconAnimationAlpha
            };
            animatorSetOpen = new AnimatorSet();
            animatorSetOpen.playTogether(animators);
        }
        if (null == animatorSetClose) {
            // close
            ValueAnimator valueAnimator = ValueAnimator.ofInt(height, lowHeight);
            valueAnimator.addUpdateListener(animation -> {
                int height = (int) animation.getAnimatedValue();
                LogUtils.i(TAG, "close() onAnimationUpdate() height=" + height);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = height;
                setLayoutParams(layoutParams);
            });
            ObjectAnimator[] shutdownBtnAnimators = getBtnCloseAnimators(mbtnFirst);
            ObjectAnimator[] delayBtnAnimators = getBtnCloseAnimators(mbtnSecond);
            ObjectAnimator[] screenBtnAnimators = getBtnCloseAnimators(mbtnThird);

            ObjectAnimator downIconAnimationAlpha = ObjectAnimator.ofFloat(imgDown,
                    "alpha",
                    1, 0);

            Animator[] animators = new Animator[]{
                    valueAnimator,
                    shutdownBtnAnimators[0], shutdownBtnAnimators[1], shutdownBtnAnimators[2],
                    delayBtnAnimators[0], delayBtnAnimators[1], delayBtnAnimators[2],
                    screenBtnAnimators[0], screenBtnAnimators[1], screenBtnAnimators[2],
                    downIconAnimationAlpha
            };
            animatorSetClose = new AnimatorSet();
            animatorSetClose.playTogether(animators);
        }
    }

    private ObjectAnimator[] getBtnOpenAnimators(MenuBtn menuBtn) {
        ObjectAnimator[] objectAnimators = new ObjectAnimator[3];
        int textX = (menuBtn.text().getWidth() >> 1);
        ObjectAnimator textAnimationX = ObjectAnimator.ofFloat(menuBtn.text(),
                "x",
                menuBtn.text().getX() - textX, menuBtn.text().getX());

        ObjectAnimator textAnimationAlpha = ObjectAnimator.ofFloat(menuBtn.text(),
                "alpha",
                0, 1);

        int iconX = (int) (((menuBtn.getWidth() >> 1) - menuBtn.icon().getWidth() + menuBtn.icon().getWidth()) * 0.5);
        ObjectAnimator iconAnimationX = ObjectAnimator.ofFloat(menuBtn.icon(),
                "x",
                menuBtn.icon().getX() + iconX, menuBtn.icon().getX());

        objectAnimators[0] = textAnimationX;
        objectAnimators[1] = textAnimationAlpha;
        objectAnimators[2] = iconAnimationX;

        return objectAnimators;
    }

    private ObjectAnimator[] getBtnCloseAnimators(MenuBtn menuBtn) {
        ObjectAnimator[] objectAnimators = new ObjectAnimator[3];
        int textX = (menuBtn.text().getWidth() >> 1);
        ObjectAnimator textAnimationX = ObjectAnimator.ofFloat(menuBtn.text(),
                "x",
                menuBtn.text().getX(), menuBtn.text().getX() - textX);

        ObjectAnimator textAnimationAlpha = ObjectAnimator.ofFloat(menuBtn.text(),
                "alpha",
                1, 0);

        int iconX = (int) (((menuBtn.getWidth() >> 1) - menuBtn.icon().getWidth() + menuBtn.icon().getWidth()) * 0.5);
        ObjectAnimator iconAnimationX = ObjectAnimator.ofFloat(menuBtn.icon(),
                "x",
                menuBtn.icon().getX(), menuBtn.icon().getX() + iconX);

        objectAnimators[0] = textAnimationX;
        objectAnimators[1] = textAnimationAlpha;
        objectAnimators[2] = iconAnimationX;

        return objectAnimators;
    }

}
