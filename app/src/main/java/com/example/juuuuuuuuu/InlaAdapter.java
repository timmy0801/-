package com.example.juuuuuuuuu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

class InlaAdapter extends RecyclerView.Adapter<InlaAdapter.InlaHolder> {
    private ArrayList<Inla> list;
    private Context mCtx;


    public InlaAdapter(Context mCtx, ArrayList<Inla> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public InlaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.invoice_recycleview,null);
        return new InlaHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull InlaHolder holder, int position) {
        Inla inla= list.get(position);
        holder.invoice_id.setText(inla.getNum());
        holder.invoice_cost.setText(inla.getCost());
        holder.invoice_date.setText(inla.getDate());
        holder.gain.setText(inla.getGain());
        if (inla.getAss().equals("specials")){
            holder.prize.setImageResource(R.drawable.specials);
        }
        else if (inla.getAss().equals("special")){
            holder.prize.setImageResource(R.drawable.special);
        }
        else if (inla.getAss().equals("head")){
            holder.prize.setImageResource(R.drawable.head);
        }
        else if (inla.getAss().equals("two")){
            holder.prize.setImageResource(R.drawable.two);
        }
        else if (inla.getAss().equals("three")){
            holder.prize.setImageResource(R.drawable.three);
        }
        else if (inla.getAss().equals("four")){
            holder.prize.setImageResource(R.drawable.four);
        }
        else if (inla.getAss().equals("five")){
            holder.prize.setImageResource(R.drawable.five);
        }
        else if (inla.getAss().equals("six")){
            holder.prize.setImageResource(R.drawable.six);
        }
        else if (inla.getAss().equals("fail")){
            holder.prize.setImageResource(R.drawable.fail);
        }
        else if (inla.getAss().equals("none")){
            holder.prize.setImageResource(R.drawable.none);
        }

        Log.v("da",""+holder.invoice_date.getText().toString());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InlaHolder extends RecyclerView.ViewHolder{

        public TextView invoice_id,invoice_cost,invoice_date,gain;
        public ImageView prize;

        public  InlaHolder(@NotNull View itemView){
            super(itemView);
            invoice_id=itemView.findViewById(R.id.invoice_id);
            invoice_cost=itemView.findViewById(R.id.invoice_cost);
            invoice_date=itemView.findViewById(R.id.invoice_date);
            prize=itemView.findViewById(R.id.prize);
            gain=itemView.findViewById(R.id.gain);
        }


    }

}