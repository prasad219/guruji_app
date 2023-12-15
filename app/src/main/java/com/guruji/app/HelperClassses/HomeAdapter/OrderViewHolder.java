package com.guruji.app.HelperClassses.HomeAdapter;

import android.view.View;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.guruji.app.DRVinterface.ItemClickListener;
import com.guruji.app.R;

import org.jetbrains.annotations.NotNull;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress, txtOrderName;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.orderid);
        txtOrderStatus = itemView.findViewById(R.id.orderstatus);
        txtOrderAddress = itemView.findViewById(R.id.orderaddress);
        txtOrderName = itemView.findViewById(R.id.ordername);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getBindingAdapterPosition(), false);
    }


}
