package com.shopify.mobilechallengefall2018.Views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base view that all views extend from
 */
public abstract class BaseView {

    protected ViewGroup viewGroup;
    protected Context context;

    public abstract void setupView();

    //Getters
    public View getView(){return this.viewGroup;}
    public Context getContext(){return this.context;}
}
