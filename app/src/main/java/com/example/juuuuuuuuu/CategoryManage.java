package com.example.juuuuuuuuu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class CategoryManage extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener  {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CategoryManageIn fragment1 = new CategoryManageIn();
    private CategoryManageOut fragment2 = new CategoryManageOut();
    private ImageButton backtohome ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_manage);

        viewPager = (ViewPager) findViewById(R.id.category_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.category_tablayout);
        backtohome = findViewById(R.id.category_backtohome);

        viewPager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);
        backtohome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CategoryManage.this,HomePage.class);
                startActivity(intent);
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return fragment2;
                    case 1:
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
}