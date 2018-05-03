package com.shopify.mobilechallengefall2018.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.shopify.mobilechallengefall2018.Activities.OrdersByProvince;
import com.shopify.mobilechallengefall2018.Model.EventPojos.NumberOfOrdersCategoryDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;
import com.shopify.mobilechallengefall2018.Views.Adapters.OrdersAdapter;
import com.shopify.mobilechallengefall2018.Views.CustomViews.CustomTableRow;

public class MainView extends BaseView {

    public MainView(ViewGroup viewGroup, Context context){
        this.viewGroup = viewGroup;
        this.context = context;
    }

    @Override
    public void setupView() {
        //do nothing
        onNumberOfOrdersByProvinceHeaderClicked();
    }


    public void onNumberOfOrdersByProvinceHeaderClicked(){
        this.viewGroup.findViewById(R.id.numberOfOrdersByProvince).setOnClickListener(view -> {
            context.startActivity(new Intent(context, OrdersByProvince.class));
        });
    }

    public void hideLoadingProvincesText(){
        viewGroup.findViewById(R.id.loadingOrdersByProvince).setVisibility(View.GONE);
    }

    public void setupOnOrdersByProvinceNumber(NumberOfOrdersCategoryDO categoryDO){
        //Every time this method is called we append a new row in the table layout and add this row
        TableLayout tableLayout = viewGroup.findViewById(R.id.ordersByProvinceTableLayout);
        CustomTableRow tableRow = new CustomTableRow(context);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,10);
        tableRow.setLayoutParams(layoutParams);

        Typeface typeface = Typeface.createFromAsset(context.getResources().getAssets(),
                "opensansregularfont.ttf");

        //Add Province TextView
        TextView province = new TextView(context);
        province.setLayoutParams(layoutParams);
        province.setText(categoryDO.getProvince());
        province.setTextColor(context.getResources().getColor(R.color.colorBlack, context.getTheme()));
        province.setTypeface(typeface);

        //Add result textview
        TextView amount = new TextView(context);
        amount.setLayoutParams(layoutParams);
        amount.setText(categoryDO.getAmount());
        amount.setTextColor(context.getResources().getColor(R.color.colorBlack, context.getTheme()));
        amount.setTypeface(typeface);

        tableRow.addView(province);
        tableRow.addView(amount);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    }

    public void setupOnOrdersByYearNumber(NumberOfOrdersCategoryDO categoryDO){
        ((TextView)this.viewGroup.findViewById(R.id.ordersByYear)).setText(categoryDO.getAmount());
    }
    
    public void setupOrdersByYearRecyclerView(OrderDOList orderDOList){
        OrdersAdapter ordersAdapter = new OrdersAdapter(orderDOList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        RecyclerView ordersToFulFillRecyclerView = this.viewGroup.findViewById(R.id.ordersIn2017);
        ordersToFulFillRecyclerView.setLayoutManager(layoutManager);
        ordersToFulFillRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersToFulFillRecyclerView.setAdapter(ordersAdapter);
    }
    

}
