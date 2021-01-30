package com.example.juuuuuuuuu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.juuuuuuuuu.Notigications.APIService;
import com.example.juuuuuuuuu.Notigications.Client;
import com.example.juuuuuuuuu.Notigications.Data;
import com.example.juuuuuuuuu.Notigications.MyResponse;
import com.example.juuuuuuuuu.Notigications.Sender;
import com.example.juuuuuuuuu.Notigications.Token;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chatroom extends AppCompatActivity {
//Flo;atingActionButton chat_send;

    Button chat_send;
    EditText chat_message;
    TextView chat_friend;
    DatabaseReference chatref,userRef,cc;
    private FirebaseListAdapter<Chat> adapter;
    private ImageButton backtohome;
    ListView showlist;
    String username,fid,myname;
    FirebaseAuth auth;
    APIService apiService;
    Boolean notify=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        backtohome=findViewById(R.id.chat_backtohome);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        fid=getIntent().getStringExtra("fid");
        auth=FirebaseAuth.getInstance();
        final String currentUser=auth.getCurrentUser().getUid();
        chat_friend=findViewById(R.id.chat_friend);
        chat_send=findViewById(R.id.chat_send);
        chat_message=findViewById(R.id.chat_message);
        chatref=FirebaseDatabase.getInstance().getReference();
        showlist=findViewById(R.id.showlist);
        chatref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myname=dataSnapshot.child("User").child(currentUser).child("ris_Username").getValue().toString();
                final String fname=dataSnapshot.child("User").child(fid).child("ris_Username").getValue().toString();
                chat_friend.setText(fname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Chatroom.this, FdUpdatePage.class);
                intent.putExtra("position","3");
                startActivity(intent);
            }
        });

        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify=true;
                chatref.child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(fid).push().setValue(new Chat(chat_message.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                chatref.child("chats").child(fid).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                        .setValue(new Chat(chat_message.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                final String msg= chat_message.getText().toString();
                cc=FirebaseDatabase.getInstance().getReference("User").child(fid);
                cc.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        sendNotification(fid,auth.getCurrentUser().getUid(),msg);

                        notify=false;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                chat_message.setText("");
            }
        });

        Query query=chatref.child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(fid);
        FirebaseListOptions<Chat> options=new FirebaseListOptions.Builder<Chat>().setQuery(query, Chat.class).setLayout(R.layout.activity_chatmessage).build();
        adapter=new FirebaseListAdapter<Chat>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Chat model, int position) {
                final TextView chat_userID,chat_time,chat_content,chat_content2;
//                chat_userID=v.findViewById(R.id.chat_userID);
                chat_time=v.findViewById(R.id.chat_time);

                chat_content=v.findViewById(R.id.chat_content);
                chat_content2=v.findViewById(R.id.chat_content2);
                userRef=FirebaseDatabase.getInstance().getReference("User").child(model.getMessageUser());
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        username=dataSnapshot.child("ris_Username").getValue().toString();
                        //chat_userID.setText(username);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                android.text.format.DateFormat dateFormat=new android.text.format.DateFormat();
                chat_time.setText(dateFormat.format("MM/dd (HH:mm)",model.getMessageTime()).toString());
                if (currentUser.equals(model.getMessageUser())){
                    chat_content2.setText(model.getMessageText());
                    chat_content.setText("");
                    chat_content2.setBackgroundResource(R.mipmap.chat1);
                    chat_content.setBackground(null);

                }

                else {
                    chat_content.setText(model.getMessageText());
                    chat_content2.setText("");
                    chat_content2.setBackground(null);
                    chat_content.setBackgroundResource(R.mipmap.chat2);
                }


            }
        };
        showlist.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void sendNotification(String reciever,String username,String message){
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        DatabaseReference m=FirebaseDatabase.getInstance().getReference("User");
        Query query=tokens.orderByKey().equalTo(reciever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Token token=dataSnapshot1.getValue(Token.class);

                    Data data=new Data(username,R.mipmap.ic_launcher,myname+":"+message,"New Message",reciever);
                    Sender sender=new Sender(data,token.getToken());
                    apiService.sendNotifacation(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                            if(response.code()==200){
                                if (response.body().success!=1){
                                    Log.d("f","f");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}