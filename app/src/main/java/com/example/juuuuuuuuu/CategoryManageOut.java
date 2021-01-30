package com.example.juuuuuuuuu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CategoryManageOut extends Fragment {

    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8,ib9,ib10;
    private RecyclerView food_recyclerView,transport_recyclerView,play_recyclerView,shopping_recyclerView,house_recyclerView,individual_recyclerView,life_recyclerView,hospital_recyclerView,learn_recyclerView,otherout_recyclerView;
    private ArrayList<Manage> food_list,transport_list,play_list,shopping_list,house_list,individual_list,life_list,hospital_list,learn_list,otherout_list;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private ManageAdapter adapter1,adapter2,adapter3,adapter4,adapter5,adapter6,adapter7,adapter8,adapter9,adapter10;
    private String food,transport,play,shopping,house,individual,life,hospital,learn,otherout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.activity_category_manage_out, container, false);
        food_recyclerView=(RecyclerView)abc.findViewById(R.id.food_manage);
        transport_recyclerView=(RecyclerView)abc.findViewById(R.id.transport_manage);
        play_recyclerView=(RecyclerView)abc.findViewById(R.id.play_manage);
        shopping_recyclerView=(RecyclerView)abc.findViewById(R.id.shopping_manage);
        house_recyclerView=(RecyclerView)abc.findViewById(R.id.house_manage);
        individual_recyclerView=(RecyclerView)abc.findViewById(R.id.individual_manage);
        life_recyclerView=(RecyclerView)abc.findViewById(R.id.life_manage);
        hospital_recyclerView=(RecyclerView)abc.findViewById(R.id.hospital_manage);
        learn_recyclerView=(RecyclerView)abc.findViewById(R.id.learn_manage);
        otherout_recyclerView=(RecyclerView)abc.findViewById(R.id.otherout_manage);
        ib1 = abc.findViewById(R.id.category_1);
        ib2 = abc.findViewById(R.id.category_2);
        ib3 = abc.findViewById(R.id.category_3);
        ib4 = abc.findViewById(R.id.category_4);
        ib5 = abc.findViewById(R.id.category_5);
        ib6 = abc.findViewById(R.id.category_6);
        ib7 = abc.findViewById(R.id.category_7);
        ib8 = abc.findViewById(R.id.category_8);
        ib9 = abc.findViewById(R.id.category_9);
        ib10 = abc.findViewById(R.id.category_10);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        auth=FirebaseAuth.getInstance();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                food_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").getChildrenCount();a++){
                    food_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(a)).getValue().toString()));
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
                transport_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("交通").getChildrenCount();a++){
                    transport_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter2();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                play_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").getChildrenCount();a++){
                    play_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter3();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopping_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("購物").getChildrenCount();a++){
                    shopping_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter4();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                house_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("家居").getChildrenCount();a++){
                    house_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter5();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                individual_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("個人").getChildrenCount();a++){
                    individual_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter6();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                life_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").getChildrenCount();a++){
                    life_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter7();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospital_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("醫療").getChildrenCount();a++){
                    hospital_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter8();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                learn_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("學習").getChildrenCount();a++){
                    learn_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter9();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                otherout_list=new ArrayList<>();
                for (int a=0;a<dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").getChildrenCount();a++){
                    otherout_list.add(new Manage(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(a)).getValue().toString()));
                }
                setadapter10();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ///////////////////////////////////////////////////////////////飲食
        ib1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v1 = inflater1.inflate(R.layout.categorydialog1, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v1)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_food=v1.findViewById(R.id.add_food);
                                        food=add_food.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").getChildrenCount())).setValue(food);
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
        } );
        ///////////////////////////////////////////////////////////////交通
        ib2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v2 = inflater1.inflate(R.layout.categorydialog2, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v2)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_transport=v2.findViewById(R.id.add_transport);
                                        transport=add_transport.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("交通").getChildrenCount())).setValue(transport);
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
        } );
        ///////////////////////////////////////////////////////////////娛樂
        ib3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v3 = inflater1.inflate(R.layout.categorydialog3, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v3)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_play=v3.findViewById(R.id.add_play);
                                        play=add_play.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").getChildrenCount())).setValue(play);
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
        } );
        ///////////////////////////////////////////////////////////////購物
        ib4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v4 = inflater1.inflate(R.layout.categorydialog4, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v4)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_shopping=v4.findViewById(R.id.add_shopping);
                                        shopping=add_shopping.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("購物").getChildrenCount())).setValue(shopping);
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
        } );
        ///////////////////////////////////////////////////////////////家居
        ib5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v5 = inflater1.inflate(R.layout.categorydialog5, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v5)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_house=v5.findViewById(R.id.add_house);
                                        house=add_house.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("家居").getChildrenCount())).setValue(house);
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
        } );
        ///////////////////////////////////////////////////////////////個人
        ib6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v6 = inflater1.inflate(R.layout.categorydialog6, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v6)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_individual=v6.findViewById(R.id.add_individual);
                                        individual=add_individual.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("個人").getChildrenCount())).setValue(individual);
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
        } );
        ///////////////////////////////////////////////////////////////生活休閒
        ib7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v7 = inflater1.inflate(R.layout.categorydialog7, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v7)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_life=v7.findViewById(R.id.add_life);
                                        life=add_life.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").getChildrenCount())).setValue(life);
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
        } );
        ///////////////////////////////////////////////////////////////醫療
        ib8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v8 = inflater1.inflate(R.layout.categorydialog8, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v8)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_hospital=v8.findViewById(R.id.add_hospital);
                                        hospital=add_hospital.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("醫療").getChildrenCount())).setValue(hospital);
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
        } );
        ///////////////////////////////////////////////////////////////學習
        ib9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v9 = inflater1.inflate(R.layout.categorydialog9, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v9)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_learn=v9.findViewById(R.id.add_learn);
                                        learn=add_learn.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("學習").getChildrenCount())).setValue(learn);
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
        } );
        ///////////////////////////////////////////////////////////////其他支出
        ib10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(view.getContext());
                final View v10 = inflater1.inflate(R.layout.categorydialog10, null);

                new AlertDialog.Builder(view.getContext())
                        .setView(v10)
                        .setPositiveButton("建立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        EditText add_otherout=v10.findViewById(R.id.add_otherout);
                                        otherout=add_otherout.getText().toString().trim();
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").getChildrenCount())).setValue(otherout);
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
        } );

        return abc;
    }
    public void setadapter1(){
        food_recyclerView.setHasFixedSize(true);
        food_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter1 = new ManageAdapter(getActivity(), food_list);
        food_recyclerView.setAdapter(adapter1);
        adapter1.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(food_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(s)).removeValue();
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
        transport_recyclerView.setHasFixedSize(true);
        transport_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter2 = new ManageAdapter(getActivity(), transport_list);
        transport_recyclerView.setAdapter(adapter2);
        adapter2.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(transport_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("交通").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child(String.valueOf(s)).removeValue();
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
    public void setadapter3(){
        play_recyclerView.setHasFixedSize(true);
        play_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter3 = new ManageAdapter(getActivity(), play_list);
        play_recyclerView.setAdapter(adapter3);
        adapter3.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(play_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter3.notifyDataSetChanged();
            }});}
    public void setadapter4(){
        shopping_recyclerView.setHasFixedSize(true);
        shopping_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter4 = new ManageAdapter(getActivity(), shopping_list);
        shopping_recyclerView.setAdapter(adapter4);
        adapter4.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(shopping_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("購物").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter4.notifyDataSetChanged();
            }});}
    public void setadapter5(){
        house_recyclerView.setHasFixedSize(true);
        house_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter5 = new ManageAdapter(getActivity(), house_list);
        house_recyclerView.setAdapter(adapter5);
        adapter5.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(house_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("家居").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter5.notifyDataSetChanged();
            }});}
    public void setadapter6(){
        individual_recyclerView.setHasFixedSize(true);
        individual_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter6 = new ManageAdapter(getActivity(), individual_list);
        individual_recyclerView.setAdapter(adapter6);
        adapter6.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(individual_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("個人").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter6.notifyDataSetChanged();
            }});}
    public void setadapter7(){
        life_recyclerView.setHasFixedSize(true);
        life_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter7 = new ManageAdapter(getActivity(), life_list);
        life_recyclerView.setAdapter(adapter7);
        adapter7.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(life_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter7.notifyDataSetChanged();
            }});}
    public void setadapter8(){
        hospital_recyclerView.setHasFixedSize(true);
        hospital_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter8 = new ManageAdapter(getActivity(), hospital_list);
        hospital_recyclerView.setAdapter(adapter8);
        adapter8.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(hospital_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("醫療").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter8.notifyDataSetChanged();
            }});}
    public void setadapter9(){
        learn_recyclerView.setHasFixedSize(true);
        learn_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter9 = new ManageAdapter(getActivity(), learn_list);
        learn_recyclerView.setAdapter(adapter9);
        adapter9.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(learn_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("學習").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter9.notifyDataSetChanged();
            }});}
    public void setadapter10(){
        otherout_recyclerView.setHasFixedSize(true);
        otherout_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter10 = new ManageAdapter(getActivity(), otherout_list);
        otherout_recyclerView.setAdapter(adapter10);

        adapter10.setOnClickListener(new ManageAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View a,int position) {


                LayoutInflater inflater1=LayoutInflater.from(a.getContext());
                final View v=inflater1.inflate(R.layout.activity_addfriend_dialog,null);
                EditText hello=v.findViewById(R.id.hello);
                hello.setText(otherout_list.get(position).getCategory());
                new  AlertDialog.Builder(a.getContext()).setTitle("更改類別名稱").setView(v).setPositiveButton("送出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(position)).setValue(hello.getText().toString());

                    }
                }).setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int s=(int) dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").getChildrenCount();
                                if (s==position){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(position)).removeValue();
                                }else {
                                    for (int dowm=position;dowm<s;dowm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(dowm)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child(String.valueOf(dowm+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他支出").child(String.valueOf(s)).removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).show();
                adapter10.notifyDataSetChanged();
            }});}
}

