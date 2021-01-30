package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FdViewTabB extends Fragment {
    RecyclerView selfview;
    DatabaseReference Ref,dateref,reelref;
    String myid,year,img,name,cost,type,tagfriends;
    ArrayList<FdUpdate> arrayList;
    private SelfdataAdapter SelfdataAdapter;
    private ArrayList<Post> userinfo,reverselist;
    ArrayList<String> friendlist;
    String cost_pic;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fd_view_tab_b, container, false);
        selfview = view.findViewById(R.id.selfview);
        selfview.setHasFixedSize(true);
        selfview.setLayoutManager(new LinearLayoutManager(getActivity()));
        userinfo=new ArrayList<>();
        reverselist=new ArrayList<>();
        CheckSelf();
        return  view;
    }

    private void CheckSelf() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
        Query query=FirebaseDatabase.getInstance().getReference("Posts").orderByChild("count");
        /*reference*/query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userinfo.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    if (post.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        userinfo.add(post);
                    }
                }
                for (int i=0;i<userinfo.size();i++){
                    reverselist.add(userinfo.get(userinfo.size()-i-1));
                }
                SelfdataAdapter=new SelfdataAdapter(getActivity(),reverselist);
                selfview.setAdapter(SelfdataAdapter);
                SelfdataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

