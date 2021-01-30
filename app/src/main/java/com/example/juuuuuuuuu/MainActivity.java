package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    Button login,register,facebook,forget;
    TextView login_username,login_password,alert;
    DatabaseReference ref;
    FirebaseAuth auth;
    User user;

    private ProgressDialog progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideBottomUIMenu();

        //去標題
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }/////////////////////////////////圖片
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                drawable = MainActivity.this.getResources().getDrawable(
                        Integer.parseInt(source));
                drawable.setBounds(-10, -10,  drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        };


        login =  findViewById(R.id.login_login_button);
        register = (Button) findViewById(R.id.login_register_button) ;
        forget =findViewById(R.id.forget);
        //facebook =findViewById(R.id.login_facebook_login_button);
        //Spanned span = Html.fromHtml("<img src=\""+ R.drawable.facebook+"\"/> <font color=\"ffffff\">Facebook登入</font>", imgGetter, null);
        //facebook.setText(span);
        user=new User();
        login_username=findViewById(R.id.login_username);
        login_password=findViewById(R.id.login_password);
        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("User");//取得資料庫連結
        progressdialog=new ProgressDialog(this);
        if (user!=null&&user.isEmailVerified()) {
            finish();
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }

            login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(login_username.getText().toString()) || TextUtils.isEmpty(login_password.getText().toString()) ){
                            Toast.makeText(MainActivity.this,"Please enter the email or password", Toast.LENGTH_LONG).show();
                        }else {
                            check(login_username.getText().toString(), login_password.getText().toString());
                        }
                    }
                });
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,Regisiter_page.class);
                    startActivity(intent);
                }
            });
            forget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,ForgetPage.class);
                    startActivity(intent);
                }
            });

    }
    protected void hideBottomUIMenu() {
        //隱藏虛擬按鍵，並且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        }
    }

    public void check(String account,String password){

            progressdialog.setMessage("登入中，請稍後");
            progressdialog.show();
            auth.signInWithEmailAndPassword(account,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                        progressdialog.dismiss();
                        startActivity(new Intent(MainActivity.this,HomePage.class));
                    }
                    else {
                        Toast.makeText(MainActivity.this,"請驗證Email", Toast.LENGTH_LONG).show();
                        progressdialog.dismiss();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"登入失敗", Toast.LENGTH_LONG).show();
                    progressdialog.dismiss();
                }
            }
        });

    }

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

