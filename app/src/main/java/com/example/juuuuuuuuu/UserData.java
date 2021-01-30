package com.example.juuuuuuuuu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.net.URI;

import dmax.dialog.SpotsDialog;
public class UserData extends AppCompatActivity {
    private TextView U_id,U_email,U_ris_Username,user_nickname;
    private Button pic_send;
    private StorageReference stref;
    private DatabaseReference dbref,abc;
    private FirebaseAuth auth;
    private ImageButton User_Image;
    private ProgressDialog progressdialog;
    private Uri imageUri;
    private static final int pickImagecode=1000;
    AlertDialog dialog;
    Bitmap bitmap;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_page);
////////////////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
     ///////////////////////////////////////////////////////////////////////////
        U_id=findViewById(R.id.U_id);
        U_email=findViewById(R.id.U_email);
        U_ris_Username=findViewById(R.id.U_ris_Username);
        User_Image=findViewById(R.id.User_Image);
       // U_username=findViewById(R.id.U_username);
        auth=FirebaseAuth.getInstance();
        stref= FirebaseStorage.getInstance().getReference(auth.getCurrentUser().getUid()+"uploads");
        dbref=FirebaseDatabase.getInstance().getReference("User");
        pic_send=findViewById(R.id.pic_send);
        user_nickname=findViewById(R.id.user_nickname);
        dbref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("imageurl")){
                    url=dataSnapshot.child("imageurl").getValue().toString();
                    Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().transform(new CircleTransform()).into(User_Image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        User_Image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        dialog=new SpotsDialog.Builder().setContext(this).build();
        progressdialog=new ProgressDialog(this);
        progressdialog.setMessage("載入資料中，請稍後");
        progressdialog.show();
        final String currentUser=auth.getCurrentUser().getUid();
        dbref=dbref.child(currentUser);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String set_email=dataSnapshot.child("email").getValue().toString();
                String set_id=currentUser;
                String set_ris_Username=dataSnapshot.child("ris_Username").getValue().toString();
               // String set_username=dataSnapshot.child("username").getValue().toString();
                U_email.setText(set_email);
                user_nickname.setText(set_ris_Username);
              //  U_username.setText(set_username);
                progressdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        User_Image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        pic_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (url!=null){
                    dbref.child("imageurl").setValue(url);
                }
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
                User_Image.setDrawingCacheEnabled(true);
                User_Image.buildDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] hello = baos.toByteArray();
                UploadTask uploadTask = stref.putBytes(hello);
                Task<Uri> task=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            Toast.makeText(UserData.this, "上傳失敗", Toast.LENGTH_LONG).show();
                        }
                        return stref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            url=task.getResult().toString().substring(0,task.getResult().toString().indexOf("&token"));
                            Toast.makeText(UserData.this, "上傳成功", Toast.LENGTH_LONG).show();
                            //放相片
                             Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().into(User_Image);
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
