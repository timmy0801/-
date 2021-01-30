package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FdViewTabD extends Fragment {
    ImageButton add_friend_send;
    SearchView searchView;
    String friend, senderUID, nUser;
    DatabaseReference UserRef, FriendRef, FriendDB, Uref, listdb,friendDB;
    FirebaseAuth auth, fauth;
    FirebaseUser CurrentUser, nowUser;
    String cstate, userID,username;
    private RecyclerView Friend_list;
    ArrayList<String> Friendlist;
    String name, img,message;
    Friendadapter friendadapter;
    private ProgressDialog progressdialog;
    ArrayList<Friend> friends;
    int count=0,i;
    private ProgressDialog progressDialog;
    boolean none=false,isF=false;
    @Override
    public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_fd_view_tab_d, container, false);
        add_friend_send = (ImageButton) view.findViewById(R.id.add_friend_list);
        searchView=view.findViewById(R.id.searchview);
//设置输入字体颜色
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);//字体颜色
        textView.setTextSize(14);//字体、提示字体大小
        textView.setHintTextColor(Color.WHITE);//提示字体颜色
        if (searchView != null) {
            try {        //--拿到字节码
                Class<?> argClass = searchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(searchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cstate = "not_friends";
        Friend_list =view.findViewById(R.id.Friend_list);
        fauth = FirebaseAuth.getInstance();
        nowUser = fauth.getCurrentUser();
        nUser = nowUser.getUid();
        Friendlist = new ArrayList<>();
        add_friend_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater1=LayoutInflater.from(view.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                new  AlertDialog.Builder(view.getContext()).setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        none=false;
                        isF=false;
                        EditText hello=v.findViewById(R.id.hello);
                        friend = hello.getText().toString().trim();  //獲得好友帳號
                        Uref = FirebaseDatabase.getInstance().getReference("User");
                        auth = FirebaseAuth.getInstance();
//                        UserRef = FirebaseDatabase.getInstance().getReference().child("User").child(friend); //連結DB以好友帳號為KEY的連結
                        FriendRef = FirebaseDatabase.getInstance().getReference("Friend_Req");
                        friendDB=FirebaseDatabase.getInstance().getReference("Friends").child(auth.getCurrentUser().getUid());

//                        userID = UserRef.getKey(); //get 好友帳號
                        Uref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                progressDialog=new ProgressDialog(getActivity());
                                progressDialog.setMessage("核對資料中");
                                progressDialog.show();

                                for (DataSnapshot a : dataSnapshot.getChildren()) {


                                    if(a.child("ris_Username").getValue().toString().equals(friend)){
                                        userID=a.getKey();
                                        break;
                                    }
                                    else {
                                        userID=friend;
                                    }

                                }
                                for (DataSnapshot a : dataSnapshot.getChildren()) {
                                    if(a.getKey()==userID){

                                        none=false;
                                        break;
                                    }
                                    else {
                                        none=true;
                                    }
                                }
                                CurrentUser = auth.getCurrentUser(); //自己帳號
                                friendDB.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        count=0;
                                        for (DataSnapshot a : dataSnapshot.getChildren()) {

                                            count++;
                                            if(!a.getKey().equals(userID)){

                                                isF=false;

                                            }
                                            else if(count==a.getChildrenCount()){
                                                progressDialog.dismiss();
                                            }
                                            else {
                                                isF=true;
                                                progressDialog.dismiss();

                                                break;
                                            }
                                        }
                                        senderUID = auth.getCurrentUser().getUid(); ///自己帳號的id
                                        if (userID.equals(senderUID)){
                                            progressDialog.dismiss();
                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setTitle("注意");
                                            dialog.setMessage("無法加自己為好友ㄛ");
                                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                }
                                            });
                                            dialog.show();
                                        }
                                        else if(none==true){
                                            progressDialog.dismiss();
                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setTitle("注意");
                                            dialog.setMessage("沒有這個人ㄛ");
                                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                }
                                            });
                                            dialog.show();
                                        }

                                        else if(isF==true){

                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setTitle("注意");
                                            dialog.setMessage("你們已經是好友了！");
                                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                }
                                            });
                                            dialog.show();
                                        }
                                        else if (cstate.equals("not_friends")) {
                                            progressDialog.dismiss();
                                            if (userID.trim() != null) {
                                                FriendRef.child(userID).child(senderUID).child("request_type")
                                                        .setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "邀請已發送", Toast.LENGTH_LONG).show();
//                                                    Intent gohome = new Intent(getActivity(), HomePage.class);
//                                                    startActivity(gohome);

                                                        } else {
                                                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
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
                }).show();

            }
        });
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                friendadapter.getFilter().filter(s);
                return false;
            }
        });
        getFriendId();
        return view;
    }

    public void getFriendId() {
        FriendDB = FirebaseDatabase.getInstance().getReference().child("Friends").child(nUser);
        FriendDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Friendlist = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    String temp = dataSnapshot.getValue().toString();
                    String[] test = temp.split(",");
                    for (int k = 0; k < test.length; k++) {
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

    public void SetUserInfo(ArrayList<String> FriendID) {
        friends = new ArrayList<>();
        for ( i = 0; i < FriendID.size(); i++) {
//            getMessage(i);
            listdb = FirebaseDatabase.getInstance().getReference("User").child(FriendID.get(i));
             int finalI = i;
            listdb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("ris_Username").getValue().toString();
                    if (!dataSnapshot.hasChild("imageurl")) {
                        img = "https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media";
                    } else {
                        img = dataSnapshot.child("imageurl").getValue().toString();
                    }

                    friends.add(new Friend(
                            name,
                            img,
                            FriendID.get(finalI)
                    ));
                    setadapter();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    public void setadapter() {

        Friend_list.setHasFixedSize(true);
        Friend_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendadapter = new Friendadapter(getActivity(), friends);
        Friend_list.setAdapter(friendadapter);
        friendadapter.setOnclicklistener(new Friendadapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), Chatroom.class);
                intent.putExtra("fid", friends.get(position).getcontent());
                startActivity(intent);
            }
        });
        friendadapter.notifyDataSetChanged();
    }

}







