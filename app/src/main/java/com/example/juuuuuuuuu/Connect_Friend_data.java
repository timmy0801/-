package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Connect_Friend_data extends AppCompatActivity {
    TextView Friend_Username;
    RecyclerView friend_view;
    ImageView Friend_Image;
    String userID;
    PostAdapter postAdapter;
    ArrayList<Post> arrayList;
    private DatabaseReference databaseReference;
    private Button delete;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect__friend_data);
        Friend_Image=findViewById(R.id.Friend_Image);
        Friend_Username=findViewById(R.id.Friend_Username);
        friend_view=findViewById(R.id.friend_view);
        arrayList=new ArrayList<>();
        userID=getIntent().getStringExtra("userID");
        friend_view.setHasFixedSize(true);
        friend_view.setLayoutManager(new LinearLayoutManager(this));
        postAdapter=new PostAdapter(this,arrayList);
        friend_view.setAdapter(postAdapter);
        delete=findViewById(R.id.delete);
        back=findViewById(R.id.connect_backtohome);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        getFriendData();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Friends").child(userID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue()!=null){
                    delete.setText("刪除好友");
                }else {
                    delete.setText("新增好友");
                }

                getuserInfo(Friend_Image,Friend_Username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Connect_Friend_data.this, FdUpdatePage.class);
                intent.putExtra("position","0");
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete.getText().toString().equals("刪除好友")){
                    databaseReference.child("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(userID).removeValue();
                    databaseReference.child("Friends").child(userID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                    Toast.makeText(Connect_Friend_data.this, "已刪除好友", Toast.LENGTH_LONG).show();
                    delete.setText("新增好友");
                }
                else if(delete.getText().toString().equals("新增好友")){
                    databaseReference.child("Friend_Req").child(userID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("request_type")
                            .setValue("received");
                    Toast.makeText(Connect_Friend_data.this, "邀請已發送", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getuserInfo(ImageView Friend_Image,TextView Friend_Username) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Friends").child(userID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue()!=null){
                    User user=dataSnapshot.child("User").child(userID).getValue(User.class);
//                String name=dataSnapshot.child("ris_Username").getValue().toString();
//                username.setText(name);
                    Friend_Username.setText(user.getRis_Username());
                    if (dataSnapshot.child("User").child(userID).hasChild("imageurl")){
                        String url=dataSnapshot.child("User").child(userID).child("imageurl").getValue().toString();
                        Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().transform(new CircleTransform()).into(Friend_Image);
                    }else {
                        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media")
                                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().transform(new CircleTransform()).into(Friend_Image);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getFriendData() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    if (post.getUser().equals(userID)){
                        arrayList.add(post);
                    }
                }
               postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

