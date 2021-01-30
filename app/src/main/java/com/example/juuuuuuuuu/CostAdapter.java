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
import java.text.DecimalFormat;
import java.util.ArrayList;

class CostAdapter extends RecyclerView.Adapter<CostAdapter.CostHolder> {
    private ArrayList<Cost> list;
    private Context mCtx;
    private OnItemClickListener clickListener;


    public CostAdapter(Context mCtx, ArrayList<Cost> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public CostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.showrecord_view_recycler_layout,null);
        return new CostHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CostHolder holder, int position) {
        DecimalFormat fomatter = new DecimalFormat("#,###");
        Cost cost=list.get(position);
        holder.showrecord_cost.setText(fomatter.format((Integer.valueOf(cost.getCost()))));
        holder.showrecord_test.setText(cost.getType());
        /////設置小框框的icon
        if(cost.getTypeMom().equals("飲食"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.food);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r1));
        }
        if(cost.getTypeMom().equals("交通"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.transport);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r2));
        }
        if(cost.getTypeMom().equals("娛樂"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.play);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r3));

        }
        if(cost.getTypeMom().equals("購物"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.shopping);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r4));
        }
        if(cost.getTypeMom().equals("家居"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.house);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r5));
        }
        if(cost.getTypeMom().equals("個人"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.individual);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r6));
        }
        if(cost.getTypeMom().equals("生活休閒"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.life);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r7));
        }
        if(cost.getTypeMom().equals("醫療"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.hospital);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r8));
        }
        if(cost.getTypeMom().equals("學習"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.learn);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r9));
        }
        if(cost.getTypeMom().equals("其他支出"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.more);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r10));
        }
        if(cost.getTypeMom().equals("工作收入"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.salary);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r11));
        }
        if(cost.getTypeMom().equals("非工作收入"))
        {
            holder.showrecord_icon.setImageResource(R.drawable.more);
            holder.showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r12));
        }
        if(cost.getSymbol().equals("true"))
        {
            holder.showrecord_symbol.setImageResource(R.drawable.minus);
            holder.showrecord_symbol.setColorFilter(mCtx.getResources().getColor(R.color.minus));
        }
        if(cost.getSymbol().equals("false"))
        {
            holder.showrecord_symbol.setImageResource(R.drawable.in);
            holder.showrecord_symbol.setColorFilter(mCtx.getResources().getColor(R.color.in));
        }


    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setOnClickListener(OnItemClickListener hello){clickListener=hello;}

    public class CostHolder extends RecyclerView.ViewHolder{

        public TextView showrecord_test,showrecord_cost;
        public ImageView showrecord_icon ,showrecord_symbol;

        public  CostHolder(@NotNull View itemView){
            super(itemView);
            showrecord_test=itemView.findViewById(R.id.showrecord_test);
            showrecord_cost=itemView.findViewById(R.id.showrecord_cost);
            showrecord_icon=itemView.findViewById(R.id.showrecord_icon);
            showrecord_symbol=itemView.findViewById(R.id.showrecord_symbol);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    clickListener.onItemClick(position);

                }
            });

        }


    }

}