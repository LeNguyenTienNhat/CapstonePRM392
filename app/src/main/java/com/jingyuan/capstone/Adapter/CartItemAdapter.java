package com.jingyuan.capstone.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingyuan.capstone.DTO.View.CartItem;
import com.jingyuan.capstone.R;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder>{

    Context context;
    ArrayList<CartItem> cartItemsList;
    public CartItemAdapter(Context context, ArrayList<CartItem> cartItemsList) {
        this.context = context;
        this.cartItemsList = cartItemsList;
    }

    @NonNull
    @Override
    public CartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recview_cart_item, parent, false);
        return new CartItemAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.MyViewHolder holder, int position) {
        holder.name.setText(cartItemsList.get(position).getName());
        holder.quantity.setText(cartItemsList.get(position).getQuantity()+"");
        holder.price.setText(cartItemsList.get(position).getPrice()+"");
        String thumbnailURL = cartItemsList.get(position).getThumbnail();
        Glide.with(context).load(thumbnailURL).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView thumbnail;
        TextView price;
        TextView quantity;
        public MyViewHolder (@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_item_name);
            thumbnail = itemView.findViewById(R.id.cart_item_thumbnail);
            price = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }
}
