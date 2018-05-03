package com.shopify.mobilechallengefall2018.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shopify.mobilechallengefall2018.Controllers.MainController;
import com.shopify.mobilechallengefall2018.Model.EventPojos.EventPojo;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.Adapters.OrdersAdapter;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class OrdersByProvinceView extends BaseView {

    public OrdersByProvinceView(ViewGroup viewGroup, Context context){
        this.viewGroup = viewGroup;
        this.context = context;
    }

    @Override
    public void setupView() {
        setupOrdersByProvinceView();
    }


    //Since this activity is just for displaying the recyclerview with all the fulfilled orders we will skip the usual architecture for simplicity
    public void setupOrdersByProvinceView(){
        OrderDOList orderDOList = MainController.getOrderListDO();
        //We need to first get all the provinces
        Set<String> provinceNames = new TreeSet<>();
        orderDOList.getOrderDOList().forEach(orderDO -> {
            provinceNames.add(orderDO.getProvince());
        });

        //Get Layout that we will be appending to
        LinearLayout linearLayout = this.viewGroup.findViewById(R.id.allOrdersByProvince);

        provinceNames.forEach(provinceName ->{

            //Create child linear layout
            LinearLayout childLinearLayout = new LinearLayout(context);
            childLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            childLinearLayout.setOrientation(LinearLayout.VERTICAL);
            childLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.green,context.getTheme()));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.setMargins(0,10,0,10);
            TextView provinceTextView = new TextView(context);
            provinceTextView.setLayoutParams(params);
            provinceTextView.setText(provinceName);
            provinceTextView.setTextColor(context.getResources().getColor(R.color.colorBlack, context.getTheme()));
            provinceTextView.setTextSize(14);

            Typeface typeface = Typeface.createFromAsset(context.getResources().getAssets(),
                    "opensansboldfont.ttf");

            provinceTextView.setTypeface(typeface);
            provinceTextView.setPadding(0, 10, 0, 10);
            childLinearLayout.addView(provinceTextView);


            //Create the imageview drop shadow
            ImageView dropShadow = new ImageView(context);
            dropShadow.setBackground(context.getResources().getDrawable( R.drawable.bottom_gradient_shadow,context.getTheme()));
            dropShadow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
            childLinearLayout.addView(dropShadow);

            //Add the childLayout to the parent
            linearLayout.addView(childLinearLayout);

            //Create the recycler view and populate it with all the orders under this province name
            OrderDOList subsetDoList = new OrderDOList(EventPojo.ORDERS_BY_PROVINCE, orderDOList.getOrderDOList().stream().filter(orderDO -> orderDO.getProvince().equals(provinceName)).collect(Collectors.toList()));
            OrdersAdapter ordersAdapter = new OrdersAdapter(subsetDoList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            RecyclerView ordersToFulFillRecyclerView = new RecyclerView(context);
            ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
            ordersToFulFillRecyclerView.setNestedScrollingEnabled(false);
            ordersToFulFillRecyclerView.setFocusable(false);
            ordersToFulFillRecyclerView.setAdapter(ordersAdapter);

            //Set enter animation animation
            int animationId = R.anim.layout_animation_fall_down;
            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(context, animationId);
            ordersToFulFillRecyclerView.setLayoutAnimation(animationController);

            //Set on click listener on childLinearLayout so that the recyclerview minimizes when clicked
            childLinearLayout.setOnClickListener(view -> {
                if (ordersToFulFillRecyclerView.getVisibility() == View.VISIBLE){
                    ordersToFulFillRecyclerView.setVisibility(View.GONE);
                } else {
                    ordersToFulFillRecyclerView.setVisibility(View.VISIBLE);
                }

            });

            linearLayout.addView(ordersToFulFillRecyclerView);
        });
    }
}
