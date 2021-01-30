package com.example.juuuuuuuuu;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

class Friendadapter extends RecyclerView.Adapter<Friendadapter.FriendHolder> implements Filterable {
    private ArrayList<Friend> list,listfull;
    private Context mCtx;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnclicklistener(OnItemClickListener hi){
        clickListener=hi;
    }

    public Friendadapter(Context mCtx, ArrayList<Friend> list) {
        this.mCtx = mCtx;
        this.list = list;
        listfull=new ArrayList<>(list);
    }
    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.single_friend_layout,null);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        Friend FriendId= list.get(position);
//        holder.friend_name.setText(FriendId.getUsername());
//        holder.setimage(FriendId.getimageurl());
        getuserinfo(holder.friend_name,holder.friend_image,FriendId.getcontent());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FriendId.getcontent())){
                    for (DataSnapshot snapshot : dataSnapshot.child(FriendId.getcontent()).getChildren()) {
                        if (snapshot.hasChild("messageText")) {
                            holder.message_list.setText(snapshot.child("messageText").getValue().toString());
                        }
                    }
                }else {
                    holder.message_list.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getuserinfo(TextView name,ImageView image,String ID) {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("User").child(ID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                name.setText(user.getRis_Username());
                if (dataSnapshot.hasChild("imageurl")){
                    String url=dataSnapshot.child("imageurl").getValue().toString();
                    Picasso.get().load(url).into(image);
                }else {
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media").into(image);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return friendfilter;
    }



    private Filter friendfilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Friend> filterlist=new ArrayList<>();
            if (charSequence==null || charSequence.length()==0){
                filterlist.addAll(listfull);
            }else {
                String FilterPattern=charSequence.toString().toLowerCase().trim();
                for (Friend friend:listfull){
                    if (friend.getUsername().toLowerCase().contains(FilterPattern)){
                        filterlist.add(friend);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterlist;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Friend)resultValue).getUsername();
        }
    };

    public class FriendHolder extends RecyclerView.ViewHolder{
        public ImageView friend_image;
        public TextView friend_name;
        public TextView message_list;
        public  FriendHolder(@NotNull View itemView){
            super(itemView);
            friend_image=itemView.findViewById(R.id.friend_image);
            friend_name=itemView.findViewById(R.id.friend_name);
            message_list=itemView.findViewById(R.id.message_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(clickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
        public void setimage(String imageurl){
            friend_image=itemView.findViewById(R.id.friend_image);
            if (imageurl!=null){
                Picasso.get().load(imageurl)./*memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().transform(new CircleTransform()).*/into(friend_image);
            }
        }

    }
}