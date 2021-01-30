package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class report_category_show extends AppCompatActivity {
    private ArrayList<Total> list;
    TotalAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    private ImageButton report_backtohome;
    private TextView nnname;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_category_show);
        report_backtohome=findViewById(R.id.report_backtohome);
        report_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(report_category_show.this,Report_page.class);
                startActivity(intent);
            }
        });
        nnname=findViewById(R.id.nnname);
        auth=FirebaseAuth.getInstance();
        final String month=getIntent().getStringExtra("month");
        final String year=getIntent().getStringExtra("year");
        final String type=getIntent().getStringExtra("type");
        nnname.setText(type);
        list = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("User").child(auth.getCurrentUser().getUid()).child("record").child(year).child(month);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i=31;i>=1;i--){
                    for (int j=(int)dataSnapshot.child(String.valueOf(i)).getChildrenCount();j>=1;j--){
                        if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total("＄"+(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString()),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }
                        else if(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("typeMom").getValue().equals(type)){
                            list.add(new Total(dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("cost").getValue().toString(),dataSnapshot.child(String.valueOf(i)).child(String.valueOf(j)).child("type").getValue().toString(),year+"/"+(month)+"/"+i));
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        setadapter();
    }
    public void setadapter(){
        recyclerView=findViewById(R.id.show);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TotalAdapter(this, list);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }
}
