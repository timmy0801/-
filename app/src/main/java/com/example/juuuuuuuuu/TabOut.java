package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juuuuuuuuu.Notigications.APIService;
import com.example.juuuuuuuuu.Notigications.Client;
import com.example.juuuuuuuuu.Notigications.Data;
import com.example.juuuuuuuuu.Notigications.MyResponse;
import com.example.juuuuuuuuu.Notigications.Sender;
import com.example.juuuuuuuuu.Notigications.Token;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TabOut extends Fragment {
    Button btnsendd;
    EditText cost, record_date,  record_googlemap, other;
    Spinner account_mother_money,account_mother_test, account_mother_category,record_team;
    DatabaseReference databaseReference,FriendRef,postref;
    int boo;
    FirebaseAuth auth;
    User user;
    private StorageReference storageReference;
    String str,strs,name,Fname,selfname,img,WebURL,UrlData,currency;
    int maxId=0,year,day,month,Index,scancost;
    Double fine;
    ArrayAdapter<String> adapter,friendadapter;
    ArrayList<String> spinnerDataList,friendlist,idlist,arrayList,hey;
    ValueEventListener listener;
    ChipGroup chipgroup;
    List<String> money;
    Handler handler;
    Uri imageUri;
    Bitmap bitmap;
    APIService apiService;
    ImageView camera;
    long postcount;
    ImageView tabout_scan;
    String url,click_date_hello;
    int currency_policy;
    String project_name,project_id,web;
    private static final int Image_Capture_Code = 1;
    private Boolean lil=false,oi=false,flag1=true;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View abc = inflater.inflate(R.layout.activity_tab_out, container, false);
        abc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });
        url="https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media";
        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        progressDialog=new ProgressDialog(getActivity());
        Calendar calendar = Calendar.getInstance();
        btnsendd = (Button) abc.findViewById(R.id.btnSendd);
        cost = (EditText) abc.findViewById(R.id.cost);
        cost.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        record_date = (EditText) abc.findViewById(R.id.record_date);
        account_mother_test =  (Spinner) abc.findViewById(R.id.account_mother_test);
        account_mother_category = (Spinner) abc.findViewById(R.id.account_mother_category);
        record_team =  (Spinner) abc.findViewById(R.id.record_team);
        account_mother_money = (Spinner) abc.findViewById(R.id.account_mother_money);
        record_googlemap = (EditText) abc.findViewById(R.id.record_googlemap);
        other = (EditText) abc.findViewById(R.id.other);
        str=(String)account_mother_test.getSelectedItem();
        strs=(String)account_mother_category.getSelectedItem();
        FriendRef=FirebaseDatabase.getInstance().getReference("Friends");
        chipgroup=abc.findViewById(R.id.chipgroup);
        arrayList = new ArrayList<>();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        money=new ArrayList<String>();
        currency=(String)account_mother_money.getSelectedItem();
        camera=abc.findViewById(R.id.camera);
        storageReference= FirebaseStorage.getInstance().getReference(String.valueOf(System.currentTimeMillis())+"uploads");
        tabout_scan=abc.findViewById(R.id.tabout_scan);
        Bundle bundle=getArguments();
        if (bundle!=null){
            project_name=bundle.getString("project_name");
            project_id=bundle.getString("project_id");
            click_date_hello=bundle.getString("click_date");
        }
        if (click_date_hello!=null){
            record_date.setText(click_date_hello);
        }else {
            record_date.setText(year+"/"+(month+1)+"/"+day);
        }
        ///////////////////相機bug de
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new  String[]{Manifest.permission.CAMERA},0);
        }
        account_mother_test.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str=(String) account_mother_test.getSelectedItem();
                spinnerDataList=new ArrayList<>();
                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
                account_mother_category.setAdapter(adapter);
                retrieveData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        account_mother_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strs=(String) account_mother_category.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(abc.getContext(),R.array.accounting_mother_category, R.layout.support_simple_spinner_dropdown_item);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        account_mother_test.setAdapter(tagAdapter);

        record_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fname=(String)record_team.getSelectedItem();
                int k=0;
                if (!Fname.equals("分帳好友Tag")){
                    final Chip entryChip = getChip(getView(),chipgroup, Fname);
                    chipgroup.addView(entryChip);
                    arrayList.add(k,Fname);
                    k=k+1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getFriendID();

        record_date.setInputType(InputType.TYPE_NULL);
        record_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickerDialog();
                }
            }
        });
        record_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
                record_date.setInputType(InputType.TYPE_NULL);
            }
        });
        //getnum();
        //錢錢
        getnum();
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
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        btnsendd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(cost.getText())) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("小提醒");
                    dialog.setMessage("有資料尚未填寫");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });
                    dialog.show();
                }
                else {

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            hi();


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }}
        });
        tabout_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator =IntentIntegrator.forSupportFragment(TabOut.this);
                scanIntegrator.setPrompt("請掃描發票左側QRCODE");
                scanIntegrator.setTimeout(300000);
                scanIntegrator.setBeepEnabled(true);
                scanIntegrator.initiateScan();

            }
        });
        DatabaseReference postreference=FirebaseDatabase.getInstance().getReference("Posts");
        postreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    postcount=dataSnapshot.getChildrenCount();
                }else{
                    postcount=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return abc;
    }



    private void getnum() {
        DatabaseReference Mref=FirebaseDatabase.getInstance().getReference("CurrencyPolicy");
        Mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    currency_policy=Integer.parseInt(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString());
                    account_mother_money.setSelection(currency_policy);
//                    Toast.makeText(getContext(),Integer.toString(currency_policy),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(record_date.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                record_date.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
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
    public void getFriendID(){
        FriendRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())){
                    DatabaseReference myfriend=FirebaseDatabase.getInstance().getReference("Friends").child(auth.getCurrentUser().getUid());
                    myfriend.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            idlist=new ArrayList<>();
                            String temp=dataSnapshot.getValue().toString();
                            String[] test = temp.split(",");
                            for(int k = 0; k<test.length; k++){
                                String id = test[k].split("=")[0].replaceAll("\\p{Punct}", "");
                                id = id.trim();
                                idlist.add(id);
                            }
                            getfriendname(idlist);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getfriendname(ArrayList<String> list){
        friendlist=new ArrayList<>();
        friendlist.add("分帳好友Tag");
        for (int j=0;j<list.size();j++){
            DatabaseReference nameref=FirebaseDatabase.getInstance().getReference("User").child(list.get(j));
            nameref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name=dataSnapshot.child("ris_Username").getValue().toString();
                    friendlist.add(name);
                    friendadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,friendlist);
                    record_team.setAdapter(friendadapter);
                    friendadapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    private Chip getChip(View view, final ChipGroup entryChipGroup, final String text) {
        final Chip chip = new Chip(view.getContext());
        chip.setChipDrawable(ChipDrawable.createFromResource(getActivity(), R.xml.my_chip));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(text);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryChipGroup.removeView(chip);
                for (int n=0;n<arrayList.size();n++){
                    if (arrayList.get(n).equals(text)){
                        arrayList.remove(n);
                        n--;
                        break;
                    }
                }
            }
        });
        return chip;
    }
    public  void hi(){
        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("User").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                selfname=dataSnapshot.child("ris_Username").getValue().toString();
                if (dataSnapshot.hasChild("imageurl")){
                    img=dataSnapshot.child("imageurl").getValue().toString();
                }else {
                    img="https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/12231.jpg?alt=media";
                }
                senddb();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void senddb(){


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                hey=new ArrayList<>();
                maxId = (int) (dataSnapshot.child(auth.getCurrentUser().getUid()).child("record").child(record_date.getText().toString()).getChildrenCount());
                DatabaseReference abc=databaseReference.child(auth.getCurrentUser().getUid()).child("record").child(record_date.getText().toString());
                int fcount=arrayList.size();
                DatabaseReference okfine=FirebaseDatabase.getInstance().getReference("Money");
                okfine.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (Index!=0){
                            final String[] gg=record_date.getText().toString().split("/");
                            if (Integer.valueOf(gg[0])>year || Integer.valueOf(gg[1])>(month+1) || Integer.valueOf(gg[2])>day){
                                fine=Double.valueOf(dataSnapshot.child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(day)).child(String.valueOf(Index)).getValue().toString());
                                lil=true;
                            }
                            else {
                                fine=Double.valueOf(dataSnapshot.child(record_date.getText().toString()).child(String.valueOf(Index)).getValue().toString());
                            }
                        }
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (arrayList.size()>0){
                                    AlertDialog.Builder builder1=new AlertDialog.Builder(getActivity());
                                    builder1.setTitle("是否要進行分帳");
                                    builder1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder builder2=new AlertDialog.Builder(getActivity());
                                            builder2.setTitle("輸入自身記帳金額");
                                            final EditText editText = new EditText(getActivity());
                                            editText.setText(Integer.toString(Integer.parseInt(cost.getText().toString())/(arrayList.size()+1)));
                                            builder2.setView(editText);
                                            builder2.setPositiveButton("完成",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (Double.parseDouble(editText.getText().toString()) >= Double.parseDouble(cost.getText().toString())) {
                                                        AlertDialog.Builder dialog3 = new AlertDialog.Builder(getActivity());
                                                        dialog3.setTitle("小提醒");
                                                        dialog3.setMessage("分帳金額大於等於原本記帳金額");
                                                        dialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                            }
                                                        });
                                                        dialog3.show();
                                                    } else {
                                                        int friendmoney = (Integer.valueOf(cost.getText().toString()) - Integer.valueOf(editText.getText().toString()) / arrayList.size());
                                                        if (currency.equals("新台幣 TWD")) {
                                                            abc.child(Integer.toString(maxId + 1)).child("cost").setValue(editText.getText().toString());
                                                        } else {
                                                            abc.child(Integer.toString(maxId + 1)).child("cost").setValue(getInt((Double.parseDouble(editText.getText().toString()) * fine) / arrayList.size()));
                                                            abc.child(Integer.toString(maxId + 1)).child("original_cost").setValue(editText.getText().toString());
                                                            abc.child(Integer.toString(maxId + 1)).child("cost_typeID").setValue(Index);
                                                            abc.child(Integer.toString(maxId + 1)).child("cost_type").setValue(currency);
                                                        }
                                                        if (lil==true){Toast.makeText(getActivity(), "匯率設置為今日之匯率", Toast.LENGTH_LONG).show();}
                                                        if (record_googlemap.getText().toString()!=null) {
                                                            abc.child(Integer.toString(maxId + 1)).child("location").setValue(record_googlemap.getText().toString());
                                                        }
                                                        abc.child(Integer.toString(maxId + 1)).child("type").setValue(strs);
                                                        abc.child(Integer.toString(maxId + 1)).child("typeMom").setValue(str);
                                                        abc.child(Integer.toString(maxId + 1)).child("img").setValue(img);
                                                        abc.child(Integer.toString(maxId + 1)).child("name").setValue(selfname);
                                                        if (!TextUtils.isEmpty(other.getText())){
                                                            abc.child(Integer.toString(maxId+1)).child("other").setValue(other.getText().toString());
                                                        }

                                                        for (int q = 0; q < arrayList.size(); q++) {
                                                            for (DataSnapshot a : dataSnapshot.getChildren()) {
                                                                if (a.child("ris_Username").getValue().toString().equals(arrayList.get(q))) {
                                                                    hey.add(a.getKey());
                                                                }
                                                            }
                                                            abc.child(Integer.toString(maxId + 1)).child("friends").child(Integer.toString(q + 1)).setValue(hey.get(q));
                                                        }
                                                        //NEW POST
                                                        postref = FirebaseDatabase.getInstance().getReference("Posts");
                                                        String postid = postref.push().getKey();
                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                        hashMap.put("postid", postid);
                                                        if (currency.equals("新台幣 TWD")) {
                                                            hashMap.put("cost", editText.getText().toString());
                                                        } else {
                                                            boo = getInt(Double.parseDouble(editText.getText().toString()) * fine) / arrayList.size();
                                                            hashMap.put("cost", String.valueOf(getInt((Double.parseDouble(editText.getText().toString()) * fine) / arrayList.size())));
                                                            hashMap.put("original_cost", editText.getText().toString());
                                                            hashMap.put("cost_type", currency);
                                                        }
                                                        if (!url.equals("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media")) {
                                                            hashMap.put("cost_pic", url);
                                                        } else {
                                                            hashMap.put("cost_pic", url);
                                                        }
                                                        if (project_id != null) {
                                                            hashMap.put("project", project_id);
                                                        }
                                                        if (!TextUtils.isEmpty(other.getText())){
                                                            hashMap.put("other",other.getText().toString());
                                                        }
                                                        hashMap.put("type", strs);
                                                        hashMap.put("typeMom", str);
                                                        hashMap.put("count",postcount);
                                                        hashMap.put("user", auth.getCurrentUser().getUid());
                                                        hashMap.put("date", record_date.getText().toString());
                                                        if (record_googlemap.getText().toString()!=null) {
                                                            hashMap.put("location",record_googlemap.getText().toString());
                                                        }
                                                        postref.child(postid).setValue(hashMap);
                                                        for (int q = 0; q < arrayList.size(); q++) {
                                                            int z = (q + 1);
                                                            postref.child(postid).child("friends").child(Integer.toString(z)).setValue(hey.get(q));
                                                        }


                                                        for (int q = 0; q < arrayList.size(); q++) {
//                                        HashMap<String,Object> FriendHashMap=new HashMap<>();
//                                        String Newpostid=postref.push().getKey();
//                                        FriendHashMap.put("postid",Newpostid);
                                                            int z = (q + 1);
                                                            int c = (int) dataSnapshot.child(hey.get(q)).child("record").child(record_date.getText().toString()).getChildrenCount();
                                                            int ass = (int) dataSnapshot.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).getChildrenCount();
                                                            DatabaseReference dd = FirebaseDatabase.getInstance().getReference("User").child(hey.get(q));
                                                            dd.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    sendNotification(dataSnapshot.getKey(), FirebaseAuth.getInstance().getCurrentUser().getUid(), "");

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });


                                                            abc.child(Integer.toString(maxId + 1)).child("friends").child(Integer.toString(z)).setValue(hey.get(q));
