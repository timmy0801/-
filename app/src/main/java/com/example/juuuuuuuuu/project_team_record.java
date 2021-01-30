package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class project_team_record extends Fragment {
    String project_name,project_id;
    RecyclerView project_recyclerview_teamdetail;
    Button team_button;
    private ArrayList<Post> postlist;
    String count;
    private  ProjectCostAdapter projectCostAdapter;
    private  ArrayList<String> project_frined_list;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.activity_project_team_record, container, false);
        Bundle bundle=getArguments();
        if (bundle!=null){
            project_name=bundle.getString("project_name");
            project_id=bundle.getString("project_id");
        }
        project_recyclerview_teamdetail=abc.findViewById(R.id.project_recyclerview_teamdetail);
        team_button=abc.findViewById(R.id.team_button);
        team_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),RecordPage.class);
                intent.putExtra("project_name",project_name);
                intent.putExtra("project_id",project_id);
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Project").child(project_id);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("friends")){
                            project_frined_list=new ArrayList<>();
                            int k=(int)dataSnapshot.child("friends").getChildrenCount();
                            for (int i=1;i<=k;i++){
                                project_frined_list.add(dataSnapshot.child("friends").child(Integer.toString(i)).getValue().toString());
                            }
                            intent.putExtra("friendlist",project_frined_list);
                            startActivity(intent);
                        }else {
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        project_recyclerview_teamdetail.setHasFixedSize(true);
        project_recyclerview_teamdetail.setLayoutManager(new LinearLayoutManager(getContext()));
        postlist=new ArrayList<>();
        getdata();
        return abc;
    }

    private void getdata() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postlist.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post hellopost=snapshot.getValue(Post.class);
                    if (snapshot.hasChild("friends")){
                        if (snapshot.hasChild("project")){
                            if (hellopost.getProject().equals(project_id)){
                                postlist.add(hellopost);
                            }
                        }
                    }

                }
                projectCostAdapter=new ProjectCostAdapter(getContext(),postlist);
                projectCostAdapter.setOnclicklistener(new ProjectCostAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(getActivity(),RecordChangeOut.class);
                        intent.putExtra("cost",postlist.get(position).getCost());
                        intent.putExtra("date",postlist.get(position).getDate());
                        intent.putExtra("type",postlist.get(position).getType());
                        intent.putExtra("typemom",postlist.get(position).getTypeMom());
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("record")){
                                    if (dataSnapshot.child("record").hasChild(postlist.get(position).getDate())){
                                        count=Integer.toString((int) dataSnapshot.child("record").child(postlist.get(position).getDate()).getChildrenCount());
                                        intent.putExtra("count",count);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                project_recyclerview_teamdetail.setAdapter(projectCostAdapter);
                projectCostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}