package com.example.juuuuuuuuu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Documented;
import java.util.ArrayList;

class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareHolder> {
    private ArrayList<Share> list;
    private Context mCtx;
    private OnItemClickListener clickListener;


    public ShareAdapter(Context mCtx, ArrayList<Share> list) {
        this.mCtx = mCtx;
        this.list = list;
    }
    @NonNull
    @Override
    public ShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.share_cost_recycle,null);
        return new ShareHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShareHolder holder, int position) {
        Share share= list.get(position);
        holder.cate.setText(share.getCategory());
        holder.moneymoney.setText(share.getMoney());
        holder.friendnum.setText(share.getFriendnum());
        holder.people.setText(share.getPeople());
        holder.time.setText(share.getTime());
    }
    public interface OnItemClickListener{
        void onSaveClick(int position,Double cmoney);
    }

    @Override
    public int getItemCount() { return list.size();
    }
    public void setOnClickListener(OnItemClickListener hello){clickListener=hello;}

    public class ShareHolder extends RecyclerView.ViewHolder{

        public TextView cate,friendnum,people,time;
        public EditText moneymoney;
        public Button share_bt;
        public  ShareHolder(@NotNull View itemView){
            super(itemView);
            moneymoney=itemView.findViewById(R.id.moneymoney);
            moneymoney.setInputType(EditorInfo.TYPE_CLASS_PHONE);
            friendnum=itemView.findViewById(R.id.people);
            people=itemView.findViewById(R.id.share_proname);
            cate=itemView.findViewById(R.id.cate);
            share_bt=itemView.findViewById(R.id.share_bt);
            time=itemView.findViewById(R.id.share_time);
            share_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Double cmoney=Double.parseDouble(moneymoney.getText().toString());
                    clickListener.onSaveClick(position,cmoney);

                }
            });

        }


    }

}

