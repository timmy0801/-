package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Regisiter_page extends AppCompatActivity {

    Button btnSend;
    EditText Username,Password,Password_check,Ris_Username,Email;
    DatabaseReference databaseReference;
    User user;
    FirebaseAuth auth;
    private ProgressDialog progressdialog;
    int check=0,content=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisiter_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        btnSend=  findViewById(R.id.register_subit);
        Password = findViewById(R.id.Password);
        Password_check = findViewById(R.id.Password_check);
        Ris_Username = findViewById(R.id.Ris_Username);
        Email = findViewById(R.id.Email);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");//取得資料庫連結
        auth=FirebaseAuth.getInstance();
        user=new User();
        progressdialog=new ProgressDialog(Regisiter_page.this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if (TextUtils.isEmpty(user.getPassword())
                        ||TextUtils.isEmpty(user.getPassword_check())||TextUtils.isEmpty(user.getRis_Username())||TextUtils.isEmpty(user.getEmail())) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Regisiter_page.this);
                    dialog.setTitle("小提醒");
                    dialog.setMessage("有資料尚未填寫");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });
                    dialog.show();
                }
                else  if(!(user.getPassword().equals(user.getPassword_check()))){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Regisiter_page.this);
                    dialog.setTitle("Warning");
                    dialog.setMessage("密碼與確認密碼不一致");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Password_check.setText("");
                        }
                    });
                    dialog.show();
                }
                else if(user.getPassword().length()<6){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Regisiter_page.this);
                    dialog.setTitle("Warning");
                    dialog.setMessage("密碼長度最少需要6個字元");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Password.setText("");
                            Password_check.setText("");
                        }
                    });
                    dialog.show();
                }
                else if(!(isEmail(user.getEmail()))){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Regisiter_page.this);
                    dialog.setTitle("Warning");
                    dialog.setMessage("信箱格式錯誤");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Email.setText("");
                        }
                    });
                    dialog.show();
                }
                else {
                    content=0;
                    check=0;
                    progressdialog.setMessage("wait");
                    progressdialog.show();
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot a : dataSnapshot.getChildren()) {
                                    content=content+1;
                                    if (a.child("ris_Username").getValue().toString().equals(user.getRis_Username())) {
                                        check = 1;
                                        Toast.makeText(Regisiter_page.this, "暱稱已被使用", Toast.LENGTH_LONG).show();
                                        progressdialog.dismiss();
                                        break;
                                    }
                                    else if(check==0&&content==dataSnapshot.getChildrenCount()){
                                        progressdialog.dismiss();
                                        progressdialog.setMessage("註冊中，請稍候");
                                        progressdialog.show();
                                        auth.createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    user.sendEmailVerification()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(Regisiter_page.this, "register success", Toast.LENGTH_LONG).show();

                                                                        SendDB();
                                                                        Intent intent = new Intent(Regisiter_page.this, MainActivity.class);
                                                                        startActivity(intent);


                                                                        //回到主頁面
//                                                    Toast.makeText(Regisiter_page.this, "Email 驗證成功", Toast.LENGTH_SHORT).show();
                                                                    } else{
//                                                    Toast.makeText(Regisiter_page.this, "Email 驗證"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });

                                                }


                                                else{
                                                    Toast.makeText(Regisiter_page.this, "Email已被註冊", Toast.LENGTH_LONG).show();
                                                    progressdialog.dismiss();
                                                }
                                            }
                                        });

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
    }
    private void getValues(){
        

        user.setPassword(Password.getText().toString());
        user.setPassword_check(Password_check.getText().toString());
        user.setRis_Username(Ris_Username.getText().toString());
        user.setEmail(Email.getText().toString());
    }
    private void SendDB(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                databaseReference.child(auth.getCurrentUser().getUid()).setValue(user);
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("0").setValue("早餐");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("1").setValue("午餐");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("2").setValue("晚餐");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("3").setValue("點心");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("4").setValue("飲料");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("5").setValue("酒");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("6").setValue("水果");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("飲食").child("7").setValue("冰品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("0").setValue("加油費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("1").setValue("停車費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("2").setValue("火車");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("3").setValue("捷運");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("4").setValue("公車");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("5").setValue("電動車");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("6").setValue("計程車");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("7").setValue("機票");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("交通").child("8").setValue("船票");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("0").setValue("電影");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("1").setValue("音樂");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("2").setValue("遊戲");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("3").setValue("健身");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("4").setValue("博弈");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("5").setValue("夜店");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("6").setValue("KTV");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("7").setValue("展覽");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("娛樂").child("8").setValue("演唱會");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("0").setValue("市場");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("1").setValue("上衣");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("2").setValue("下身");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("3").setValue("鞋子");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("4").setValue("配件");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("5").setValue("包包");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("6").setValue("美妝保養");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("7").setValue("電子產品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("購物").child("8").setValue("APP");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("0").setValue("日常用品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("1").setValue("家電");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("2").setValue("家具");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("3").setValue("房租");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("4").setValue("電費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("5").setValue("水費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("6").setValue("電話費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("7").setValue("網路費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("8").setValue("洗衣費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("家居").child("9").setValue("修繕費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("0").setValue("借款");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("1").setValue("投資");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("2").setValue("稅金");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("3").setValue("保險");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("4").setValue("捐款");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("5").setValue("彩券");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("個人").child("6").setValue("罰款");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child("0").setValue("美容美髮");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child("1").setValue("按摩");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child("2").setValue("運動");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child("3").setValue("外宿");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("生活休閒").child("4").setValue("旅行");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("0").setValue("掛號費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("1").setValue("藥品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("2").setValue("保健品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("3").setValue("醫療用品");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("4").setValue("打針");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("5").setValue("住院");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("6").setValue("手術");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("醫療").child("7").setValue("健康檢查");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child("0").setValue("書籍");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child("1").setValue("課程");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child("2").setValue("文具");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child("3").setValue("學雜費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("學習").child("4").setValue("影印費");
                databaseReference.child(auth.getCurrentUser().getUid()).child("category").child("其他").child("0").setValue("錢不見");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作").child("0").setValue("薪水");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("工作").child("1").setValue("獎金");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("0").setValue("零用錢");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("1").setValue("還款");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("2").setValue("禮金");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("3").setValue("利息");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("4").setValue("投資收入");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("5").setValue("意外收入");
                databaseReference.child(auth.getCurrentUser().getUid()).child("in_category").child("其他").child("6").setValue("保險理賠");
                Log.d("why","check");
                progressdialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public static boolean isEmail(String str){
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

//    private InputMethodManager manager=null;
//    manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


}


