package com.example.juuuuuuuuu;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.juuuuuuuuu.Notigications.Token;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.iid.FirebaseInstanceId;
import com.marcohc.robotocalendar.RobotoCalendarView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.marcohc.robotocalendar.RobotoCalendarView.RobotoCalendarListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;


public class HomePage extends AppCompatActivity implements RobotoCalendarListener {
    private DatabaseReference dbref,friendreqref,friendref,databaseReference,RecordDB;
    private FirebaseAuth auth;
    private FirebaseUser CurrentUser;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view,leftheader;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private TextView month_cost,daily_cost,daily_in,home_date,txtHeader,home_income,earn;
    private ImageButton imagebutton_iv1,iv_3;
    private ImageView homepage_leftshow_image;
    private CalendarView calendarView;
    private String mCurrentState,click_date;
    private Array arr;
    private RecyclerView recyclerView;
    private CostAdapter adapter;
    private ArrayList<Cost> list;
    private ArrayList<String> changecost;
    private ArrayList<String> changetype;
    private ArrayList<String> changetypemom;
    private ArrayList<String> changedate;
    private ArrayList<String> changecount;
    private Boolean flag=true,flag1=true;
    private String type,typeMom,symbol,WebURL,DDD,a;
    private String cost,name,nofriendid;
    private int year,day,month,dayin;
    private RobotoCalendarView robotoCalendarView;
    private ProgressDialog progressDialog;
    private NotificationBadge notificationBadge;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    showNav(R.id.navigation_home);
//                    return true;
//                case R.id.navigation_scan:
//                    showNav(R.id.navigation_scan);
//                    return true;
                case R.id.navigation_newrecrd:
//                    mTextMessage.setText(R.string.title_home);
                    showNav(R.id.navigation_newrecrd);
                    return true;
                case R.id.navigation_report:
                    showNav(R.id.navigation_report);
                    return true;
                case R.id.navigation_fdlist:
                    showNav(R.id.navigation_fdlist);
                    return true;
            }
            return false;
        }

        private void showNav(int navid) {
//        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
            switch (navid) {
//                case R.id.navigation_home:
//                    Intent intent = new Intent(HomePage.this, HomePage.class);
//                    startActivity(intent);
//                    break;
//                case R.id.navigation_scan:
//                    Intent intent1 = new Intent(HomePage.this, TestScanner.class);
//                    startActivity(intent1);
//                    break;
                case R.id.navigation_newrecrd:


                    Intent intent2 = new Intent(HomePage.this, RecordPage.class);
                    if (click_date != null) {
                        intent2.putExtra("click_date",click_date);

                    }
                    startActivity(intent2);
                    break;
                case R.id.navigation_report:
                    Intent intent4=new Intent(HomePage.this,Report_page.class);
                    startActivity(intent4);
                    break;
                case R.id.navigation_fdlist:
                    Intent intent3 = new Intent(HomePage.this, FdUpdatePage.class);
                    intent3.putExtra("position","0");
                    startActivity(intent3);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        updateToken(FirebaseInstanceId.getInstance().getToken());

        WebURL = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
        dbref=FirebaseDatabase.getInstance().getReference("Money");
        //set 千分位
        DecimalFormat fomatter = new DecimalFormat("#,###");
        // Gets the calendar from the view
        robotoCalendarView = findViewById(R.id.robotoCalendarPicker);

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(false);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.setDate(new Date());

///////////////////////////////////////////////////////////////////////////標頭
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        /////////////////////////////////////////////////////////////////////////////

        mCurrentState="not_friends";
        // final String friend_id=getIntent().getStringExtra("friend_id");
        // display=findViewById(R.id.display);

        Calendar calendar=Calendar.getInstance();
        friendreqref=FirebaseDatabase.getInstance().getReference("Friend_Req");
        friendref=FirebaseDatabase.getInstance().getReference().child("Friends");
        auth=FirebaseAuth.getInstance();
        CurrentUser=auth.getCurrentUser();
        iv_3=findViewById(R.id.iv_3);
        month_cost=findViewById(R.id.month_cost);
        daily_cost=findViewById(R.id.daily_cost);
        daily_in=findViewById(R.id.daily_in);
        home_date=findViewById(R.id.home_date);
        home_income=findViewById(R.id.home_income);
        earn=findViewById(R.id.earn);
        notificationBadge=findViewById(R.id.badge);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        final String date1 = year + "/" + (month + 1) + "/" + day;
        databaseReference=FirebaseDatabase.getInstance().getReference("User");
        list = new ArrayList<>();
        changecost=new ArrayList<>();
        changetype=new ArrayList<>();
        changecount=new ArrayList<>();
        changedate=new ArrayList<>();
        changetypemom=new ArrayList<>();



        progressDialog=new ProgressDialog(HomePage.this);
        progressDialog.setMessage("請稍候");
        progressDialog.show();
        DatabaseReference db2=FirebaseDatabase.getInstance().getReference();
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Money").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(day)).getValue()==null){
                    thread t=new thread();
                    t.start(); try {
                        Thread.sleep(100);
                    }catch (InterruptedException d){}
                    flag=false;
                }
                if (dataSnapshot.child("invoice").child(String.valueOf(year)).child(String.valueOf((month+1)/2)).getValue()==null){

                    threada b=new threada();
                    b.start();
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException d){}
                    flag1=false;
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference abc = databaseReference.child(auth.getCurrentUser().getUid());


        abc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int daycost = 0;
                int monthcost = 0;
                dayin=0;
                int monthincome = 0;
                list = new ArrayList<>();
                changecost=new ArrayList<>();
                changetype=new ArrayList<>();
                changecount=new ArrayList<>();
                changedate=new ArrayList<>();
                changetypemom=new ArrayList<>();
                for (int n=1;n<=31;n++){
                    if (dataSnapshot.child("record").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(n)).getValue()!=null){
                        robotoCalendarView.markCircleImage1(year+"/"+(month+1)+"/"+n);
                    }
                    if (dataSnapshot.child("income").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(n)).getValue()!=null){
                        robotoCalendarView.markCircleImage2(year+"/"+(month+1)+"/"+n);
                    }
                }
                for (int x = 1; x <= 31; x++) {
                    // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                    //}
                    for (int y = 1; y <= dataSnapshot.child("income").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).getChildrenCount(); y++) {
                        if (dataSnapshot.child("income").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
                        }else {
                            monthincome = monthincome + Integer.valueOf(dataSnapshot.child("income").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).child("income").getValue().toString());
                        }
                    }
                }
                home_income.setText("本月收入︰NT＄"+fomatter.format((monthincome)));


                if (dataSnapshot.child("record").child(date1).getValue() == null) {

                    home_date.setText(date1);
                    daily_cost.setText("本日支出︰NT＄0");

                    setadapter();

                    for (int x = 1; x <= 31; x++) {
                        // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                        //}
                        for (int y = 1; y <= dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).getChildrenCount(); y++) {
                            if (dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
                            }else {
                                monthcost = monthcost + Integer.valueOf(dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).child("cost").getValue().toString());
                            }
                        }
                    }
                    month_cost.setText("本月支出︰NT＄"+fomatter.format((monthcost)));

                    //month_cost.setText(cost);
                } else {

                    RecordDB = FirebaseDatabase.getInstance().getReference("User").child(auth.getCurrentUser().getUid()).child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(day));
                    RecordDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for (int w = 1; w <= dataSnapshot.getChildrenCount(); w++) {
                                cost = dataSnapshot.child(Integer.toString(w)).child("cost").getValue().toString();
                                type = dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString();
                                typeMom = dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString();
                                symbol = dataSnapshot.child(Integer.toString(w)).child("record").toString();
                                list.add(new Cost(
                                        cost,
                                        type,
                                        typeMom,
                                        symbol,
                                        "true"

                                ));

                                changecost.add(dataSnapshot.child(Integer.toString(w)).child("cost").getValue().toString());
                                changedate.add((year)+"/"+(month + 1)+"/"+(day));
                                changetype.add(dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString());
                                changetypemom.add(dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString());
                                changecount.add(String.valueOf(w));
                            }
                            setadapter();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    home_date.setText(date1);
                    for (int z = 1; z <= dataSnapshot.child("record").child(date1).getChildrenCount(); z++) {
                        if (dataSnapshot.child("record").child(date1).child(Integer.toString(z))==null){
                            continue;
                        }else{

                            daycost = daycost + Integer.valueOf(dataSnapshot.child("record").child(date1).child(Integer.toString(z)).child("cost").getValue().toString());
                            daily_cost.setText("本日支出︰NT＄"+fomatter.format((daycost)));}
                    }
                    for (int x = 1; x <= 31; x++) {
                        // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                        //}
                        for (int y = 1; y <= dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).getChildrenCount(); y++) {
                            if(dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){}
//                            if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
//                            }
                            else {
                                monthcost = monthcost + Integer.valueOf(dataSnapshot.child("record").child(Integer.toString(year)).child(Integer.toString(month + 1)).child(Integer.toString(x)).child(Integer.toString(y)).child("cost").getValue().toString());
                            }
                        }
                    }
                    month_cost.setText("本月支出︰NT＄"+fomatter.format((monthcost)));
                }
                if(dataSnapshot.child("income").child(date1).getValue()==null){
                    daily_in.setText("本日收入︰NT＄0");

                }else {
                    for (int z=1;z<=dataSnapshot.child("income").child(date1).getChildrenCount();z++){
                        if (dataSnapshot.child("income").child(date1).child(Integer.toString(z)).getValue()==null){}
                        else {
                            dayin = dayin + Integer.valueOf(dataSnapshot.child("income").child(date1).child(Integer.toString(z)).child("income").getValue().toString());
                        }
                        daily_in.setText("本日收入︰NT＄"+fomatter.format((dayin)));
                    }
                    DatabaseReference RecordDBB=FirebaseDatabase.getInstance().getReference("User").child(auth.getCurrentUser().getUid()).child("income").child(date1);
                    RecordDBB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(int w=1;w<=dataSnapshot.getChildrenCount();w++){
                                cost=dataSnapshot.child(Integer.toString(w)).child("income").getValue().toString();
                                type=dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString();
                                typeMom = dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString();
                                symbol = dataSnapshot.child(Integer.toString(w)).child("record").toString();

                                list.add(new Cost(
                                        cost,
                                        type,
                                        typeMom,
                                        symbol,
                                        "false"

                                ));
                                changedate.add(date1);
                                changecost.add(dataSnapshot.child(Integer.toString(w)).child("income").getValue().toString());
                                changetype.add(dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString());
                                changetypemom.add(dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString());
                                changecount.add(String.valueOf(w));
                            }
                            setadapter();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if (monthincome - monthcost < 0) {
                    earn.setText("本月總收支︰NT＄"+fomatter.format((monthincome - monthcost)));
                } else {
                    earn.setText("本月總收支︰NT＄"+fomatter.format((monthincome - monthcost)));
                }
                if (dataSnapshot.child("notifyrecord").getValue()!=null){
                    int count=0;
                    for (int d=1950;d<=2100;d++){
                        for (int dd=1;dd<=12;dd++){
                            for (int ddd=1;ddd<=31;ddd++){
                                if (dataSnapshot.child("notifyrecord").child(String.valueOf(d)).child(String.valueOf(dd)).child(String.valueOf(ddd)).getValue()!=null){
                                    count=count+(int) dataSnapshot.child("notifyrecord").child(String.valueOf(d)).child(String.valueOf(dd)).child(String.valueOf(ddd)).getChildrenCount();
                                }
                            }
                        }
                    }
                    if (count>=100){
                        notificationBadge.setText("99+");
                    }else {
                        notificationBadge.setNumber(count);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        robotoCalendarView.setRobotoCalendarListener(new RobotoCalendarListener() {
            @Override
            public void onDayClick(String date) {
                String[] a=date.split("/");
                int i=Integer.valueOf(a[0]);
                int i1=Integer.valueOf(a[1])-1;
                int i2=Integer.valueOf(a[2]);
                home_date.setText(date);
                click_date=date;
                DatabaseReference abc=databaseReference.child(auth.getCurrentUser().getUid());
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int daycost=0;
                        int monthcost=0;
                        dayin=0;
                        int monthincome=0;
                        list=new ArrayList<>();
                        changecost=new ArrayList<>();
                        changetype=new ArrayList<>();
                        changedate=new ArrayList<>();
                        changecount=new ArrayList<>();
                        changetypemom=new ArrayList<>();

                        for (int x=1;x<=31;x++){
                            // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                            //}
                            for (int y=1;y<=dataSnapshot.child("income").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getChildrenCount();y++){
                                //if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
                                //}
                                //
                                monthincome=monthincome+Integer.valueOf(dataSnapshot.child("income").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).child("income").getValue().toString());

                            }
                        }
                        home_income.setText("本月收入︰NT＄"+fomatter.format((monthincome)));



                        if(dataSnapshot.child("record").child(date).getValue()==null){

                            home_date.setText(date);
                            daily_cost.setText("本日支出︰NT＄0");
                            setadapter();
                            for (int x=1;x<=31;x++){
                                // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                                //}
                                for (int y=1;y<=dataSnapshot.child("record").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getChildrenCount();y++){
                                    //if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
                                    //}
                                    monthcost=monthcost+Integer.valueOf(dataSnapshot.child("record").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).child("cost").getValue().toString());

                                }
                            }
                            month_cost.setText("本月支出︰NT＄"+fomatter.format((monthcost)));

                            //month_cost.setText(cost);
                        }
                        else {

                            RecordDB=FirebaseDatabase.getInstance().getReference("User").child(auth.getCurrentUser().getUid()).child("record").child(date);
                            RecordDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(int w=1;w<=dataSnapshot.getChildrenCount();w++){
                                        cost=dataSnapshot.child(Integer.toString(w)).child("cost").getValue().toString();
                                        type=dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString();
                                        typeMom = dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString();
                                        symbol = dataSnapshot.child(Integer.toString(w)).child("record").toString();

                                        list.add(new Cost(
                                                cost,
                                                type,
                                                typeMom,
                                                symbol,
                                                "true"

                                        ));
                                        changedate.add(date);
                                        changecost.add(dataSnapshot.child(Integer.toString(w)).child("cost").getValue().toString());
                                        changetype.add(dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString());
                                        changetypemom.add(dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString());
                                        changecount.add(String.valueOf(w));
                                    }
                                    setadapter();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            home_date.setText(date);
                            for (int z=1;z<=dataSnapshot.child("record").child(date).getChildrenCount();z++){
                                if (dataSnapshot.child("record").child(date).child(Integer.toString(z)).getValue()==null){}
                                else {
                                    daycost = daycost + Integer.valueOf(dataSnapshot.child("record").child(date).child(Integer.toString(z)).child("cost").getValue().toString());
                                }
                                daily_cost.setText("本日支出︰NT＄"+fomatter.format((daycost)));
                            }
                            for (int x=1;x<=31;x++){
                                // if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getValue()==null){
                                //}
                                for (int y=1;y<=dataSnapshot.child("record").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).getChildrenCount();y++){
                                    //if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).getValue()==null){
                                    //}
                                    if (dataSnapshot.child("record").child(Integer.toString(i)).child(Integer.toString(i1+1)).child(Integer.toString(x)).child(Integer.toString(y)).child("cost").getValue()==null){}
                                    else {
                                        monthcost = monthcost + Integer.valueOf(dataSnapshot.child("record").child(Integer.toString(i)).child(Integer.toString(i1 + 1)).child(Integer.toString(x)).child(Integer.toString(y)).child("cost").getValue().toString());
                                    }
                                }
                            }
                            month_cost.setText("本月支出︰NT＄"+fomatter.format((monthcost)));
                        }
                        if(dataSnapshot.child("income").child(date).getValue()==null){
                            daily_in.setText("本日收入︰NT＄0");
                            Log.v("d","fuck");
                        }else {
                            for (int z=1;z<=dataSnapshot.child("income").child(date).getChildrenCount();z++){
                                if (dataSnapshot.child("income").child(date).child(Integer.toString(z)).getValue()==null){}
                                else {
                                    dayin = dayin + Integer.valueOf(dataSnapshot.child("income").child(date).child(Integer.toString(z)).child("income").getValue().toString());
                                }
                                daily_in.setText("本日收入︰NT＄"+fomatter.format((dayin)));
                            }
                            DatabaseReference RecordDBB=FirebaseDatabase.getInstance().getReference("User").child(auth.getCurrentUser().getUid()).child("income").child(date);
                            RecordDBB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(int w=1;w<=dataSnapshot.getChildrenCount();w++){
                                        cost=dataSnapshot.child(Integer.toString(w)).child("income").getValue().toString();
                                        type=dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString();
                                        typeMom = dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString();
                                        symbol = dataSnapshot.child(Integer.toString(w)).child("record").toString();
                                        list.add(new Cost(
                                                cost,
                                                type,
                                                typeMom,
                                                symbol,
                                                "false"

                                        ));
                                        changedate.add(date);
                                        changecost.add(dataSnapshot.child(Integer.toString(w)).child("income").getValue().toString());
                                        changetype.add(dataSnapshot.child(Integer.toString(w)).child("type").getValue().toString());
                                        changetypemom.add(dataSnapshot.child(Integer.toString(w)).child("typeMom").getValue().toString());
                                        changecount.add(String.valueOf(w));
                                    }
                                    setadapter();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        if(monthincome-monthcost<0){
                            earn.setText("本月總收支︰NT＄"+fomatter.format((monthincome-monthcost)));
                        }else {
                            earn.setText("本月總收支︰NT＄"+fomatter.format((monthincome-monthcost)));
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onDayLongClick(Date date) {

            }

            @Override
            public void onRightButtonClick(String right) {
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(int v=1;v<=31;v++){
                            if (dataSnapshot.child("record").child(right).child(String.valueOf(v)).getValue()!=null){
                                robotoCalendarView.markCircleImage1(right+v);
                            }
                            if (dataSnapshot.child("income").child(right).child(String.valueOf(v)).getValue()!=null){
                                robotoCalendarView.markCircleImage2(right+v);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }


            @Override
            public void onLeftButtonClick(String left) {
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(int v=1;v<=31;v++){
                            if (dataSnapshot.child("record").child(left).child(String.valueOf(v)).getValue()!=null){
                                robotoCalendarView.markCircleImage1(left+v);
                            }
                            if (dataSnapshot.child("income").child(left).child(String.valueOf(v)).getValue()!=null){
                                robotoCalendarView.markCircleImage2(left+v);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
            @Override
            public void onMonthClick(){
                abc.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        year=calendar.get(Calendar.YEAR);
                        month=calendar.get(Calendar.MONTH);
                        for (int n=1;n<=31;n++){
                            if (dataSnapshot.child("record").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(n)).getValue()!=null){
                                robotoCalendarView.markCircleImage1(year+"/"+(month+1)+"/"+n);
                            }
                            if (dataSnapshot.child("income").child(String.valueOf(year)).child(String.valueOf(month+1)).child(String.valueOf(n)).getValue()!=null){
                                robotoCalendarView.markCircleImage2(year+"/"+(month+1)+"/"+n);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomePage.this, FdUpdatePage.class);
                intent.putExtra("position","3");
                startActivity(intent);
            }
        });
        friendreqref.child(CurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> arr= new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    int friendcount = (int) dataSnapshot.getChildrenCount();
                    for (int i = 0; i < friendcount; i++) {
                        //arr.add(dataSnapshot.getValue().toString());
                        String a = dataSnapshot.getValue().toString();
                        String[] tokens = a.split(",");

                        for (String token : tokens) {
                            arr.add(token);
                            tokens[i].replaceAll("\\p{Punct}", "");

                        }
                        String friendid = arr.get(i).replaceAll("\\p{Punct}", "");
                        nofriendid=friendid.replaceAll("=requesttype=received","");

                        if (dataSnapshot.hasChild(nofriendid)){
                            String req_type=dataSnapshot.child(nofriendid).child("request_type").getValue().toString();
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    name=dataSnapshot.child(nofriendid).child("ris_Username").getValue().toString();
                                    Log.v("a",""+name);
                                    Log.v("ab",""+nofriendid);
                                    if (req_type.equals("received")){
                                        AlertDialog.Builder dialog=new AlertDialog.Builder(HomePage.this);
                                        dialog.setTitle("您有一則交友邀請");
                                        Log.v("c",""+name);
                                        dialog.setMessage(name+"想與您做朋友");
                                        dialog.setPositiveButton("接受", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                mCurrentState = "req_received";
                                                if (mCurrentState.equals("req_received")){
                                                    final String CurrentDate= DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.MEDIUM,new Locale("zh", "TW")).format(new Date());
                                                    friendref.child(CurrentUser.getUid()).child(nofriendid).setValue(CurrentDate)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    friendref.child(nofriendid).child(CurrentUser.getUid()).setValue(CurrentDate)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    friendreqref.child(CurrentUser.getUid()).child(nofriendid).removeValue();
                                                                                    mCurrentState="friend";
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                            }
                                        });
                                        dialog.setNegativeButton("拒絕", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                friendreqref.child(auth.getCurrentUser().getUid()).removeValue();
                                            }
                                        });
                                        dialog.show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

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


//        showNav(R.id.content);
//        init();
        BottomNavigationView navView = findViewById(R.id.navigation);//navigation是在homepage.xml的最底那個的ID
//        navView.setItemTextColor(null);
//        navView.setItemIconTintList(null);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigation_view =findViewById(R.id.home_page_leftview);
        View header=navigation_view.inflateHeaderView(R.layout.left_header);//要有這才能拿左側的ID
        homepage_leftshow_image=header.findViewById(R.id.homepage_leftshow_image);
        txtHeader=header.findViewById(R.id.txtHeader);
        homepage_leftshow_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomePage.this,UserData.class);
                startActivity(intent);
            }
        });
        final String currentUser=auth.getCurrentUser().getUid();
        DatabaseReference userdata=databaseReference.child(currentUser);
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String set_username=dataSnapshot.child("ris_Username").getValue().toString();
                txtHeader.setText(set_username);
                if (!dataSnapshot.hasChild("imageurl")){}
                else {
                    String img=dataSnapshot.child("imageurl").getValue().toString();
                    Picasso.get().load(img).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).fit().centerCrop().transform(new CircleTransform()).into(homepage_leftshow_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////左側的內容
        imagebutton_iv1=findViewById(R.id.iv_1);
        imagebutton_iv1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
//                if (id == R.id.left_setting) {
//                    // Toast.makeText(MainActivity.this, "首頁", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(HomePage.this,UserData.class);
//                    startActivity(intent);
//                    return true;
//                } else
                if (id == R.id.left_notification) {
                    Intent intent = new Intent(HomePage.this, ShareCost.class);
                    startActivity(intent);
                    return true;
                }


                if (id == R.id.left_remind) {
                    Intent intent = new Intent(HomePage.this, CategoryManage.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.left_invoice) {
                    Intent intent = new Intent(HomePage.this, Invoice.class);
                    startActivity(intent);
                    return true;
                }
//                  else if (id == R.id.left_mission) {
//                    Intent intent = new Intent(HomePage.this, MissionPage.class);
//                    startActivity(intent);
//                    return true;
//                }
                else if (id == R.id.left_project) {
                    Intent intent = new Intent(HomePage.this, ProjectPage.class);
                    startActivity(intent);
                    return true;
                }
                else if (id == R.id.left_currency) {
                    Intent intent = new Intent(HomePage.this, Currency_setting.class);
                    startActivity(intent);
                    return true;
                }
//                else if (id == R.id.left_teamcompetition) {
//                    Intent intent = new Intent(HomePage.this, TeamCompetition.class);
//                    startActivity(intent);
//                    return true;
//                }
                else if(id==R.id.left_logout){
                    FirebaseAuth.getInstance().signOut();
                    Intent signout=new Intent(HomePage.this,MainActivity.class);
                    startActivity(signout);
                    return true;
                }

                return false;
            }
        });

    }
    public void setadapter(){
        recyclerView=findViewById(R.id.home_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CostAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new CostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (list.get(position).getSymbol().equals("true")){
                    Intent intent=new Intent(HomePage.this,RecordChangeOut.class);

                    intent.putExtra("cost",changecost.get(position));
                    intent.putExtra("date",changedate.get(position));
                    intent.putExtra("type",changetype.get(position));
                    intent.putExtra("count",changecount.get(position));
                    intent.putExtra("typemom",changetypemom.get(position));

                    startActivity(intent);
                }else {

                    Intent intent=new Intent(HomePage.this,RecordChangeIn.class);

                    intent.putExtra("cost",changecost.get(position));
                    intent.putExtra("date",changedate.get(position));
                    intent.putExtra("type",changetype.get(position));
                    intent.putExtra("count",changecount.get(position));
                    intent.putExtra("typemom",changetypemom.get(position));

                    startActivity(intent);

                }


            }
        });
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onDayClick(String date) {
        Toast.makeText(this, "onDayClick: " + date, Toast.LENGTH_SHORT).show();
        //       home_date.setText(date.toString());
    }

    @Override
    public void onDayLongClick(Date date) {
        Toast.makeText(this, "onDayLongClick: " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightButtonClick(String right) {
        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLeftButtonClick(String left) {
        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMonthClick(){}
    public String getURLdata() {
        String urlData = null;
        String decodedString;
        try {
            //建立連線物件
            HttpURLConnection hc = null;
            //建立網址物件
            URL url = new URL(WebURL);

            //連線
            hc = (HttpURLConnection) url.openConnection();
            hc.setRequestMethod("GET");
            hc.setDoInput(true);
            hc.setDoOutput(true);
//            hc.connect();
            //用BufferedReader讀回來
            BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));
            while ((decodedString = in.readLine()) != null) {
                urlData += decodedString;
            }
            in.close();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        // Parser(urlData);
        return urlData;

    }

    public void Parser(String urlData) {
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c=Calendar.getInstance();

                String temp = null;
                int start = 0;
                int end = 0;
                int counter = 0;
                for (counter=0;counter<19;counter++){
                    year=c.get(Calendar.YEAR);
                    month=c.get(Calendar.MONTH)+1;
                    day=c.get(Calendar.DAY_OF_MONTH);
                    String date1 = year + "/" + month + "/" + day;
                    start = urlData.indexOf(" <td data-table=\"本行現金買入\" class=\"text-right display_none_print_show print_width\"", end + 1);
                    start = urlData.indexOf(">", start + 1);
                    end = urlData.indexOf("</td>", start + 1);
                    temp = urlData.substring(start + 1, end);
                    if (!temp.equals("-")) {
                        dbref.child(date1).child(String.valueOf(counter+1)).setValue(temp);
                    }else {
                        if (day!=1){
                            day=day-1;
                        }
                        else if (day==1 && month!=1){
                            if (month==2 || month==4 || month==6 || month==8 || month==9 || month==11){
                                day=31;
                                month=month-1;
                            }
                            else if (month!=3){
                                day=30;
                                month=month-1;
                            }
                            else {
                                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0 && year % 4000 != 0)){
                                    day=29;
                                    month=month-1;
                                }
                                else {
                                    day=28;
                                    month=month-1;
                                }
                            }
                        }
                        else if (day==1 && month==1){
                            day=31;
                            month=12;
                            year=year-1;
                        }

                        String mo=dataSnapshot.child(String.valueOf(year)).child(String.valueOf(month)).child(String.valueOf(day)).child(String.valueOf(counter+1)).getValue().toString();
                        dbref.child(date1).child(String.valueOf(counter+1)).setValue(mo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private class thread extends Thread{


        @Override
        public  void run(){
            while (flag==true){
                try {
                    DDD=getURLdata();
                    Parser(DDD);

                } catch (Exception e){e.printStackTrace();}

            }}

    }
    private void updateToken(String token){
        DatabaseReference d=FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        d.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }

    public String getURLdata1() {
        String urlData1 = null;
        String decodedString;
        try {
            //建立連線物件
            HttpURLConnection hc = null;
            //建立網址物件
            URL url = new URL("https://invoices.com.tw/invoice.html");

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
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Calendar c=Calendar.getInstance();
                DatabaseReference sm=FirebaseDatabase.getInstance().getReference();
                String temp = null;
                int start = 0;
                int end = 0;
                int counter = 0;
                String temp1 = null;
                int start1 = 0;
                int end1 = 0;
                String temp2 = null;
                int start2 = 0;
                int end2 = 0;
                start2 = urlData1.indexOf("<title>發", end2 + 1);
                start2 = urlData1.indexOf("票", start2 + 1);
                end2 = urlData1.indexOf("月", start2 + 1);
                temp2 = urlData1.substring(start2 + 1, end2);
                String[] cool777=temp2.split(" ");
                final int okok=Integer.valueOf(cool777[1].trim())/2;
                for (counter=0;counter<3;counter++){

                    start = urlData1.indexOf("<td width=\"200\" class=\"number\"", end + 1);
                    start = urlData1.indexOf(">", start + 1);
                    end = urlData1.indexOf("</td>", start + 1);
                    temp = urlData1.substring(start + 1, end);

                    if (counter==0 || counter==1){
                        sm.child("invoice").child(String.valueOf(c.get(Calendar.YEAR))).child(String.valueOf(okok)).child("special").child(String.valueOf(counter)).setValue(temp.trim());
                    }
                    if(counter==2){
                        String[] bigdick=temp.trim().split("</span>");
                        for (int gg=0;gg<bigdick.length;gg++){
                            sm.child("invoice").child(String.valueOf(c.get(Calendar.YEAR))).child(String.valueOf(okok)).child("big").child(String.valueOf(gg)).setValue(bigdick[gg].replace("<span class=\"number2\">",""));
                        }}
                }
                start1 = urlData1.indexOf("<td width=\"200\" class=\"number3\"", end1 + 1);
                start1 = urlData1.indexOf(">", start1 + 1);
                end1 = urlData1.indexOf("</td>", start1 + 1);
                temp1 = urlData1.substring(start1 + 1, end1);
                String[] okbye=temp1.trim().split("、");
                for(int w=0;w<okbye.length;w++){
                    sm.child("invoice").child(String.valueOf(c.get(Calendar.YEAR))).child(String.valueOf(okok)).child("small").child(String.valueOf(w)).setValue(okbye[w]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private class threada extends Thread{

        @Override
        public  void run(){
            while (flag1==true){
                try {
                    a=getURLdata1();
                    abcd(a);

                } catch (Exception e){e.printStackTrace();}

            }}
    }
}

