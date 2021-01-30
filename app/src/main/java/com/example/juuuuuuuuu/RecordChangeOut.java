package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RecordChangeOut extends AppCompatActivity {
    private ImageView backtohome,remove;
    private EditText changecost,changedate,record_google,other;
    private Spinner changetype,changetypemom,account_mother_money;
    private DatabaseReference databaseReference,a;
    private FirebaseAuth auth;
    private FirebaseUser CurrentUser;
    private ArrayList<String> spinnerDataList;
    private String str,strs,cost,type,typemom,date,count,currency,img;
    private int max=0,Index=0;
    private Double fuckok=0.0;
    private Button changesend;
    private ProgressDialog progressdialog,progressDialog;
    private String[] friend;
    private  String postkey,postimg;
    private TextView show_friend;
    ValueEventListener listener;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(RecordChangeOut.this);
        progressDialog.setMessage("核對資料中");
        progressDialog.show();

        setContentView(R.layout.record_change_out);
        other=findViewById(R.id.other);
        record_google=findViewById(R.id.record_googlemap);
        backtohome=findViewById(R.id.changeback);
        changecost=findViewById(R.id.cost);
        changecost.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        changetype=findViewById(R.id.changetype);
        account_mother_money = findViewById(R.id.account_mother_money);
        currency=(String)account_mother_money.getSelectedItem();
        show_friend=findViewById(R.id.show_friend);
        changetypemom=findViewById(R.id.changetypemom);
        changedate=findViewById(R.id.changedate);
        changesend=findViewById(R.id.changesend);
        remove=findViewById(R.id.remove);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        a = FirebaseDatabase.getInstance().getReference();
        str=(String)changetypemom.getSelectedItem();
        strs=(String)changetype.getSelectedItem();
        progressdialog=new ProgressDialog(RecordChangeOut.this);
        cost=getIntent().getStringExtra("cost");
        type=getIntent().getStringExtra("type");
        typemom=getIntent().getStringExtra("typemom");
        date=getIntent().getStringExtra("date");
        count=getIntent().getStringExtra("count");
        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(RecordChangeOut.this,R.array.accounting_mother_category, R.layout.support_simple_spinner_dropdown_item);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        changetypemom.setAdapter(tagAdapter);

        int spinnerPosition = tagAdapter.getPosition(typemom);
        changetypemom.setSelection(spinnerPosition);
        changedate.setText(date);
        DatabaseReference postref=FirebaseDatabase.getInstance().getReference("Posts");
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    Log.v("id",""+post.getUser());
                    Log.v("cost",""+post.getCost());
                    Log.v("date",""+post.getDate());
                    Log.v("type",""+post.getType());


                    if (post.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())&&post.getCost().equals(cost)&&post.getDate().equals(date)
                            &&post.getType().equals(type)&&post.getTypeMom().equals(typemom)){
                        Log.v(",",""+post.getPostid());
                        postkey=post.getPostid();
                        postimg=post.getCost_pic();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").getValue()!=null){
                    String showf="";
                    int dow=(int)dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").getChildrenCount();
                    for (int aka=1;aka<=dow;aka++){
                        String nn=dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").child(String.valueOf(aka)).getValue().toString();
                        String nname=dataSnapshot.child(nn).child("ris_Username").getValue().toString();
                        if (aka==1){
                            showf=nname;
                        }
                        else{
                            showf=showf+"、"+nname;
                        }

                    }
                    show_friend.setText(showf);
                }
                if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("location").getValue()!=null){
                    record_google.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("location").getValue().toString());
                }
                if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("other").getValue()!=null){
                    other.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("other").getValue().toString());
                }
                if (dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("original_cost").getValue()==null){
                    changecost.setText(cost);
                }else {
                    changecost.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("original_cost").getValue().toString());
                    ArrayAdapter myAdap = (ArrayAdapter) account_mother_money.getAdapter();
                    int spinnerPosition69 = myAdap.getPosition(dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("cost_type").getValue().toString());
                    account_mother_money.setSelection(spinnerPosition69);
                }
                friend=new String[(int)dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").getChildrenCount()];
                for (int gy=1;gy<=dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").getChildrenCount();gy++){
                    friend[gy-1]=dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").child(String.valueOf(gy)).getValue().toString();
                }
                img=dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("img").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
        //母分類點擊事件
        changetypemom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str=(String) changetypemom.getSelectedItem();
                spinnerDataList=new ArrayList<>();
                adapter=new ArrayAdapter<String>(RecordChangeOut.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
                changetype.setAdapter(adapter);
                retrieveData();
                if (str.equals(typemom)){
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (adapter!=null){
                                int spinnerPosition1 = adapter.getPosition(type);
                                changetype.setSelection(spinnerPosition1);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        changetype.setAdapter(type);
//        changetypemom.setAdapter(typemom);

        //子分類點擊事件
        changetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strs=(String) changetype.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        account_mother_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency=(String)account_mother_money.getSelectedItem();
                Index=i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //日期
        changedate.setInputType(InputType.TYPE_NULL);
        changedate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickerDialog();
                }
            }
        });
        changedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
                changedate.setInputType(InputType.TYPE_NULL);
            }
        });
        changesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(changecost.getText())) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RecordChangeOut.this);
                    dialog.setTitle("小提醒");
                    dialog.setMessage("有資料尚未填寫");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });
                    dialog.show();
                }
                else {
                    a.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressdialog.setMessage("更改中，請稍後");
                            progressdialog.show();
                            //PostChange
                            HashMap<String,Object> FriendHashMap=new HashMap<>();
                            FriendHashMap.put("postid",postkey);
                            FriendHashMap.put("user",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            if (changedate.getText().toString().equals(date)){
                                if(currency.equals("新台幣 TWD")){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("cost").setValue(changecost.getText().toString());
                                    FriendHashMap.put("cost",changecost.getText().toString());
                                }else {
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("original_cost").setValue(changecost.getText().toString());
                                    FriendHashMap.put("original_cost",changecost.getText().toString());

                                    fuckok=Double.parseDouble(dataSnapshot.child("Money").child(date).child(String.valueOf(Index)).getValue().toString());
                                    Double fuck1=Double.parseDouble(changecost.getText().toString())*fuckok;
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("cost").setValue(getInt(fuck1));
                                    FriendHashMap.put("cost",String.valueOf(getInt(fuck1)));
                                    FriendHashMap.put("cost_type",currency);

                                }
                                FriendHashMap.put("cost_pic",postimg);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("type").setValue(strs);
                                FriendHashMap.put("date",changedate.getText().toString());
                                FriendHashMap.put("type",strs);
                                FriendHashMap.put("typeMom",str);
                                if (other.getText()!=null){
                                    FriendHashMap.put("other",other.getText().toString());
                                }
                                if (record_google.getText()!=null)
                                    FriendHashMap.put("location",record_google.getText().toString());
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
                                reference.child(postkey).setValue(FriendHashMap);
                                if (friend!=null){
                                    for (int sm=0;sm<friend.length;sm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("friends").child(String.valueOf(sm+1)).setValue(friend[sm]);
                                        reference.child(postkey).child("friends").child(String.valueOf(sm+1)).setValue(friend[sm]);
                                    }
                                }
                                if (record_google.getText()!=null)
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("location").setValue(record_google.getText().toString());
                                if (other.getText()!=null)
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("other").setValue(other.getText().toString());

                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("cost_typeID").setValue(Index);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("cost_type").setValue(currency);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("img").setValue(img);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("type").setValue(strs);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressdialog.dismiss();
                                        Intent intent=new Intent(RecordChangeOut.this,HomePage.class);
                                        startActivity(intent);
                                    }
                                });


                            }
                            else {
                                max=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).getChildrenCount()+1;
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).removeValue();
                                if(currency.equals("新台幣 TWD")){
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("cost").setValue(changecost.getText().toString());
                                    FriendHashMap.put("cost",changecost.getText().toString());
                                }else {
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("original_cost").setValue(changecost.getText().toString());
                                    FriendHashMap.put("original_cost",changecost.getText().toString());
                                    fuckok=Double.parseDouble(dataSnapshot.child("Money").child(changedate.getText().toString()).child(String.valueOf(Index)).getValue().toString());
                                    Double fuck=Double.parseDouble(changecost.getText().toString())*fuckok;
                                    Log.v("d",""+fuck);
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("cost").setValue(getInt(fuck));
                                    FriendHashMap.put("cost",String.valueOf(getInt(fuck)));
                                    FriendHashMap.put("cost_type",currency);
                                }
                                FriendHashMap.put("cost_pic",postimg);
                                FriendHashMap.put("date",changedate.getText().toString());
                                FriendHashMap.put("type",strs);
                                FriendHashMap.put("typeMom",str);
                                if (record_google.getText()!=null){
                                    FriendHashMap.put("location",record_google.getText().toString());
                                }
                                if (other.getText()!=null){
                                    FriendHashMap.put("other",other.getText().toString());
                                }
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
                                reference.child(postkey).setValue(FriendHashMap);
                                if (friend!=null){
                                    for (int sm=0;sm<friend.length;sm++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("friends").child(String.valueOf(sm+1)).setValue(friend[sm]);
                                        reference.child(postkey).child("friends").child(String.valueOf(sm+1)).setValue(friend[sm]);
                                    }
                                }
                                if (record_google.getText()!=null)
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("location").setValue(record_google.getText().toString());
                                if (other.getText()!=null)
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("other").setValue(other.getText().toString());

                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("img").setValue(img);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("cost_typeID").setValue(Index);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("cost_type").setValue(currency);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("type").setValue(strs);
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("name").setValue(dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("ris_Username").getValue().toString());
                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(changedate.getText().toString()).child(Integer.toString(max)).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(Integer.valueOf(databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).getKey())!=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("record").child(date).getChildrenCount()+1){
                                            for(int a=Integer.valueOf(databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).getKey());a<(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("record").child(date).getChildrenCount();a++){
                                                databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf(a)).setValue(dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf(a+1)).getValue());
                                            }
                                            databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf((int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("record").child(date).getChildrenCount())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    new Thread(){
                                                        @Override
                                                        public  void run(){
                                                            try {
                                                                wait(2000);

                                                            } catch (Exception e){e.printStackTrace();}

                                                        }
                                                    }.start();
                                                    progressdialog.dismiss();
                                                    Intent intent=new Intent(RecordChangeOut.this,HomePage.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }

                                    }
                                });





                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }}
        });
        backtohome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RecordChangeOut.this,HomePage.class);
                startActivity(intent);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordChangeOut.this);
                dialog.setTitle("小提醒");
                dialog.setMessage("確定要刪除資料？");
                dialog.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DatabaseReference removeref=FirebaseDatabase.getInstance().getReference("Posts");
                                removeref.child(postkey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(RecordChangeOut.this,"8888",Toast.LENGTH_LONG).show();
                                    }
                                });
                                final int xxx=(int)dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).getChildrenCount();
                                if(xxx!=Integer.valueOf(count)){
                                    for (int s=Integer.valueOf(count);s<xxx;s++){
                                        databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf(s)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf(s+1)).getValue());
                                    }
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(String.valueOf(xxx)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(RecordChangeOut.this,HomePage.class);
                                            startActivity(intent);
                                        }
                                    });
                                }else {
                                    databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(date).child(count).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(RecordChangeOut.this,HomePage.class);
                                            startActivity(intent);
                                        }
                                    });
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }
    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(changedate.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                changedate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }
    public void retrieveData(){
        listener=databaseReference.child(auth.getCurrentUser().getUid()).child("category").child(str).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()){
                    spinnerDataList.add(item.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

}

