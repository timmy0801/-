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

class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.ManageHolder> {
    private ArrayList<Manage> list;
    private Context mCtx;
    private ManageAdapter.OnItemClickListener clickListener;


    public ManageAdapter(Context mCtx, ArrayList<Manage> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public ManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.manage_recycle,null);
        return new ManageHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ManageHolder holder, int position) {
        Manage manage= list.get(position);
        holder.manage_category.setText(manage.getCategory());
    }
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnClickListener(ManageAdapter.OnItemClickListener hello){clickListener=hello;}

    public class ManageHolder extends RecyclerView.ViewHolder{

        public TextView manage_category;

        public  ManageHolder(@NotNull View itemView){
            super(itemView);
            manage_category=itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    clickListener.onItemClick(v,position);
                }
            });
        }


    }

}