package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class RecordChangeIn extends AppCompatActivity {
    private String str,strs,cost,type,typemom,date,count,currency,img;
    private Button Send;
    private ImageView remove,changeback,record_change_in_camera;
    private Spinner account_mother_test,account_mother_category,account_mother_money;
    private EditText income,income_date,other;
    ArrayList<String> spinnerDataList;
    ArrayAdapter<String> adapter;
    ValueEventListener listener;
    private int Index=0;
    int maxId=0,maxId1=0,year,month,day;
    private ProgressDialog progressdialog;
    private Double fuckok=0.0,fine;
    private Boolean lil=false;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Uri imageUri;
    private Bitmap bitmap;
    private String url;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        setContentView(R.layout.record_change_in);
        Send=findViewById(R.id.Send);
        changeback=findViewById(R.id.changeback);
        cost=getIntent().getStringExtra("cost");
        type=getIntent().getStringExtra("type");
        typemom=getIntent().getStringExtra("typemom");
        date=getIntent().getStringExtra("date");
        count=getIntent().getStringExtra("count");
        Send = (Button) findViewById(R.id.Send);
        remove=(ImageView) findViewById(R.id.remove);
        income = (EditText) findViewById(R.id.income);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        income.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        income_date = (EditText) findViewById(R.id.income_date);
        account_mother_test =  (Spinner) findViewById(R.id.account_mother_test);
        account_mother_category = (Spinner) findViewById(R.id.account_mother_category);
        auth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        account_mother_money = (Spinner) findViewById(R.id.account_mother_money);
        other = (EditText) findViewById(R.id.other);
        str=(String)account_mother_test.getSelectedItem();
        strs=(String)account_mother_category.getSelectedItem();
        income_date.setText(date);
        income.setText(cost);
        record_change_in_camera=findViewById(R.id.record_change_in_camera);
        DatabaseReference photoref;
        photoref=databaseReference.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("income").child(date).child(count);
        photoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("img")){
                    String photourl=dataSnapshot.child("img").getValue().toString();
                    Picasso.get().load(photourl).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().into(record_change_in_camera);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(RecordChangeIn.this,R.array.accounting_mother_category1, R.layout.support_simple_spinner_dropdown_item);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        account_mother_test.setAdapter(tagAdapter);
        int spinnerPosition = tagAdapter.getPosition(typemom);
        account_mother_test.setSelection(spinnerPosition);
        storageReference= FirebaseStorage.getInstance().getReference(String.valueOf(System.currentTimeMillis())+"uploads");
        progressdialog=new ProgressDialog(RecordChangeIn.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(RecordChangeIn.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RecordChangeIn.this,new  String[]{Manifest.permission.CAMERA},0);
        }
        record_change_in_camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        account_mother_test.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str=(String) account_mother_test.getSelectedItem();
                spinnerDataList=new ArrayList<>();
                adapter=new ArrayAdapter<String>(RecordChangeIn.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
                account_mother_category.setAdapter(adapter);
                retrieveData();
                if (str.equals(typemom)){
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (adapter!=null){
                                int spinnerPosition1 = adapter.getPosition(type);
                                account_mother_category.setSelection(spinnerPosition1);
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
        account_mother_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strs=(String) account_mother_category.getSelectedItem();
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
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("original_income").getValue()==null){
                    income.setText(cost);
                }else {
                    income.setText(dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("original_income").getValue().toString());
                    ArrayAdapter myAdap = (ArrayAdapter) account_mother_money.getAdapter();
                    int spinnerPosition69 = myAdap.getPosition(dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("income_type").getValue().toString());
                    account_mother_money.setSelection(spinnerPosition69);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        income_date.setInputType(InputType.TYPE_NULL);
        income_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickerDialog();
                }
            }
        });
        income_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
                income_date.setInputType(InputType.TYPE_NULL);
            }
        });
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(income.getText())) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RecordChangeIn.this);
                    dialog.setTitle("小提醒");
                    dialog.setMessage("有資料尚未填寫");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });
                    dialog.show();
                }else{
                    progressdialog.setMessage("更改中，請稍後");
                    progressdialog.show();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (income_date.getText().toString().equals(date)){
                                if(currency.equals("新台幣 TWD")){
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("income").setValue(income.getText().toString());
                                }else {

                                    fuckok=Double.parseDouble(dataSnapshot.child("Money").child(date).child(String.valueOf(Index)).getValue().toString());
                                    Double fuck1=Double.parseDouble(income.getText().toString())*fuckok;
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("income").setValue(getInt(fuck1));
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("original_income").setValue(income.getText().toString());
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("income_typeID").setValue(Index);
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("income_type").setValue(currency);
                                }
                                if (url!=null){
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("img").setValue(url);
                                }
                                databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("type").setValue(strs);
                                databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).child("typeMom").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressdialog.dismiss();
                                        Intent intent=new Intent(RecordChangeIn.this,HomePage.class);
                                        startActivity(intent);
                                    }
                                });
                            }else {
                                maxId=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).getChildrenCount()+1;
                                if(currency.equals("新台幣 TWD")){
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("income").setValue(income.getText().toString());
                                }else {
                                    final String[] gg=income_date.getText().toString().split("/");
                                    if (Integer.valueOf(gg[0])>year || Integer.valueOf(gg[1])>(month+1) || Integer.valueOf(gg[2])>day){
                                        fine=Double.valueOf(dataSnapshot.child("Money").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(day)).child(String.valueOf(Index)).getValue().toString());
                                        lil=true;
                                    }
                                    else {
                                        fine=Double.valueOf(dataSnapshot.child("Money").child(income_date.getText().toString()).child(String.valueOf(Index)).getValue().toString());
                                    }
                                    Double fuck1=Double.parseDouble(income.getText().toString())*fine;
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("income").setValue(getInt(fuck1));
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("original_income").setValue(income.getText().toString());
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("income_typeID").setValue(Index);
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("income_type").setValue(currency);
                                }
                                databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("type").setValue(strs);
                                databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).child(String.valueOf(maxId)).child("typeMom").setValue(str);
                                maxId1=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).getChildrenCount();
                                if (maxId1==Integer.valueOf(count)){
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).removeValue();
                                }else {
                                    for (int g=Integer.valueOf(count);g<=maxId1;g++){
                                        if (g!=maxId)
                                            databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(g)).setValue(dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(g+1)).getValue());
                                        else
                                            databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(g)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                                    Intent intent=new Intent(RecordChangeIn.this,HomePage.class);
                                                    startActivity(intent);
                                                }
                                            });

                                    }
                                }


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        changeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordChangeIn.this,HomePage.class);
                startActivity(intent);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordChangeIn.this);
                dialog.setTitle("小提醒");
                dialog.setMessage("確定要刪除資料？");
                dialog.setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        progressdialog.setMessage("更改中，請稍後");
                        progressdialog.show();
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                final int xxx=(int)dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).getChildrenCount();
                                if(xxx!=Integer.valueOf(count)){
                                    for (int s=Integer.valueOf(count);s<=xxx;s++){
                                        if (s!=xxx)
                                            databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(s)).setValue(dataSnapshot.child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(s+1)).getValue());
                                        else {
                                            databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(String.valueOf(xxx)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressdialog.dismiss();
                                                    Intent intent = new Intent(RecordChangeIn.this,HomePage.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }

                                }else {
                                    databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(date).child(count).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressdialog.dismiss();
                                            Intent intent = new Intent(RecordChangeIn.this,HomePage.class);
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
            }});}
    public void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(income_date.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                income_date.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }
    public void retrieveData(){
        listener=databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("in_category").child(str).addValueEventListener(new ValueEventListener() {
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
                //camera是要放東西的id
                record_change_in_camera.setDrawingCacheEnabled(true);
                record_change_in_camera.buildDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] hello = baos.toByteArray();
                UploadTask uploadTask = storageReference.putBytes(hello);
                Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            Toast.makeText(RecordChangeIn.this, "上傳失敗", Toast.LENGTH_LONG).show();
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            url=task.getResult().toString().substring(0,task.getResult().toString().indexOf("&token"));
                            Toast.makeText(RecordChangeIn.this, "上傳成功", Toast.LENGTH_LONG).show();
                            //放相片
                            Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().into(record_change_in_camera);
                        }
                    }
                });

            }else{ }
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

}


