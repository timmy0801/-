package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ProjectPage extends AppCompatActivity {
    private RecyclerView project_recyclerview;
    private ArrayList<Project> arrayList;
    ProjectAdapter projectAdapter;
    private SearchView Project_search;
    private ImageButton project_backtohome,iv_3;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.project_home:
//                    mTextMessage.setText(R.string.title_home);
                    showNav(R.id.project_home);
                    return true;
                case R.id.project_newrecord:
//                    mTextMessage.setText(R.string.title_dashboard);
                    showNav(R.id.project_newrecord);
                    return true;
                case R.id.project_report:
//                    mTextMessage.setText(R.string.title_notifications);
                    showNav(R.id.project_report);
                    return true;
            }
            return false;
        }

        private void showNav(int navid) {
//        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();

            switch (navid) {
                case R.id.project_home:
                    Intent intent = new Intent(ProjectPage.this, HomePage.class);
                    startActivity(intent);
                    break;
//
                case R.id.project_newrecord:
                    Intent intent1 = new Intent(ProjectPage.this, Project_AddNewProject.class);
                    startActivity(intent1);
                    break;
                case R.id.project_report:
                    Intent intent2=new Intent(ProjectPage.this,ProjectReportActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_page);
        project_backtohome=findViewById(R.id.project_backtohome);
        iv_3=findViewById(R.id.project_tofd);
        project_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProjectPage.this,HomePage.class);
                startActivity(intent);
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProjectPage.this, FdUpdatePage.class);
                intent.putExtra("position","3");
                startActivity(intent);
            }
        });

        Project_search=findViewById(R.id.Project_search);
        //设置输入字体颜色
        int id = Project_search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) Project_search.findViewById(id);
        textView.setTextColor(Color.WHITE);//字体颜色
        textView.setTextSize(14);//字体、提示字体大小
        textView.setHintTextColor(Color.WHITE);//提示字体颜色
        if (Project_search != null) {
            try {        //--拿到字节码
                Class<?> argClass = Project_search.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(Project_search);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        project_recyclerview=findViewById(R.id.project_recyclerview);
        project_recyclerview.setHasFixedSize(true);
        project_recyclerview.setLayoutManager(new LinearLayoutManager(ProjectPage.this));
        arrayList=new ArrayList<>();

        check();

        Project_search.setIconifiedByDefault(false);
        Project_search.setFocusable(false);
        Project_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                projectAdapter.getFilter().filter(s);
                return false;
            }
        });

        BottomNavigationView navView = findViewById(R.id.project_bottomnav);//navigation是在homepage.xml的最底那個的ID
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void check() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Project");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Project project=snapshot.getValue(Project.class);
                    if (project.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            arrayList.add(project);

                    }
                }
                projectAdapter=new ProjectAdapter(ProjectPage.this,arrayList);
                projectAdapter.setOnclicklistener(new ProjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(ProjectPage.this,Project_Recyclerview.class);
                        intent.putExtra("project_name",arrayList.get(position).getName());
                        intent.putExtra("project_id",arrayList.get(position).getProject_id());
                        startActivity(intent);
                    }
                });
                project_recyclerview.setAdapter(projectAdapter);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
