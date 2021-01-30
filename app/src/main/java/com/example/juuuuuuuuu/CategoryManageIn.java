package com.example.juuuuuuuuu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryManageIn extends Fragment {

    private ImageButton ib1, ib2;
    private RecyclerView work_recyclerView, otherin_recyclerView;
    private ArrayList<Manage> work_list, otherin_list;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ManageAdapter adapter1, adapter2;
    private String work, otherin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.activity_category_manage_in, container, false);
        work_recyclerView = (RecyclerView) abc.findViewById(R.id.work_manage);
        otherin_recyclerView = (RecyclerView) abc.findViewById(R.id.otherin_manage);
        ib1 = abc.findViewById(R.id.categoryin_1);
        ib2 = abc.findViewById(R.id.categoryin_2);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        auth = FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                work_list = new ArrayList<>();
                for (int a = 0; a < dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").getChildrenCount(); a++) {
                    work_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter1();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                otherin_list = new ArrayList<>();
                for (int a = 0; a < dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").getChildrenCount(); a++) {
                    otherin_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter2();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ///////////////////////////////////////////////////////////////工作
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v1 = inflater1.inflate(R.layout.categorydialogin1, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v1)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_work = v1.findViewById(R.id.add_work);
                                        work = add_work.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("工作").getChildrenCount())).setValue(work);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
        ///////////////////////////////////////////////////////////////其他收入
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v2 = inflater1.inflate(R.layout.categorydialogin2, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v2)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_otherin = v2.findViewById(R.id.add_otherin);
                                        otherin = add_otherin.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").getChildrenCount())).setValue(otherin);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
        return abc;
    }
    public void setadapter1(){
        work_recyclerView.setHasFixedSize(true);
        work_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter1 = new ManageAdapter(getActivity(), work_list);
        work_recyclerView.setAdapter(adapter1);
        adapter1.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(work_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作收入").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter1.notifyDataSetChanged();
            }});}
    public void setadapter2(){
        otherin_recyclerView.setHasFixedSize(true);
        otherin_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter2 = new ManageAdapter(getActivity(), otherin_list);
        otherin_recyclerView.setAdapter(adapter2);

        adapter2.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(otherin_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("非工作收入").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter2.notifyDataSetChanged();
            }});}
}