//                                        postref.child(Newpostid).child("friends").child(Integer.toString(z)).setValue(hey.get(q));
                                                            if (currency.equals("新台幣 TWD")) {
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("cost").setValue(Integer.valueOf(cost.getText().toString())-Integer.valueOf(editText.getText().toString()));
//                                            FriendHashMap.put("cost",Integer.toString(friendmoney));
                                                            } else {
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("cost").setValue(getInt(((Double.parseDouble(cost.getText().toString())-Double.parseDouble(editText.getText().toString())) * fine) / arrayList.size()));
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("orginal_cost").setValue(Integer.valueOf(cost.getText().toString())-Integer.valueOf(editText.getText().toString()));
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("cost_typeID").setValue(Index);
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("cost_type").setValue(currency);
                                                                boo = getInt((Double.parseDouble(cost.getText().toString()) - Double.parseDouble(editText.getText().toString())) * fine) / arrayList.size();
//                                            FriendHashMap.put("cost",Integer.toString(boo));
//                                            FriendHashMap.put("original_cost",Integer.toString(friendmoney));
//                                            FriendHashMap.put("cost_type",currency);
                                                            }
                                                            if (record_googlemap.getText()!=null) {

                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("location").setValue(record_googlemap.getText().toString());
                                                            }
                                                            databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("notibyID").setValue(auth.getCurrentUser().getUid());

                                                            databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("notiby").setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("ris_Username").getValue().toString());
                                                            databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("type").setValue(strs);
