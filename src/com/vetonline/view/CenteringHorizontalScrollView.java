package com.vetonline.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * A centering HSV loosely based on http://iotasol.blogspot.com/2011/08/creating-custom-horizontal-scroll-view.html
 */
public class CenteringHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener {

    private static final int SWIPE_PAGE_ON_FACTOR = 10;

    private int mActiveItem;

    private float mPrevScrollX;

    private boolean mStart;

    private int mItemWidth;

    public CenteringHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mItemWidth = 100; // or whatever your item width is.
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();

        boolean handled = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mStart) {
                    mPrevScrollX = x;
                    mStart = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                mStart = true;
                int minFactor = mItemWidth / SWIPE_PAGE_ON_FACTOR;

                if ((mPrevScrollX - (float) x) > minFactor) {
                    if (mActiveItem < getMaxItemCount() - 1) {
                        mActiveItem = mActiveItem + 1;
                    }
                }
                else if (((float) x - mPrevScrollX) > minFactor) {
                    if (mActiveItem > 0) {
                        mActiveItem = mActiveItem - 1;
                    }
                }

                scrollToActiveItem();

                handled = true;
                break;
        }

        return handled;
    }

    private int getMaxItemCount() {
        return ((LinearLayout) getChildAt(0)).getChildCount();
    }

    private LinearLayout getLinearLayout() {
        return (LinearLayout) getChildAt(0);
    }

    /**
     * Centers the current view the best it can.
     */
    public void centerCurrentItem() {
        if (getMaxItemCount() == 0) {
            return;
        }

        int currentX = getScrollX();
        View targetChild;
        int currentChild = -1;

        do {
            currentChild++;
            targetChild = getLinearLayout().getChildAt(currentChild);
        } while (currentChild < getMaxItemCount() && targetChild.getLeft() < currentX);

        if (mActiveItem != currentChild) {
            mActiveItem = currentChild;
            scrollToActiveItem();
        }
    }

    /**
     * Scrolls the list view to the currently active child.
     */
    private void scrollToActiveItem() {
        int maxItemCount = getMaxItemCount();
        if (maxItemCount == 0) {
            return;
        }

        int targetItem = Math.min(maxItemCount - 1, mActiveItem);
        targetItem = Math.max(0, targetItem);

        mActiveItem = targetItem;

        // Scroll so that the target child is centered
        View targetView = getLinearLayout().getChildAt(targetItem);

        int targetLeft = targetView.getLeft();
        int childWidth = targetView.getRight() - targetLeft;

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int targetScroll = targetLeft - ((width - childWidth) / 2);

        super.smoothScrollTo(targetScroll, 0);
    }

    /**
     * Sets the current item and centers it.
     * @param currentItem The new current item.
     */
    public void setCurrentItemAndCenter(int currentItem) {
        mActiveItem = currentItem;
        scrollToActiveItem();
    }
}
