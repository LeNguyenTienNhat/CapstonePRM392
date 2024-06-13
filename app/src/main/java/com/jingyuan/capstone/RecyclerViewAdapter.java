package com.jingyuan.capstone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jingyuan.capstone.Controller.DetailActivity;
import com.jingyuan.capstone.DTO.View.ProductItemDTO;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductItemDTO> productItemsList;
    public RecyclerViewAdapter(Context context, ArrayList<ProductItemDTO> productItemsList) {
        this.context = context;
        this.productItemsList = productItemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(productItemsList.get(position).getName());
        holder.thumbnail.setImageBitmap(productItemsList.get(position).getThumbnail());
        holder.price.setText(productItemsList.get(position).getPrice().toString());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("doc", productItemsList.get(holder.getAdapterPosition()).getDoc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productItemsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        ImageView thumbnail;
        public MyViewHolder (@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}