//                                        FriendHashMap.put("type",strs);
//                                        FriendHashMap.put("typeMom",str);
                                                            if (!url.equals("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media")) {
//                                            FriendHashMap.put("cost_pic",url);
                                                            } else {
//                                            FriendHashMap.put("cost_pic",url);
                                                            }
//                                        FriendHashMap.put("date",record_date.getText().toString());
//                                        FriendHashMap.put("user",hey.get(q));
                                                            databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("img").setValue(img);
                                                            databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("name").setValue(arrayList.get(q));
//                                        postref.child(Newpostid).setValue(FriendHashMap);
                                                            for (int s = 0; s < arrayList.size(); s++) {
                                                                if (hey.get(s) == hey.get(q)) {
                                                                    databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("friends").child(String.valueOf(s + 1)).setValue(auth.getCurrentUser().getUid());
//                                                postref.child(Newpostid).child("friends").child(Integer.toString(s+1)).setValue(auth.getCurrentUser().getUid());
                                                                } else {
                                                                    databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("friends").child(String.valueOf(s + 1)).setValue(hey.get(s));
//                                                postref.child(Newpostid).child("friends").child(Integer.toString(s+1)).setValue(hey.get(s));
                                                                }
                                                            }
                                                            if (!url.equals("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media")) {
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("cost_pic").setValue(url);
                                                            }


                                                            if (q==arrayList.size()-1){
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Intent intent = new Intent(getActivity(), HomePage.class);
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                            }else {
                                                                databaseReference.child(hey.get(q)).child("notifyrecord").child(record_date.getText().toString()).child(String.valueOf(ass + 1)).child("typeMom").setValue(str);
                                                            }
                                                        }

                                                    }
                                                }});
                                            AlertDialog dialog11=builder2.create();
                                            dialog11.show();

                                        }
                                    });
                                    builder1.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressDialog.setMessage("記帳中，請稍候");
                                            progressDialog.show();
                                            //NEW POST
                                            DatabaseReference single=FirebaseDatabase.getInstance().getReference("Posts");
                                            String postid2=single.push().getKey();
                                            HashMap<String,Object> singlehashMap=new HashMap<>();
                                            singlehashMap.put("postid",postid2);
                                            if (currency.equals("新台幣 TWD")){
                                                abc.child(Integer.toString(maxId+1)).child("cost").setValue(cost.getText().toString());
                                                singlehashMap.put("cost",cost.getText().toString());
                                            }else {
                                                abc.child(Integer.toString(maxId+1)).child("cost").setValue(getInt(Double.parseDouble(cost.getText().toString())*fine));
                                                abc.child(Integer.toString(maxId+1)).child("original_cost").setValue(cost.getText().toString());
                                                abc.child(Integer.toString(maxId+1)).child("cost_typeID").setValue(Index);
                                                abc.child(Integer.toString(maxId+1)).child("cost_type").setValue(currency);
                                                singlehashMap.put("cost",String.valueOf(getInt(Double.parseDouble(cost.getText().toString())*fine)));
                                                singlehashMap.put("original_cost",cost.getText().toString());
                                                singlehashMap.put("cost_type",currency);
                                            }
                                            if (lil==true){Toast.makeText(getActivity(), "匯率設置為今日之匯率", Toast.LENGTH_LONG).show();}

                                            if (project_id!=null){
                                                singlehashMap.put("project",project_id);
                                            }
                                            if (!TextUtils.isEmpty(other.getText())){
                                                abc.child(Integer.toString(maxId+1)).child("other").setValue(other.getText().toString());
                                            }
                                            if (!TextUtils.isEmpty(other.getText())){
                                                singlehashMap.put("other",other.getText().toString());
                                            }
                                            if (record_googlemap.getText()!=null) {
                                                singlehashMap.put("location",record_googlemap.getText().toString());
                                            }
                                            singlehashMap.put("type",strs);
                                            singlehashMap.put("typeMom",str);
                                            singlehashMap.put("count",postcount);
                                            singlehashMap.put("user",auth.getCurrentUser().getUid());
                                            singlehashMap.put("date",record_date.getText().toString());
                                            abc.child(Integer.toString(maxId+1)).child("type").setValue(strs);

                                            if (!url.equals("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media")){
                                                singlehashMap.put("cost_pic",url);
                                            }else {
                                                singlehashMap.put("cost_pic",url);
                                            }
                                            for (int q=0;q<arrayList.size();q++){
                                                for (DataSnapshot a : dataSnapshot.getChildren()) {
                                                    if(a.child("ris_Username").getValue().toString().equals(arrayList.get(q))){
                                                        hey.add(a.getKey());
                                                    }
                                                }
                                                abc.child(Integer.toString(maxId+1)).child("friends").child(Integer.toString(q+1)).setValue(hey.get(q));
                                            }
                                            single.child(postid2).setValue(singlehashMap);
                                            for (int q=0;q<arrayList.size();q++){
                                                int z=(q+1);
                                                single.child(postid2).child("friends").child(Integer.toString(z)).setValue(hey.get(q));
                                            }
                                            abc.child(Integer.toString(maxId+1)).child("img").setValue(img);
                                            if (record_googlemap.getText()!=null){
                                                abc.child(Integer.toString(maxId+1)).child("location").setValue(record_googlemap.getText().toString());

                                            }
                                            abc.child(Integer.toString(maxId+1)).child("name").setValue(selfname);
                                            abc.child(Integer.toString(maxId+1)).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(getActivity(), HomePage.class);
                                                    startActivity(intent);
                                                }
                                            });

                                            //
                                        }
                                    });
                                    AlertDialog dialog=builder1.create();
                                    dialog.show();

                                }
                                else {
                                    //NEW POST
                                    DatabaseReference single=FirebaseDatabase.getInstance().getReference("Posts");
                                    String postid2=single.push().getKey();
                                    HashMap<String,Object> singlehashMap=new HashMap<>();
                                    singlehashMap.put("postid",postid2);
                                    if (!url.equals("https://firebasestorage.googleapis.com/v0/b/juuuuuuuuu-v1.appspot.com/o/XkESSmFng0XGURnWi8acItjsX573uploads?alt=media")){
                                        abc.child(Integer.toString(maxId+1)).child("cost_pic").setValue(url);
                                        singlehashMap.put("cost_pic",url);
                                    }
                                    else {
                                        singlehashMap.put("cost_pic",url);
                                    }
                                    if (currency.equals("新台幣 TWD")){
                                        abc.child(Integer.toString(maxId+1)).child("cost").setValue(cost.getText().toString());
                                        singlehashMap.put("cost",cost.getText().toString());
                                    }else {
                                        abc.child(Integer.toString(maxId+1)).child("cost").setValue(getInt(Double.parseDouble(cost.getText().toString())*fine));
                                        abc.child(Integer.toString(maxId+1)).child("original_cost").setValue(cost.getText().toString());
                                        abc.child(Integer.toString(maxId+1)).child("cost_typeID").setValue(Index);
                                        abc.child(Integer.toString(maxId+1)).child("cost_type").setValue(currency);
                                        singlehashMap.put("cost",String.valueOf(getInt(Double.parseDouble(cost.getText().toString())*fine)));
                                        singlehashMap.put("original_cost",cost.getText().toString());
                                        singlehashMap.put("cost_type",currency);
                                    }
                                    if (!TextUtils.isEmpty(other.getText())){
                                        abc.child(Integer.toString(maxId+1)).child("other").setValue(other.getText().toString());
                                        singlehashMap.put("other",other.getText().toString());
                                    }
                                    if (lil==true){Toast.makeText(getActivity(), "匯率設置為今日之匯率", Toast.LENGTH_LONG).show();}

                                    abc.child(Integer.toString(maxId+1)).child("img").setValue(img);
                                    singlehashMap.put("type",strs);
                                    singlehashMap.put("typeMom",str);
                                    singlehashMap.put("count",postcount);
                                    singlehashMap.put("user",auth.getCurrentUser().getUid());
                                    singlehashMap.put("date",record_date.getText().toString());
                                    if (project_id!=null){
                                        singlehashMap.put("project",project_id);
                                    }
                                    if (record_googlemap.getText()!=null) {
                                        singlehashMap.put("location",record_googlemap.getText().toString());
                                    }
                                    single.child(postid2).setValue(singlehashMap);
                                    abc.child(Integer.toString(maxId+1)).child("type").setValue(strs);
                                    abc.child(Integer.toString(maxId+1)).child("name").setValue(selfname);
                                    if (record_googlemap.getText()!=null){
                                        abc.child(Integer.toString(maxId+1)).child("location").setValue(record_googlemap.getText().toString());
                                    }

                                    abc.child(Integer.toString(maxId+1)).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();

                                            Intent intent=new Intent(getActivity(),HomePage.class);
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
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//
//                progressDialog.dismiss();
//                //
//                Intent intent=new Intent(getActivity(),HomePage.class);
//                startActivity(intent);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

        StrictMode.setVmPolicy(builder.build());

        builder.detectFileUriExposure();

        Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),"file"+String.valueOf(System.currentTimeMillis())+".jpg");
        imageUri = Uri.fromFile(file);
        camera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        camera.putExtra("return_data",true);
        startActivityForResult(camera, 0);
    }
    //    private void openCamera() {
