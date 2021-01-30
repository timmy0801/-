package com.example.juuuuuuuuu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juuuuuuuuu.Notigications.APIService;
import com.example.juuuuuuuuu.Notigications.Client;
import com.example.juuuuuuuuu.Notigications.Data;
import com.example.juuuuuuuuu.Notigications.MyResponse;
import com.example.juuuuuuuuu.Notigications.Sender;
import com.example.juuuuuuuuu.Notigications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareCost extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Share> list;
    private ShareAdapter adapter;
    private DatabaseReference databaseReference,postref;
    private FirebaseAuth auth;
    private String people,categorymom,category,money,friendnum;
    private ArrayList<String> ttt;
    private ProgressDialog progressDialog;
    private APIService apiService;
    private TextView share_money_type;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ///鍵盤永遠隱藏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_cost);
        progressDialog=new ProgressDialog(ShareCost.this);
        progressDialog.setMessage("載入中，請稍候");
        progressDialog.show();
        share_money_type=findViewById(R.id.share_money_type);
        back=findViewById(R.id.iv);
        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        auth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        ttt=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").getValue()!=null){

                    for (int y=2100;y>=1950;y--){
                        for (int m=12;m>=1;m--){
                            for (int d=31;d>=1;d--){
                                for (int tt=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).getChildrenCount();tt>=1;tt--){

                                    if(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).getValue()!=null){
                                        if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("original_cost").getValue()==null){
                                            people=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("notiby").getValue().toString();
                                            categorymom=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("typeMom").getValue().toString();
                                            category=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("type").getValue().toString();
                                            money=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("cost").getValue().toString();
                                            friendnum=String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("friends").getChildrenCount()+1);
                                            list.add(new Share(people,categorymom+"-"+category,money,friendnum,y+"/"+m+"/"+d));
                                            ttt.add(String.valueOf(tt));
                                        }else {
                                            people=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("notiby").getValue().toString();
                                            categorymom=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("typeMom").getValue().toString();
                                            category=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("type").getValue().toString();
                                            money=dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("original_cost").getValue().toString();
                                            friendnum=String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("friends").getChildrenCount()+1);
                                            share_money_type.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(String.valueOf(y)).child(String.valueOf(m)).child(String.valueOf(d)).child(String.valueOf(tt)).child("cost_type").getValue().toString());
                                            list.add(new Share(people,categorymom+"-"+category,money,friendnum,y+"/"+m+"/"+d));
                                            ttt.add(String.valueOf(tt));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    setadapter();
                    progressDialog.dismiss();
                }else {
                    Log.v("fuck","fuck");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareCost.this, HomePage.class);
                startActivity(intent);

            }
        });
    }
    public void setadapter(){
        recyclerView=findViewById(R.id.share);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShareAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new ShareAdapter.OnItemClickListener() {
            @Override
            public void onSaveClick(int position,Double cmoney) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int a = (int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).getChildrenCount();
                        int d = (int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).getChildrenCount();
                        int b = (int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("friends").getChildrenCount();
                        String notiby = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("notiby").getValue().toString();
                        String img = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("img").getValue().toString();
                        String name = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("name").getValue().toString();
                        String type = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("type").getValue().toString();
                        String typeMom = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("typeMom").getValue().toString();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        postref=FirebaseDatabase.getInstance().getReference("Posts");
                        String postid=postref.push().getKey();
                        hashMap.put("postid",postid);
                        hashMap.put("user",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        // hashMap.put("cost",String.valueOf(cmoney));
                        hashMap.put("cost_pic","https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media");
                        hashMap.put("date",list.get(position).getTime());
                        hashMap.put("type",type);
                        hashMap.put("typeMom",typeMom);
                        final String myname = dataSnapshot.child(auth.getCurrentUser().getUid()).child("ris_Username").getValue().toString();
                        final String notibyID = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("notibyID").getValue().toString();
                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("typeMom").setValue(typeMom);
                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("img").setValue(img);
                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("type").setValue(type);
                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("name").setValue(name);
                        if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("cost_pic").getValue() != null){
                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("cost_pic").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("cost_pic").getValue().toString());
                        }
                        if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("original_cost").getValue() == null) {
                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("cost").setValue(getInt(cmoney));
                            hashMap.put("cost",String.valueOf(getInt(cmoney)));
                        } else {
                            final String asshole = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("cost_typeID").getValue().toString();
                            final String cost_type = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("cost_type").getValue().toString();
                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("cost_typeID").setValue(asshole);
                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("cost_type").setValue(cost_type);
                            DatabaseReference databaseReferenceq=FirebaseDatabase.getInstance().getReference("Money").child(list.get(position).getTime()).child(asshole);
                            databaseReferenceq.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Double cock=Double.parseDouble(dataSnapshot.getValue().toString());
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("name").setValue(getInt(cmoney*cock));
                                    hashMap.put("cost",String.valueOf(getInt(cmoney*cock)));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        postref.child(postid).setValue(hashMap);
                        for (int l = 1; l <= b; l++) {
                            String fnd = dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).child("friends").child(String.valueOf(l)).getValue().toString();
                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(list.get(position).getTime()).child(String.valueOf(a + 1)).child("friends").child(String.valueOf(l)).setValue(fnd);
                            postref.child(postid).child("friends").child(String.valueOf(l)).setValue(fnd);
                        }
                        databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(ttt.get(position)).removeValue();
                        if (d!=Integer.valueOf(ttt.get(position))){
                            for (int dick=Integer.valueOf(ttt.get(position));dick<=d;d++){
                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).getValue());
//                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).child("notiby").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).child("notiby").getValue().toString());
//                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).child("name").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).child("name").getValue().toString());
//                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).child("type").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).child("type").getValue().toString());
//                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).child("img").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).child("img").getValue().toString());
//                                databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick)).child("cost").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(dick+1)).child("cost").getValue().toString());
                            }
                            databaseReference.child(auth.getCurrentUser().getUid()).child("notifyrecord").child(list.get(position).getTime()).child(String.valueOf(d)).removeValue();
                        }
                        databaseReference.child(notibyID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DatabaseReference chatref=FirebaseDatabase.getInstance().getReference();

                                if (String.valueOf(cmoney)!=list.get(position).getMoney()){
                                    sendNotification(notibyID,auth.getCurrentUser().getUid(),"來自"+myname+"的分帳金額修改");
                                    chatref.child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child(notibyID).push().setValue(new Chat(list.get(position).getTime()+"中的"+list.get(position).getCategory()+"已修改為"+cmoney+"元", FirebaseAuth.getInstance().getCurrentUser().getUid()));
                                    chatref.child("chats").child(notibyID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                                            .setValue(new Chat(list.get(position).getTime()+"中的"+list.get(position).getCategory()+"已修改為"+cmoney+"元", FirebaseAuth.getInstance().getCurrentUser().getUid()));

                                }else {
                                    sendNotification(notibyID,auth.getCurrentUser().getUid(),myname+"已確認分帳金額");
                                    chatref.child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child(notibyID).push().setValue(new Chat("以確認"+list.get(position).getTime()+"中"+list.get(position).getCategory()+"的分帳金額", FirebaseAuth.getInstance().getCurrentUser().getUid()));
                                    chatref.child("chats").child(notibyID).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                                            .setValue(new Chat("以確認"+list.get(position).getTime()+"中"+list.get(position).getCategory()+"的分帳金額", FirebaseAuth.getInstance().getCurrentUser().getUid()));
                                }
                                list.remove(position);
                                ttt.remove(position);
                                adapter.notifyItemRemoved(position);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        adapter.notifyDataSetChanged();

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

                    Data data=new Data(username,R.mipmap.ic_launcher,"",message,reciever);
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
    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
}
