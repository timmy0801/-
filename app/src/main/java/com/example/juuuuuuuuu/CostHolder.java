package com.example.juuuuuuuuu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

public class CostHolder extends RecyclerView.ViewHolder {

    public TextView showrecord_test,showrecord_cost;

    public CostHolder(@NotNull View itemView) {
        super(itemView);

        showrecord_test = itemView.findViewById(R.id.showrecord_test);
        showrecord_cost = itemView.findViewById(R.id.showrecord_cost);

    }
}