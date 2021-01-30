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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class TabIn extends Fragment {
    Button Send;
    EditText income, income_date,  record_team,  record_googlemap, other;
    Spinner account_mother_money,account_mother_test, account_mother_category;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageView tabin_camera;
    FirebaseAuth auth;
    User user;
    Bitmap bitmap;
    String str,strs,currency;
    String url;
    int maxId=0,year,month,day;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    ValueEventListener listener;
    Uri imageUri;
    int currency_policy,Index;
    private ProgressDialog progressDialog;
    private Double fine;
    private Boolean lil=false;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.activity_tab_in, container, false);
        abc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });
        storageReference= FirebaseStorage.getInstance().getReference(String.valueOf(System.currentTimeMillis())+"uploads");
        Calendar calendar = Calendar.getInstance();
        progressDialog=new ProgressDialog(getActivity());
        Send = (Button) abc.findViewById(R.id.Send);
        income = (EditText) abc.findViewById(R.id.income);
        income.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        income_date = (EditText) abc.findViewById(R.id.income_date);
        account_mother_test =  (Spinner) abc.findViewById(R.id.account_mother_test);
        account_mother_category = (Spinner) abc.findViewById(R.id.account_mother_category);

        account_mother_money = (Spinner) abc.findViewById(R.id.account_mother_money);
        record_googlemap = (EditText) abc.findViewById(R.id.record_googlemap);
        other = (EditText) abc.findViewById(R.id.other);
        str=(String)account_mother_test.getSelectedItem();
        strs=(String)account_mother_category.getSelectedItem();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        income_date.setText(year+"/"+(month+1)+"/"+day);
        tabin_camera=abc.findViewById(R.id.tabin_camera);
        tabin_camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
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
        ArrayAdapter<CharSequence> tagAdapter = ArrayAdapter.createFromResource(abc.getContext(),R.array.accounting_mother_category1, R.layout.support_simple_spinner_dropdown_item);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        account_mother_test.setAdapter(tagAdapter);
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
        getnum();
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
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(income.getText())) {
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
                    progressDialog.setMessage("記帳中，請稍候");
                    progressDialog.show();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            maxId = (int) (dataSnapshot.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString()).getChildrenCount()) + 1;
                            DatabaseReference abc = databaseReference.child("User").child(auth.getCurrentUser().getUid()).child("income").child(income_date.getText().toString());
                            if (Index!=0){
                                final String[] gg=income_date.getText().toString().split("/");
                                if (Integer.valueOf(gg[0])>year || Integer.valueOf(gg[1])>(month+1) || Integer.valueOf(gg[2])>day){
                                    fine=Double.valueOf(dataSnapshot.child("Money").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(day)).child(String.valueOf(Index)).getValue().toString());
                                    lil=true;
                                }
                                else {
                                    fine=Double.valueOf(dataSnapshot.child("Money").child(income_date.getText().toString()).child(String.valueOf(Index)).getValue().toString());
                                }
                            }
                            if (currency.equals("新台幣 TWD")) {
                                abc.child(Integer.toString(maxId)).child("income").setValue(income.getText().toString());
                            } else {
                                abc.child(Integer.toString(maxId )).child("income").setValue(getInt(Double.parseDouble(income.getText().toString()) * fine));
                                abc.child(Integer.toString(maxId )).child("original_income").setValue(income.getText().toString());
                                abc.child(Integer.toString(maxId )).child("income_typeID").setValue(Index);
                                abc.child(Integer.toString(maxId )).child("income_type").setValue(currency);
                            }
                            if (lil==true){Toast.makeText(getActivity(), "匯率設置為今日之匯率", Toast.LENGTH_LONG).show();}
                            if (url!=null){
                                abc.child(Integer.toString(maxId)).child("img").setValue(url);
                            }
                            abc.child(Integer.toString(maxId)).child("type").setValue(strs);
                            abc.child(Integer.toString(maxId)).child("typeMom").setValue(str);
                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), HomePage.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                //abc.child("date").setValue(record_date.getText().toString());

            }
        });
        return abc;
    }
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
    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    //開相機
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
    //接收值
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
                //camera是要放東西的id
                tabin_camera.setDrawingCacheEnabled(true);
                tabin_camera.buildDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] hello = baos.toByteArray();
                UploadTask uploadTask = storageReference.putBytes(hello);
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
                            Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().into(tabin_camera);
                        }
                    }
                });

            }else{ }
        }
    }
    //切相片
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