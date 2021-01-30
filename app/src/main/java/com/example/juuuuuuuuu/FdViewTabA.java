package com.example.juuuuuuuuu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FdViewTabA extends Fragment {

    private RecyclerView postview;
    private  PostAdapter postAdapter;
    private ArrayList<Post> userinfo,reverselist;
    private  ArrayList<String> followinglist;
    long postcount;
    private int year,day,month;
    String today;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_fd_view_tab, container, false);
        postview=view.findViewById(R.id.postview);
        postview.setHasFixedSize(true);
        postview.setLayoutManager(new LinearLayoutManager(getActivity()));
        userinfo=new ArrayList<>();
        reverselist=new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        today=Integer.toString(year)+"/"+Integer.toString(month+1)+"/"+Integer.toString(day);
        checkFollowing();
        return view;
    }


    private void checkFollowing(){
        followinglist=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followinglist.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    followinglist.add(snapshot.getKey());
                }
                readPost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readPost(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postcount=dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query query=FirebaseDatabase.getInstance().getReference("Posts").orderByChild("count");
        /*reference*/query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userinfo.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    for (String id:followinglist){
                        if (post.getUser().equals(id)){
                            userinfo.add(post);
                        }
                    }
                }
                for (int j=0;j<userinfo.size();j++){
                    reverselist.add(userinfo.get(userinfo.size()-j-1));
                }
                postAdapter=new PostAdapter(getActivity(),reverselist);
                postview.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
