package com.example.juuuuuuuuu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

public class FriendListHolder extends RecyclerView.ViewHolder {
    public ImageView friend_image;
    public TextView friend_name;
    public TextView message_list;

    public  FriendListHolder(@NotNull View itemView){
            super(itemView);
            friend_image=itemView.findViewById(R.id.friend_image);
            friend_name=itemView.findViewById(R.id.friend_name);
            message_list=itemView.findViewById(R.id.message_list);

    }
    public void setFriend_image(String Imgurl){
        friend_image=itemView.findViewById(R.id.friend_image);
        if (Imgurl!=null){
            Picasso.get().load(Imgurl).into(friend_image);
        }
    }
}
