package com.jingyuan.capstone;

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
import com.jingyuan.capstone.Model.ProductModel;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductModel> categoryList;
    public RecyclerViewAdapter(Context context, ArrayList<ProductModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
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
        holder.label.setText(categoryList.get(position).getLabel());
        holder.thumbnail.setImageResource(categoryList.get(position).getThumbnail());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("thumbnailID", categoryList.get(holder.getAdapterPosition()).getThumbnail());
                intent.putExtra("label", categoryList.get(holder.getAdapterPosition()).getLabel());
                intent.putExtra("des", categoryList.get(holder.getAdapterPosition()).getDes());
                intent.putExtra("store", categoryList.get(holder.getAdapterPosition()).getStore());
                intent.putExtra("phoneNumber", categoryList.get(holder.getAdapterPosition()).getPhoneNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView label;
        public MyViewHolder (@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            label = itemView.findViewById(R.id.label);
        }
    }
}