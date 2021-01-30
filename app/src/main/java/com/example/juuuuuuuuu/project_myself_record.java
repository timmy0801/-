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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class project_myself_record extends Fragment {
    String project_name,project_id;
    Button create_project_page;
    RecyclerView project_recyclerview_myselfdetail;
    ArrayList<Post> postlist;
    TextView project_single_cost,record_id;
    int year,month,day;
    ProjectCostAdapter projectCostAdapter;
    String count;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.activity_project_myself_record, container, false);
        Bundle bundle=getArguments();
        if (bundle!=null){
            project_name=bundle.getString("project_name");
            project_id=bundle.getString("project_id");
        }
        project_recyclerview_myselfdetail=abc.findViewById(R.id.project_recyclerview_myselfdetail);
//        create_project_page=abc.findViewById(R.id.create_project_page);
        project_single_cost=abc.findViewById(R.id.project_single_cost);

//        create_project_page.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),RecordPage.class);
//                intent.putExtra("project_name",project_name);
//                intent.putExtra("project_id",project_id);
//                startActivity(intent);
//            }
//        });
        project_recyclerview_myselfdetail.setHasFixedSize(true);
        project_recyclerview_myselfdetail.setLayoutManager(new LinearLayoutManager(getContext()));
        postlist=new ArrayList<>();
        projectCostAdapter=new ProjectCostAdapter(getContext(),postlist);
        projectCostAdapter.setOnclicklistener(new ProjectCostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    Intent intent=new Intent(getActivity(),RecordChangeOut.class);
                    intent.putExtra("cost",postlist.get(position).getCost());
                    intent.putExtra("date",postlist.get(position).getDate());
                    intent.putExtra("type",postlist.get(position).getType());
                    Calendar calendar=Calendar.getInstance();
                    year=calendar.get(Calendar.YEAR);
                    month=calendar.get(Calendar.MONTH);
                    day=calendar.get(Calendar.DAY_OF_MONTH);
                    String date1 = year + "/" + (month + 1) + "/" + day;
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
        project_recyclerview_myselfdetail.setAdapter(projectCostAdapter);
        putdata();

    return abc;
    }

    private void putdata() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postlist.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Post hellopost=snapshot.getValue(Post.class);
                        if (!snapshot.hasChild("friends")){
                            if (snapshot.hasChild("project")){
                                if (hellopost.getProject().equals(project_id)){
                                    postlist.add(hellopost);
                                }
                            }
                        }

                }
                gettotal();
                projectCostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gettotal() {
        int total=0;
        for (int i=0;i<postlist.size();i++){
            total+=Integer.parseInt(postlist.get(i).getCost());
            project_single_cost.setText("本專案共花"+Integer.toString(total)+"元");
        }
    }
}
