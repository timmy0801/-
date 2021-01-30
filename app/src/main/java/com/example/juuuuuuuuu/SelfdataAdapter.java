package com.example.juuuuuuuuu;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


class SelfdataAdapter extends RecyclerView.Adapter<SelfdataAdapter.SelfdataHolder> {
    public ArrayList<Post> Post;
    public Context mContext;
    private FirebaseUser firebaseUser;
    private ArrayList<String> Friendarraylist;
    private OnItemClickListener clickListener;
    private String tags;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener hello){clickListener=hello;}
    public SelfdataAdapter(Context mCtx, ArrayList<Post> Post){
        this.mContext=mCtx;
        this.Post=Post;
    }
    @NonNull
    @Override
    public SelfdataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_selfdata,null);
        return new SelfdataHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SelfdataHolder holder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Post post= Post.get(position);
        Picasso.get().load(post.getCost_pic()).into(holder.self_cost_pic);
        holder.self_date.setText(post.getDate());
        holder.self_cost.setText(post.getCost());
        holder.self_note.setText(post.getType());
        publisherInfo(holder.self_img,holder.self_name,post.getUser());

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts").child(post.getPostid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                if (dataSnapshot.hasChild("friends")){
                    Friendarraylist=new ArrayList<>();
                    int i=(int)dataSnapshot.child("friends").getChildrenCount();
                    List friends=dataSnapshot.child("friends").getValue(t);
                    for (int j=1;j<=i;j++){
                        String test=friends.get(j).toString();
                        Friendarraylist.add(test);
                    }
                    getname(holder.self_tag_friends);
                    holder.self_post_friends_img.setVisibility(View.VISIBLE);

                }
                else {
                    holder.self_tag_friends.setText("");
                    holder.self_post_friends_img.setVisibility(View.GONE);
                }
                if (dataSnapshot.hasChild("other")){
                    holder.self_post_note_img.setVisibility(View.VISIBLE);
                    holder.self_post_note.setText(dataSnapshot.child("other").getValue().toString());
                }
                else {
                    holder.self_post_note_img.setVisibility(View.GONE);
                }
                if (dataSnapshot.hasChild("location")) {
                    holder.data_img.setVisibility(View.VISIBLE);
                    holder.self_data_position.setText(dataSnapshot.child("location").getValue().toString());
                }else {
                    holder.self_data_position.setText("");
                    holder.data_img.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                   if (dataSnapshot.hasChild("friends")){
//                       Friendarraylist=new ArrayList<>();

//                        for (int j=1;j<=i;j++){
//                            Friendarraylist.add(dataSnapshot.child("friends").child(Integer.toString(j)).getValue().toString());
//                        }
//                        getname(holder.self_tag_friends);
//                   }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    private void getname(TextView tag_friends) {
        for (String ID:Friendarraylist) {
            DatabaseReference nameref = FirebaseDatabase.getInstance().getReference("User").child(ID);
            nameref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    tags=tags+" "+user.getRis_Username();
                    tag_friends.setText(tags);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        Friendarraylist.clear();
        tags=" ";
    }

    @Override
    public int getItemCount() {
        return Post.size();
    }

    public class SelfdataHolder extends RecyclerView.ViewHolder{
        public ImageView self_img,self_cost_pic,self_post_friends_img,self_post_note_img,data_img;
        TextView self_name,self_tag_friends,self_note,self_cost,self_date,self_post_note,self_data_position;
        public SelfdataHolder(@NonNull View itemView) {
            super(itemView);
            self_date=itemView.findViewById(R.id.self_date);
            self_img=itemView.findViewById(R.id.self_img);
            self_cost_pic=itemView.findViewById(R.id.self_cost_pic);
            self_name=itemView.findViewById(R.id.self_name);
            self_cost=itemView.findViewById(R.id.self_cost);
            self_tag_friends=itemView.findViewById(R.id.self_tag_friends);
            self_note=itemView.findViewById(R.id.self_note);
            self_post_note=itemView.findViewById(R.id.self_post_note);
            self_post_note_img=itemView.findViewById(R.id.self_post_note_img);
            self_post_friends_img=itemView.findViewById(R.id.self_post_friend_img);
            data_img=itemView.findViewById(R.id.data_img);
            self_data_position=itemView.findViewById(R.id.self_data_position);
        }
    }
    private  void publisherInfo(ImageView imageprofile,TextView username,String userid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("User").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
//                String name=dataSnapshot.child("ris_Username").getValue().toString();
//                username.setText(name);
                username.setText(user.getRis_Username());
                if (dataSnapshot.hasChild("imageurl")){
                    String url=dataSnapshot.child("imageurl").getValue().toString();
                    Picasso.get().load(url).into(imageprofile);
                }else {
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media").into(imageprofile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
