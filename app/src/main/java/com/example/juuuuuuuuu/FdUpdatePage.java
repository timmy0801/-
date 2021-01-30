package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class FdUpdatePage extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private ImageView backtohome;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FdViewTabA fdViewTabA = new FdViewTabA();
    private FdViewTabB fdViewTabB = new FdViewTabB();
    private FdViewTabC fdViewTabC = new FdViewTabC();
    private FdViewTabD fdViewTabD = new FdViewTabD();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd_update_page);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        backtohome = findViewById(R.id.fdupdate_backtohome);
        viewPager = (ViewPager) findViewById(R.id.fd_update_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.fd_update_tablayout);


        backtohome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FdUpdatePage.this,HomePage.class);
                startActivity(intent);
            }
        });

        viewPager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return fdViewTabA;
                    case 1:
                        return fdViewTabB;
                    case 2:
                        return fdViewTabC;
                    case 3:
                        return fdViewTabD;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        viewPager.setCurrentItem(Integer.valueOf(getIntent().getStringExtra("position")));
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TabLayout里的TabItem被选中的时候触发

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager滑动之后显示触发
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
   // private Report_data_in fragment2 = new Report_data_in();
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.friend_home:
////                    mTextMessage.setText(R.string.title_home);
//                    showNav(R.id.friend_home);
//                    return true;
//                case R.id.friend_love:
////                    mTextMessage.setText(R.string.title_dashboard);
//                    showNav(R.id.friend_love);
//                    return true;
//                case R.id.Friend_list:
////                    mTextMessage.setText(R.string.title_notifications);
//                    showNav(R.id.Friend_list);
//                    return true;
//            }
//            return false;
//        }
//
//        private void showNav(int navid) {
////        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
//
//            switch (navid) {
//                case R.id.friend_home:
//                    Intent intent = new Intent(FdUpdatePage.this, HomePage.class);
//                    startActivity(intent);
//                    break;
////
//                case R.id.friend_love:
//                    break;
//
//                case R.id.Friend_list:
//                    Intent intent2 = new Intent(FdUpdatePage.this, FdListPage.class);
//                    startActivity(intent2);
//                    break;
//            }
//        }
//    };




