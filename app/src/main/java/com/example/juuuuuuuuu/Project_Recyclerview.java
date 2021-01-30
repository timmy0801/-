package com.example.juuuuuuuuu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Project_Recyclerview extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private project_myself_record fragment1 = new project_myself_record();
    private project_team_record fragment2 = new project_team_record();
    String project_name,project_id;
    TextView project_name_record;
    ImageButton project_backtohome,iv_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_recyclerview);
        project_name_record=findViewById(R.id.project_name_record);
        project_backtohome=findViewById(R.id.project_backtohome);
        tabLayout = findViewById(R.id.project_tablayout);
        viewPager =findViewById(R.id.project_viewpager);
        iv_3=findViewById(R.id.iv_3);
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Project_Recyclerview.this, FdUpdatePage.class);
                intent.putExtra("position","3");
                startActivity(intent);
            }
        });
        project_name=getIntent().getStringExtra("project_name");
        project_id=getIntent().getStringExtra("project_id");
        project_name_record.setText(project_name);
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
                        fragment2.setArguments(bundle);
                        return fragment2;
                    case 1:
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("project_name",project_name);
                        bundle2.putString("project_id",project_id);
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
        project_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Project_Recyclerview.this,ProjectPage.class);
                startActivity(intent);
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
