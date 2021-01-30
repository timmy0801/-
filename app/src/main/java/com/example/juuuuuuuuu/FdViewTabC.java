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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class FdViewTabC extends Fragment {
    private PostAdapter postAdapter;
    private RecyclerView favoriteview;
    DatabaseReference dataref;
    private  int count,i;
    private  String cost,friendimg,friends,imageurl,ris_Username,type;
    ArrayList<Post> arrayList;
    ArrayList<String> favoritelist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fd_view_tab_c, container, false);
        favoriteview=view.findViewById(R.id.favoriteview);

        favoriteview.setHasFixedSize(true);
        favoriteview.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList=new ArrayList<>();
        postAdapter=new PostAdapter(getActivity(),arrayList);
        favoriteview.setAdapter(postAdapter);
        checkfavorite();
        return view;
    }
    public void checkfavorite(){
        favoritelist=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoritelist.clear();
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if (snapshot.hasChild(id)){
                            favoritelist.add(snapshot.getKey());
                            readPost();
                        }
                }
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
                arrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                        for (String id:favoritelist){
                            if (post.getPostid().equals(id)){
                                arrayList.add(post);
                            }
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