//
//        Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(Environment.getExternalStorageDirectory(),"file"+String.valueOf(System.currentTimeMillis())+".jpg");
//        imageUri = Uri.fromFile(file);
//        camera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//        camera.putExtra("return_data",true);
//        startActivityForResult(camera, 0);
//
//    }
    //開啟相簿
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(gallery,"Select Image From Gallery"),2);
    }

    //取得相片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
//            if (requestCode==Image_Capture_Code){
//                if (requestCode==RESULT_OK){
//                    cropImage();
//                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
//                }
//            }else if (requestCode==RESULT_CANCELED){
//                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
//            }

        if(resultCode == RESULT_OK && requestCode == 0) {
            cropImage();
        }else if(requestCode == 2){
            if(data != null){
                imageUri = data.getData();
                cropImage();
            }
        }else if(requestCode == 1){
            if(data != null){
                Bundle bundle = data.getExtras();
                bitmap= bundle.getParcelable("data");
                // camera.setImageBitmap(bitmap);
                camera.setDrawingCacheEnabled(true);
                camera.buildDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] hello = baos.toByteArray();
                UploadTask uploadTask = storageReference.putBytes(hello);
                // UploadTask uploadTask=storageReference.putFile(uri);
                Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            Toast.makeText(getActivity(), "上傳失敗", Toast.LENGTH_LONG).show();
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            url=task.getResult().toString().substring(0,task.getResult().toString().indexOf("&token"));
                            Toast.makeText(getActivity(), "上傳成功", Toast.LENGTH_LONG).show();
                            Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().into(camera);
                        }
                    }
                });

            }else{ }
        }else if (requestCode==49374){
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null)
            {
                if(scanningResult.getContents() != null)
                {
                    String scanContent = scanningResult.getContents();
                    if (!scanContent.equals("")&&scanContent.length()>8)
                    {
                        String invoice=scanContent.substring(0,10);
                        String date=scanContent.substring(10,17);
                        String year=date.substring(0,3);
                        year=String.valueOf(Integer.parseInt(year)+1911);
                        final String year1=year;
                        String mouth=date.substring(3,5);
                        String day=date.substring(5);
                        String DATE=year+"/"+mouth+"/"+day;
                        String ab=scanContent.substring(29,37);
                        web="https://opengovtw.com/ban/"+scanContent.substring(45,53);
                        Log.v("d",""+web);
                        final int D2 = Integer.parseInt(ab,16);

                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                try {

                                    abcd(getURLdata1());

                                } catch (Exception e){e.printStackTrace();}
                            }
                        }.start();

                        DatabaseReference ddddd=FirebaseDatabase.getInstance().getReference("User");
                        record_date.setText(DATE);
                        cost.setText(String.valueOf(D2));
                        account_mother_money.setSelection(0);

                        ddddd.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int h=(int)dataSnapshot.child(auth.getCurrentUser().getUid()).child("invoice").child(year1).child(String.valueOf((Integer.valueOf(mouth)+1)/2)).getChildrenCount()+1;
                                for (int cm=1;cm<h;cm++){
                                    final String jk=dataSnapshot.child(auth.getCurrentUser().getUid()).child("invoice").child(year1).child(String.valueOf((Integer.valueOf(mouth)+1)/2)).child(String.valueOf(cm)).child("ID").getValue().toString();
                                    if (jk.equals(invoice.trim())){
                                        oi=true;
                                    }
                                }
                                if (oi==false){
                                    ddddd.child(auth.getCurrentUser().getUid()).child("invoice").child(year1).child(String.valueOf((Integer.valueOf(mouth)+1)/2)).child(String.valueOf(h)).child("ID").setValue(invoice.trim());
                                    ddddd.child(auth.getCurrentUser().getUid()).child("invoice").child(year1).child(String.valueOf((Integer.valueOf(mouth)+1)/2)).child(String.valueOf(h)).child("cost").setValue(D2);
                                    ddddd.child(auth.getCurrentUser().getUid()).child("invoice").child(year1).child(String.valueOf((Integer.valueOf(mouth)+1)/2)).child(String.valueOf(h)).child("date").setValue(DATE);
                                    Toast.makeText(getContext(),"已將儲存發票", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getContext(),"已儲存過發票", Toast.LENGTH_LONG).show();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        if (Integer.parseInt(moneyadd[0])>2){
//                            record_date.setText(DATE);
//                            Toast.makeText(getContext(),"抱歉，現在無法提供品項過多的輸入", Toast.LENGTH_LONG).show();
//
//                        }else {
//                            if (Integer.parseInt(moneyadd[0])==2) {
//                                for (int k = 3; k <= moneyadd.length - 4; k += 3) {
//                                    double count = Double.valueOf(moneyadd[k + 1]);
//                                    double singlecost = Double.valueOf(moneyadd[k + 2]);
//                                    scancost += count * singlecost;
//                                    cost.setText(Integer.toString(scancost));
//                                    record_date.setText(DATE);
//                                }
//                            }else {
//                                for (int k = 3; k <= moneyadd.length - 3; k += 3) {
//                                    double count = Double.valueOf(moneyadd[k + 1]);
//                                    double singlecost = Double.valueOf(moneyadd[k + 2]);
//                                    scancost +=  count * singlecost;
//                                    cost.setText(Integer.toString(scancost));
//                                    record_date.setText(DATE);
//                                }
//                            }
//                            scancost=0;
//                        }
//                        Toast.makeText(getContext(),"掃描內容: "+scanContent.toString(), Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getContext(),"資料不符", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else
            {
                super.onActivityResult(requestCode, resultCode, data);
                Toast.makeText(getContext(),"發生錯誤",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getContext(),"發生錯誤",Toast.LENGTH_LONG).show();
        }
    }
    public void cropImage(){
        try{
            Intent CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(imageUri,"image/*");
            CropIntent.putExtra("crop", "true");
//            CropIntent.putExtra("outputX", 400);
//            CropIntent.putExtra("outputY", 400);
//            CropIntent.putExtra("aspectX", 4);
//            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            startActivityForResult(CropIntent,1);
        }catch (ActivityNotFoundException ex){

        }
    }


    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }
    private void sendNotification(String reciever,String username,String message){
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        DatabaseReference m=FirebaseDatabase.getInstance().getReference("User");
        Query query=tokens.orderByKey().equalTo(reciever);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Token token=dataSnapshot1.getValue(Token.class);

                    Data data=new Data(username,R.mipmap.ic_launcher,"","您有新的記帳通知",reciever);
                    Sender sender=new Sender(data,token.getToken());
                    apiService.sendNotifacation(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

                            if(response.code()==200){
                                if (response.body().success!=1){
                                    Log.d("f","f");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getURLdata1() {
        String urlData1 = null;
        String decodedString;
        try {
            //建立連線物件
            HttpURLConnection hc = null;
            //建立網址物件
            URL url = new URL(web);

            //連線
            hc = (HttpURLConnection) url.openConnection();
            hc.setRequestMethod("GET");
            hc.setDoInput(true);
            hc.setDoOutput(true);
//            hc.connect();
            //用BufferedReader讀回來
            BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));
            while ((decodedString = in.readLine()) != null) {
                urlData1 += decodedString;
            }
            in.close();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        // Parser(urlData);
        return urlData1;

    }
    public void abcd(String urlData1) {

        String temp = null;
        int start = 0;
        int end = 0;


        start = urlData1.indexOf("<title", end + 1);
        start = urlData1.indexOf(">", start + 1);
        end = urlData1.indexOf("</title>", start + 1);
        temp = urlData1.substring(start + 1, end);

        String[] diu=temp.trim().split(";");
        record_googlemap.setText(diu[2]);



    }
    private class threada extends Thread{


        @Override
        public  void run(){
            while (flag1==true){
                try {

                    abcd(getURLdata1());

                } catch (Exception e){e.printStackTrace();}

            }}
    }

}

