package com.example.juuuuuuuuu;

import android.content.Context;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;


class FdUpdateAdapter extends RecyclerView.Adapter<FdUpdateAdapter.FdUpdateHolder> {
    private ArrayList<FdUpdate> list;
    private Context mCtx;
    private OnItemClickListener clickListener;
    private  String tag;
    private  FirebaseUser firebaseUser;
    int i=0,j;
    ArrayList<String> friend;
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onFavoriteClick(int position);
    }

    public void setOnClickListener(OnItemClickListener hello){clickListener=hello;}


    public FdUpdateAdapter(Context mCtx,ArrayList<FdUpdate> list){
        this.mCtx=mCtx;
        this.list=list;
    }
    @NonNull
    @Override
    public FdUpdateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.activity_fd_updata_page_data,null);
        return new FdUpdateHolder(view);

    };
    @Override
    public void onBindViewHolder(@NonNull FdUpdateHolder holder, int position) {
        FdUpdate FriendId= list.get(position);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Picasso.get().load(FriendId.getCost_pic()).into(holder.cost_pic);

        tag="";
        holder.post_name.setText(FriendId.getPost_name());
        holder.note.setText(FriendId.getNote());
        holder.cost.setText("NT＄"+FriendId.getCost());
        //holder.tag_friends.setText(FriendId.getArrayList());
        Picasso.get().load(FriendId.getFriend_img()).into(holder.friend_img);
        Picasso.get().load(FriendId.getCost_pic()).into(holder.cost_pic);
        publisherInfo(holder.friend_img,FriendId.getFriend_img(),holder.post_name,FriendId.getPostid());
        friend=new ArrayList<>();
        friend=FriendId.getArrayList();
        int l=friend.size();
        for (int k=0;k<l;k++){
            tag+=k;
        }
        holder.tag_friends.setText(tag);





    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FdUpdateHolder extends RecyclerView.ViewHolder{
        public ImageView friend_img,cost_pic,favorite;
        public TextView post_name,tag_friends,note,cost,date,post_note;
        public ChipGroup chipGroup;
        public FdUpdateHolder(@NonNull View itemView) {
            super(itemView);
            cost=itemView.findViewById(R.id.cost);
            friend_img=itemView.findViewById(R.id.friend_img);
            cost_pic=itemView.findViewById(R.id.cost_pic);
            post_name=itemView.findViewById(R.id.post_name);
            note=itemView.findViewById(R.id.note);
            favorite=itemView.findViewById(R.id.Favorite);
            date=itemView.findViewById(R.id.post_date);
            tag_friends=itemView.findViewById(R.id.tag_friends);
            post_note=itemView.findViewById(R.id.post_note);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onFavoriteClick(position);
                        }
                        if (favorite.getTag().equals("like")){

                        }else if (favorite.getTag().equals("liked")){
                            String ID=FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            int k=arrayList.get(j).getNum();
//                            FirebaseDatabase.getInstance().getReference().child("favorite").child(ID).child(Integer.toString(arrayList.get(j).getNum())).removeValue();
                        }
                    }
                }
            });
            cost_pic.setOnClickListener(new View.OnClickListener() {
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
                        if (clickListener!=null){
                            int position=getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                clickListener.onItemClick(position);
                                i=0;
                            }
                        }

                    }
                }
            });
        }
    }
    private  void publisherInfo(ImageView imageprofile,String image,TextView username,String userid){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("User").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getRis_Username());
                Picasso.get().load(image).into(imageprofile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
