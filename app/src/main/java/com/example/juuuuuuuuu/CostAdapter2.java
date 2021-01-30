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

import static android.hardware.camera2.params.RggbChannelVector.RED;

class CostAdapter2 extends RecyclerView.Adapter<CostAdapter2.CostHolder> {
    private ArrayList<Cost2> list;
    private Context mCtx;
    private OnItemClickListener clickListener;



    public CostAdapter2(Context mCtx, ArrayList<Cost2> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public CostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.report_category_recycler,null);

        return new CostHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CostHolder holder, int position) {

        DecimalFormat fomatter = new DecimalFormat("#,###");
        Cost2 cost= list.get(position);
        holder.report_showrecord_cost.setText("NT＄"+fomatter.format((Integer.valueOf(cost.getCost()))));
        holder.report_showrecord_test.setText(cost.getType());
        if(cost.getType()=="飲食")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.food);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r1));
        }
        if(cost.getType()=="交通")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.transport);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r2));
        }
        if(cost.getType()=="娛樂")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.play);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r3));

        }
        if(cost.getType()=="購物")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.shopping);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r4));
        }
        if(cost.getType()=="家居")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.house);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r5));
        }
        if(cost.getType()=="個人")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.individual);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r6));
        }
        if(cost.getType()=="生活休閒")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.life);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r7));
        }
        if(cost.getType()=="醫療")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.hospital);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r8));
        }
        if(cost.getType()=="學習")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.learn);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r9));
        }
        if(cost.getType()=="其他支出")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.more);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r10));
        }
        if(cost.getType()=="工作收入")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.salary);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r11));
        }
        if(cost.getType()=="非工作收入")
        {
            holder.report_showrecord_icon.setImageResource(R.drawable.more);
            holder.report_showrecord_icon.setColorFilter(mCtx.getResources().getColor(R.color.r12));
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

        public TextView report_showrecord_cost,report_showrecord_test;
        public ImageView report_showrecord_icon;

        public  CostHolder(@NotNull View itemView){
            super(itemView);
            report_showrecord_icon=itemView.findViewById(R.id.report_showrecord_icon);
            report_showrecord_cost=itemView.findViewById(R.id.report_showrecord_cost);
            report_showrecord_test=itemView.findViewById(R.id.report_showrecord_test);
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