package com.shopify.mobilechallengefall2018.Views.Adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDO;
import com.shopify.mobilechallengefall2018.Model.EventPojos.OrderDOList;
import com.shopify.mobilechallengefall2018.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private OrderDOList orderDOList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView orderId;
        private TextView orderEmail;
        private TextView orderProvince;
        private TextView orderPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            orderEmail = itemView.findViewById(R.id.orderEmail);
            orderProvince = itemView.findViewById(R.id.orderProvince);
            orderPrice = itemView.findViewById(R.id.orderPrice);
        }
    }

    public OrdersAdapter(OrderDOList orderDOList){
        this.orderDOList = orderDOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDO orderDO = orderDOList.getOrderDOList().get(position);

        holder.orderId.setText(orderDO.getOrderId());
        holder.orderEmail.setText(orderDO.getEmail());
        holder.orderProvince.setText(orderDO.getProvince());
        holder.orderPrice.setText("$" + orderDO.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return this.orderDOList.getOrderDOList().size();
    }
}
