package com.shopify.mobilechallengefall2018.Views.CustomViews;

import android.content.Context;
import android.view.animation.ScaleAnimation;
import android.widget.TableRow;

public class CustomTableRow extends TableRow {
    public CustomTableRow(Context context) {
        super(context);
    }

    /**
     * Simple fade in animation
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ScaleAnimation anim = new ScaleAnimation(0,1,0,1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        this.startAnimation(anim);
    }
}
