package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FdListPage extends AppCompatActivity {
ImageButton add_friend_send;
EditText friend_id;
String friend,senderUID,nUser;
DatabaseReference UserRef,FriendRef,FriendDB,Uref,listdb;
FirebaseAuth auth,fauth;
FirebaseUser CurrentUser,nowUser;
String cstate,userID;
private RecyclerView Friend_list;
ArrayList<String> Friendlist;
String name,img;
Friendadapter friendadapter;
ArrayList<Friend> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd_list_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        add_friend_send=findViewById(R.id.add_friend_list);
        cstate="not_friends";

            fauth=FirebaseAuth.getInstance();
            nowUser=fauth.getCurrentUser();
            nUser=nowUser.getUid();
        Uref=FirebaseDatabase.getInstance().getReference().child("User");


//        showlist();



        add_friend_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friend=friend_id.getText().toString();  //獲得好友帳號
                auth=FirebaseAuth.getInstance();
                UserRef= FirebaseDatabase.getInstance().getReference().child("User").child(friend); //連結DB以好友帳號為KEY的連結
                FriendRef=FirebaseDatabase.getInstance().getReference("Friend_Req");
                userID=UserRef.getKey(); //get 好友帳號

                CurrentUser=auth.getCurrentUser(); //自己帳號

                senderUID=auth.getCurrentUser().getUid(); ///自己帳號的id
                if(cstate.equals("not_friends")) {
                    if (userID.trim() != null) {
                        FriendRef.child(userID).child(senderUID).child("request_type")
                                .setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(FdListPage.this, "邀請已發送", Toast.LENGTH_LONG).show();
                                    Intent gohome = new Intent(FdListPage.this, HomePage.class);
                                    startActivity(gohome);

                                } else {
                                    final AlertDialog.Builder dialog = new AlertDialog.Builder(FdListPage.this);
                                    dialog.setTitle("Alert");
                                    dialog.setMessage("傳送邀請失敗");
                                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        });
                    }
                }
            }
        });

        getFriendId();

    }

    public void getFriendId(){
        FriendDB=FirebaseDatabase.getInstance().getReference().child("Friends").child(nUser);
        FriendDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Friendlist=new ArrayList<>();
                if (dataSnapshot.hasChildren()){
                    String temp=dataSnapshot.getValue().toString();
                    String[] test = temp.split(",");
                    for(int k = 0; k<test.length; k++){
                        String id = test[k].split("=")[0].replaceAll("\\p{Punct}", "");
                        id = id.trim();
                        Friendlist.add(id);

                    }
                }
                SetUserInfo(Friendlist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SetUserInfo(ArrayList<String> FriendID){
        friends = new ArrayList<>();
        for(int i = 0; i<FriendID.size();i++){
            listdb=FirebaseDatabase.getInstance().getReference("User").child(FriendID.get(i));
            listdb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name=dataSnapshot.child("ris_Username").getValue().toString();
                    if (!dataSnapshot.hasChild("imageurl")){
                        img="https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media";
                    }
                    else {
                        img=dataSnapshot.child("imageurl").getValue().toString();
                    }

                    friends.add(new Friend(
                            name,
                            img,
                            ""
                    ));
                    setadapter();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void setadapter(){
        Friend_list=findViewById(R.id.Friend_list);
        Friend_list.setHasFixedSize(true);
        Friend_list.setLayoutManager(new LinearLayoutManager(this));
        friendadapter = new Friendadapter(this, friends);
        Friend_list.setAdapter(friendadapter);
        friendadapter.setOnclicklistener(new Friendadapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    Intent intent=new Intent(FdListPage.this,Chatroom.class);
                    intent.putExtra("fid",Friendlist.get(position));
                    startActivity(intent);
            }
        });
        friendadapter.notifyDataSetChanged();
    }



//   public void showlist(){
//       FirebaseRecyclerOptions<Friend> options=new FirebaseRecyclerOptions.Builder<Friend>().setQuery(FriendDB,Friend.class).build();
//       adapter=new FirebaseRecyclerAdapter<Friend, FriendListHolder>(options) {
//           @Override
//           protected void onBindViewHolder(@NonNull final FriendListHolder friendListHolder, int position, @NonNull Friend friend) {
//
//             DatabaseReference test=FriendDB.child(getRef(position).getKey());
////                friendListHolder.friend_name.setText(friend.getUsername());
////                friendListHolder.setFriend_image(friend.getimageurl());
//
//               final String FriendUid=test.getKey();
//               int a=1;
//
//               FriendDB.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                          if (dataSnapshot.child(nUser).hasChild(FriendUid)){
//                              Uref.child(FriendUid).addValueEventListener(new ValueEventListener() {
//                                  @Override
//                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                      if (dataSnapshot.exists()){
//                                          String set_username=dataSnapshot.child("username").getValue().toString();
//                                          if (!dataSnapshot.hasChild("imageurl")){}
//                                          else {
//                                              String img=dataSnapshot.child("imageurl").getValue().toString();
//                                              friendListHolder.setFriend_image(img);
//                                          }
//                                          friendListHolder.friend_name.setText(set_username);
//
//                                      }
//                                  }
//
//                                  @Override
//                                  public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                  }
//                              });
//
//                          }
//                      }
//
//                      @Override
//                      public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                      }
//                  });
//
//           }
//
//           @NonNull
//           @Override
//           public FriendListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//               View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend_layout,parent,false);
//               return new FriendListHolder(view);
//           }
//
//       };
//
//        adapter.startListening();
//        adapter.notifyDataSetChanged();
//        Friend_list.setAdapter(adapter);
//
//   }


}
