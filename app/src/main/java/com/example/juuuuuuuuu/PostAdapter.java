package com.example.juuuuuuuuu;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    public Context mContext;
    public ArrayList<Post> Post;
    private ArrayList<String> Friendarraylist;
    private FirebaseUser firebaseUser;
    int i=0;
    String tags;
    public PostAdapter(Context mContext,ArrayList<Post> Post){
        this.mContext=mContext;
        this.Post=Post;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_fd_updata_page_data,null);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Post post=Post.get(position);

        Picasso.get().load(post.getCost_pic()).into(holder.cost_pic);

        holder.date.setText(post.getDate());
        holder.p_cost.setText(post.getCost());
        holder.note.setText(post.getType());
        publisherInfo(holder.friend_img,holder.post_name,post.getUser());
        holder.post_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Connect_Friend_data.class);
                intent.putExtra("userID",post.getUser());
                mContext.startActivity(intent);
            }
        });
        islike(post.getPostid(),holder.favorite);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=position;
                if (holder.favorite.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("favorite").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("favorite").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                }
            }
        });
        holder.cost_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                Handler handler=new Handler();
                Runnable runn=new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                    }
                };
                if (i==1){
                    handler.postDelayed(runn,400);
                }
                else if (i==2){
                    if (holder.favorite.getTag().equals("like")){
                        FirebaseDatabase.getInstance().getReference().child("favorite").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);
                    }
                }
            }
        });
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
                    getname(holder.tag_friends);
                    holder.post_friend_image.setVisibility(View.VISIBLE);
                }
                else {
                    holder.tag_friends.setText("");
                    holder.post_friend_image.setVisibility(View.GONE);

                }
                if (dataSnapshot.hasChild("other")){
                    holder.post_note_img.setVisibility(View.VISIBLE);
                    holder.post_note.setText(dataSnapshot.child("other").getValue().toString());
                }
                else {
                    holder.post_note_img.setVisibility(View.GONE);
                    holder.post_note.setText("");
                }
                if (dataSnapshot.hasChild("location")) {
                    holder.img.setVisibility(View.VISIBLE);
                    holder.self_position.setText(dataSnapshot.child("location").getValue().toString());
                }else {
                    holder.self_position.setText("");
                    holder.img.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    public  class PostHolder extends RecyclerView.ViewHolder{
        public ImageView friend_img,cost_pic,favorite,post_note_img,post_friend_image,img;
        public TextView post_name,tag_friends,note,p_cost,date,post_note,self_position;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            p_cost=itemView.findViewById(R.id.cost);
            friend_img=itemView.findViewById(R.id.friend_img);
            cost_pic=itemView.findViewById(R.id.cost_pic);
            post_name=itemView.findViewById(R.id.post_name);
            tag_friends=itemView.findViewById(R.id.tag_friends);
            note=itemView.findViewById(R.id.note);
            favorite=itemView.findViewById(R.id.Favorite);
            date=itemView.findViewById(R.id.post_date);
            post_note=itemView.findViewById(R.id.post_note);
            post_note_img=itemView.findViewById(R.id.post_note_img);
            post_friend_image=itemView.findViewById(R.id.post_friend_img);
            self_position=itemView.findViewById(R.id.self_position);
            img=itemView.findViewById(R.id.img);
        }
    }
    private void islike(String postid,ImageView favorite){
            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("favorite").child(postid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(firebaseUser.getUid())){
                        favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                        favorite.setTag("liked");
                    }else {
                        favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        favorite.setTag("like");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
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
