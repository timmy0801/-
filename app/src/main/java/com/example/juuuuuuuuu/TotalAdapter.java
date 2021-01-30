package com.example.juuuuuuuuu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Documented;
import java.util.ArrayList;

class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.TotalHolder> {
    private ArrayList<Total> list;
    private Context mCtx;



    public TotalAdapter(Context mCtx, ArrayList<Total> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public TotalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.report_category_show_detail,null);
        return new TotalHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TotalHolder holder, int position) {
        Total total= list.get(position);
        holder.money.setText(String.valueOf(total.getCost1()));
        holder.category.setText(total.getType1());
        holder.detail_date.setText(total.getDate1());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TotalHolder extends RecyclerView.ViewHolder{

        public TextView money,category,detail_date;

        public  TotalHolder(@NotNull View itemView){
            super(itemView);
            money=itemView.findViewById(R.id.money);

            category=itemView.findViewById(R.id.category);
            detail_date=itemView.findViewById(R.id.detail_date);


        }


    }

}