package com.example.juuuuuuuuu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

//public class RecordPage extends AppCompatActivity {
public class RecordPage extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private ImageView backtohome;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabIn fragment1 = new TabIn();
    private TabOut fragment2 = new TabOut();
    String project_name,project_id,click_date;
    TextView record_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);
        record_id=findViewById(R.id.record_id);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        project_name=getIntent().getStringExtra("project_name");
        project_id=getIntent().getStringExtra("project_id");
        if (project_name!=null){
            record_id.setText(project_name);
        }


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        click_date=getIntent().getStringExtra("click_date");

        backtohome =findViewById(R.id.record_backtohome);
        viewPager = (ViewPager) findViewById(R.id.record_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.record_tablayout);

        backtohome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RecordPage.this,HomePage.class);
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
                        Bundle bundle = new Bundle();
                        bundle.putString("project_name",project_name);
                        bundle.putString("project_id",project_id);
                        bundle.putString("click_date",click_date);
                        fragment2.setArguments(bundle);

                        return fragment2;
                    case 1:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("project_name",project_name);
                        bundle2.putString("project_id",project_id);
                        bundle2.putString("click_date",click_date);
                        fragment1.setArguments(bundle2);
                        return fragment1;

                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

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
////////////////////////////////////////點空白
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