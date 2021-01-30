package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Invoice extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InlaAdapter adapter;
    private ArrayList<Inla> list;
    private int year,month;
    private TextView show_month;
    private ImageButton next,previous;
    private ImageView invoiceback;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        show_month=findViewById(R.id.showmonth);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        invoiceback=findViewById(R.id.invoiceback);
        list=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        Calendar calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        invoiceback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Invoice.this,HomePage.class);
                startActivity(intent);
            }
        });
        final int a;
        if (month%2==1){
            show_month.setText(year+","+month+"-"+(month+1)+"月");
        }else {
            show_month.setText(year+","+(month-1)+"-"+month+"月");
        }
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int dig=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).getChildrenCount();
                for (int ko=1;ko<=dig;ko++){
                    String cost=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child(String.valueOf(ko)).child("cost").getValue().toString();
                    String num=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child(String.valueOf(ko)).child("ID").getValue().toString();
                    String date=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child(String.valueOf(ko)).child("date").getValue().toString();
                    Log.v("",""+date);
                    String shit=num.replaceAll("\\D","");
                    String[] dick=shit.trim().split("");



                    if (dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).getValue()!=null){
                        String small1=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("small").child("0").getValue().toString();
                        String small2=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("small").child("1").getValue().toString();
                        String small3=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("small").child("2").getValue().toString();

                        String big1=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("big").child("0").getValue().toString();
                        String big2=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("big").child("2").getValue().toString();
                        String big3=dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("big").child("3").getValue().toString();

                        String[] s1=big1.trim().split("");
                        String[] s2=big2.trim().split("");
                        String[] s3=big3.trim().split("");
                        if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("special").child("0").getValue().toString())){
                            //特別
                            list.add(new Inla(cost,num,date,"特別獎 新台幣1,000萬元","specials"));
                        }
                        else if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).child("special").child("1").getValue().toString())){
                            //特獎
                            list.add(new Inla(cost,num,date,"特獎 新台幣200萬元","special"));

                        }
                        else if ((dick[6]+dick[7]+dick[8]).equals(small1) || (dick[6]+dick[7]+dick[8]).equals(small2) || (dick[6]+dick[7]+dick[8]).equals(small3)){
                            //200元
                            list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                        }

                        else if (dick[7].equals(s1[7]) && dick[6].equals(s1[6]) && dick[8].equals(s1[8])){
                            if (dick[5].equals(s1[5])){
                                if (dick[4].equals(s1[4])){
                                    if (dick[3].equals(s1[3])){
                                        if (dick[2].equals(s1[2])){
                                            if (dick[1].equals(s1[1])){
                                                //中八碼
                                                list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                            }else {
                                                //中七碼
                                                list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                            }
                                        }else {
                                            //中六碼
                                            list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                        }
                                    }else {
                                        //中五碼
                                        list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                    }
                                }else {
                                    //中四碼
                                    list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                }
                            }else {
                                //中三碼
                                list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                            }
                        }
                        else if (dick[7].equals(s2[7]) && dick[6].equals(s2[6]) && dick[8].equals(s2[8])){
                            if (dick[5].equals(s2[5])){
                                if (dick[4].equals(s2[4])){
                                    if (dick[3].equals(s2[3])){
                                        if (dick[2].equals(s2[2])){
                                            if (dick[1].equals(s2[1])){
                                                //中八碼
                                                list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                            }else {
                                                //中七碼
                                                list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                            }
                                        }else {
                                            //中六碼
                                            list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                        }
                                    }else {
                                        //中五碼
                                        list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                    }
                                }else {
                                    //中四碼
                                    list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                }
                            }else {
                                //中三碼
                                list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                            }
                        }
                        else if (dick[7].equals(s3[7]) && dick[6].equals(s3[6]) && dick[8].equals(s3[8])){
                            if (dick[5].equals(s3[5])){
                                if (dick[4].equals(s3[4])){
                                    if (dick[3].equals(s3[3])){
                                        if (dick[2].equals(s3[2])){
                                            if (dick[1].equals(s3[1])){
                                                //中八碼
                                                list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                            }else {
                                                //中七碼
                                                list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                            }
                                        }else {
                                            //中六碼
                                            list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                        }
                                    }else {
                                        //中五碼
                                        list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                    }
                                }else {
                                    //中四碼
                                    list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                }
                            }else {
                                //中三碼
                                list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                            }
                        }else {
                            list.add(new Inla(cost,num,date,"未中獎","fail"));
                        }

                    }else {
                        //還沒開講
                        list.add(new Inla(cost,num,date,"尚未開獎","none"));
                    }

                }
                setadapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=new ArrayList<>();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String[] k=show_month.getText().toString().trim().split(",");
                        String[] kk=k[1].trim().split("-");
                        int yy=Integer.valueOf(k[0]);
                        int mm=Integer.valueOf(kk[0]);
                        if (mm==11){
                            yy+=1;
                            mm=1;
                            show_month.setText(yy+","+mm+"-"+(mm+1)+"月");
                        }
                        else {
                            mm+=2;
                            if (mm%2==1){
                                show_month.setText(yy+","+mm+"-"+(mm+1)+"月");
                            }else {
                                show_month.setText(yy+","+(mm-1)+"-"+mm+"月");
                            }
                        }
                        int dig=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).getChildrenCount();
                        for (int ko=1;ko<=dig;ko++){
                            String cost=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("cost").getValue().toString();
                            String num=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("ID").getValue().toString();
                            String date=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("date").getValue().toString();
                            Log.v("",""+date);
                            String shit=num.replaceAll("\\D","");
                            String[] dick=shit.trim().split("");


                            if (dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).getValue()!=null){
                                String small1=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("0").getValue().toString();
                                String small2=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("1").getValue().toString();
                                String small3=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("2").getValue().toString();

                                String big1=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("0").getValue().toString();
                                String big2=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("2").getValue().toString();
                                String big3=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("3").getValue().toString();

                                String[] s1=big1.trim().split("");
                                String[] s2=big2.trim().split("");
                                String[] s3=big3.trim().split("");
                                if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("special").child("0").getValue().toString())){
                                    //特別
                                    list.add(new Inla(cost,num,date,"特別獎 新台幣1,000萬元","specials"));
                                }
                                else if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("special").child("1").getValue().toString())){
                                    //特獎
                                    list.add(new Inla(cost,num,date,"特獎 新台幣200萬元","special"));

                                }
                                else if ((dick[6]+dick[7]+dick[8]).equals(small1) || (dick[6]+dick[7]+dick[8]).equals(small2) || (dick[6]+dick[7]+dick[8]).equals(small3)){
                                    //200元
                                    list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                }

                                else if (dick[7].equals(s1[7]) && dick[6].equals(s1[6]) && dick[8].equals(s1[8])){
                                    if (dick[5].equals(s1[5])){
                                        if (dick[4].equals(s1[4])){
                                            if (dick[3].equals(s1[3])){
                                                if (dick[2].equals(s1[2])){
                                                    if (dick[1].equals(s1[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }
                                }
                                else if (dick[7].equals(s2[7]) && dick[6].equals(s2[6]) && dick[8].equals(s2[8])){
                                    if (dick[5].equals(s2[5])){
                                        if (dick[4].equals(s2[4])){
                                            if (dick[3].equals(s2[3])){
                                                if (dick[2].equals(s2[2])){
                                                    if (dick[1].equals(s2[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }
                                }
                                else if (dick[7].equals(s3[7]) && dick[6].equals(s3[6]) && dick[8].equals(s3[8])){
                                    if (dick[5].equals(s3[5])){
                                        if (dick[4].equals(s3[4])){
                                            if (dick[3].equals(s3[3])){
                                                if (dick[2].equals(s3[2])){
                                                    if (dick[1].equals(s3[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }

                                }else {
                                    list.add(new Inla(cost,num,date,"未中獎","fail"));
                                }

                            }else {
                                //還沒開講
                                list.add(new Inla(cost,num,date,"尚未開獎","none"));
                            }

                        }
                        setadapter();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=new ArrayList<>();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String[] k=show_month.getText().toString().trim().split(",");
                        String[] kk=k[1].trim().split("-");
                        int yy=Integer.valueOf(k[0]);
                        int mm=Integer.valueOf(kk[0]);
                        if (mm==1){
                            yy-=1;
                            mm=11;
                            show_month.setText(yy+","+mm+"-"+(mm+1)+"月");
                        }
                        else {
                            mm-=2;
                            if (mm%2==1){
                                show_month.setText(yy+","+mm+"-"+(mm+1)+"月");
                            }else {
                                show_month.setText(yy+","+(mm-1)+"-"+mm+"月");
                            }
                        }
                        int dig=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).getChildrenCount();
                        for (int ko=1;ko<=dig;ko++){
                            String cost=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("cost").getValue().toString();
                            String num=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("ID").getValue().toString();
                            String date=dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child(String.valueOf(ko)).child("date").getValue().toString();
                            Log.v("",""+date);
                            String shit=num.replaceAll("\\D","");
                            String[] dick=shit.trim().split("");


                            if (dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).getValue()!=null){
                                String small1=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("0").getValue().toString();
                                String small2=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("1").getValue().toString();
                                String small3=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("small").child("2").getValue().toString();

                                String big1=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("0").getValue().toString();
                                String big2=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("2").getValue().toString();
                                String big3=dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("big").child("3").getValue().toString();

                                String[] s1=big1.trim().split("");
                                String[] s2=big2.trim().split("");
                                String[] s3=big3.trim().split("");
                                if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("special").child("0").getValue().toString())){
                                    //特別
                                    list.add(new Inla(cost,num,date,"特別獎 新台幣1,000萬元","specials"));
                                }
                                else if (shit.equals(dataSnapshot.child("invoice").child(String.valueOf(yy)).child(String.valueOf((mm+1)/2)).child("special").child("1").getValue().toString())){
                                    //特獎
                                    list.add(new Inla(cost,num,date,"特獎 新台幣200萬元","special"));

                                }
                                else if ((dick[6]+dick[7]+dick[8]).equals(small1) || (dick[6]+dick[7]+dick[8]).equals(small2) || (dick[6]+dick[7]+dick[8]).equals(small3)){
                                    //200元
                                    list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                }

                                else if (dick[7].equals(s1[7]) && dick[6].equals(s1[6]) && dick[8].equals(s1[8])){
                                    if (dick[5].equals(s1[5])){
                                        if (dick[4].equals(s1[4])){
                                            if (dick[3].equals(s1[3])){
                                                if (dick[2].equals(s1[2])){
                                                    if (dick[1].equals(s1[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }
                                }
                                else if (dick[7].equals(s2[7]) && dick[6].equals(s2[6]) && dick[8].equals(s2[8])){
                                    if (dick[5].equals(s2[5])){
                                        if (dick[4].equals(s2[4])){
                                            if (dick[3].equals(s2[3])){
                                                if (dick[2].equals(s2[2])){
                                                    if (dick[1].equals(s2[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }
                                }
                                else if (dick[7].equals(s3[7]) && dick[6].equals(s3[6]) && dick[8].equals(s3[8])){
                                    if (dick[5].equals(s3[5])){
                                        if (dick[4].equals(s3[4])){
                                            if (dick[3].equals(s3[3])){
                                                if (dick[2].equals(s3[2])){
                                                    if (dick[1].equals(s3[1])){
                                                        //中八碼
                                                        list.add(new Inla(cost,num,date,"頭獎 新台幣200,000元","head"));
                                                    }else {
                                                        //中七碼
                                                        list.add(new Inla(cost,num,date,"二獎 新台幣40,000元","two"));
                                                    }
                                                }else {
                                                    //中六碼
                                                    list.add(new Inla(cost,num,date,"三獎 新台幣10,000元","three"));
                                                }
                                            }else {
                                                //中五碼
                                                list.add(new Inla(cost,num,date,"四獎 新台幣4,000元","four"));
                                            }
                                        }else {
                                            //中四碼
                                            list.add(new Inla(cost,num,date,"五獎 新台幣1,000元","five"));
                                        }
                                    }else {
                                        //中三碼
                                        list.add(new Inla(cost,num,date,"六獎 新台幣200元","six"));
                                    }

                                }else {
                                    list.add(new Inla(cost,num,date,"未中獎","fail"));
                                }

                            }else {
                                //還沒開講
                                list.add(new Inla(cost,num,date,"尚未開獎","none"));
                            }

                        }
                        setadapter();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
    public void setadapter(){
        recyclerView=findViewById(R.id.data_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InlaAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
