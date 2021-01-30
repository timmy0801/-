package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProjectReportActivity extends AppCompatActivity {
    ImageButton project_previous,project_next,projecreport_backto;
    TextView show_project_name;
    PieChart project_chart;
    RecyclerView project_recycle;
    private ArrayList<Cost2> list;
    private ArrayList<Post> postlist;
    private ArrayList<String> projectlist;
    private ArrayList<String> projectlistname;
    private int a=0;
    private RecyclerView recyclerView;
    CostAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_report);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        projecreport_backto=findViewById(R.id.projecreport_backto);
        project_chart=findViewById(R.id.project_chart);
        project_previous=findViewById(R.id.project_previous);
        project_next=findViewById(R.id.project_next);
        show_project_name=findViewById(R.id.show_project_name);
        project_recycle=findViewById(R.id.project_recycle);
        recyclerView=findViewById(R.id.project_recycle);
        project_chart.setUsePercentValues(true);
        project_chart.getDescription().setEnabled(false);

        project_chart.setUsePercentValues(true);
        project_chart.getDescription().setEnabled(false);
        list = new ArrayList<>();
        postlist=new ArrayList<>();
        projectlist=new ArrayList<>();
        projectlistname=new ArrayList<>();
        project_chart.setDragDecelerationFrictionCoef(0.95f);
        project_chart.setDrawHoleEnabled(true);
        project_chart.setHighlightPerTapEnabled(true);
        project_chart.setHoleColor(Color.WHITE);
        project_chart.setTransparentCircleRadius(61f);
        project_chart.setRotationEnabled(false);
        project_chart.setDrawEntryLabels(false);
        project_chart.animateY(500, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果
        project_chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        project_chart.getLegend().setWordWrapEnabled(true);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Project");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Project project=snapshot.getValue(Project.class);
                    if (project.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        projectlist.add(project.getProject_id());
                        projectlistname.add(project.getName());
                    }
                }
                readpost();
                show_project_name.setText(projectlistname.get(a));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        projecreport_backto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ProjectReportActivity.this,ProjectPage.class);
                startActivity(intent);
            }
        });

        project_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a==0){
                    a=projectlist.size()-1;
                }else {
                    a--;
                }
                show_project_name.setText(projectlistname.get(a));

                list = new ArrayList<>();
                postlist=new ArrayList<>();
                readpost();
                project_chart.setUsePercentValues(true);
                project_chart.getDescription().setEnabled(false);

                project_chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                project_chart.getLegend().setWordWrapEnabled(true);
                project_chart.setDragDecelerationFrictionCoef(0.95f);
                project_chart.setDrawHoleEnabled(true);
                project_chart.setHighlightPerTapEnabled(true);
                project_chart.setHoleColor(Color.WHITE);
                project_chart.setTransparentCircleRadius(61f);
                project_chart.setRotationEnabled(false);
                project_chart.setDrawEntryLabels(false);
                project_chart.animateY(500, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

            }
        });
        project_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a==projectlist.size()-1){
                    a=0;
                }else {
                    a++;
                }
                show_project_name.setText(projectlistname.get(a));

                list = new ArrayList<>();
                postlist=new ArrayList<>();
                readpost();
                project_chart.setUsePercentValues(true);
                project_chart.getDescription().setEnabled(false);

                project_chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                project_chart.getLegend().setWordWrapEnabled(true);
                project_chart.setDragDecelerationFrictionCoef(0.95f);
                project_chart.setDrawHoleEnabled(true);
                project_chart.setHighlightPerTapEnabled(true);
                project_chart.setHoleColor(Color.WHITE);
                project_chart.setTransparentCircleRadius(61f);
                project_chart.setRotationEnabled(false);
                project_chart.setDrawEntryLabels(false);
                project_chart.animateY(500, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果

            }
        });

    }

    private void readpost() {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if (snapshot.hasChild("project")){
                        Post post=snapshot.getValue(Post.class);
                        if (post.getProject().equals(projectlist.get(a))){
                            postlist.add(post);
                        }
                    }

                }
                getcount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getcount() {
        final ArrayList<PieEntry> Values=new ArrayList<>();
        int eatCost=0;
        int trafficCost=0;
        int playCost=0;
        int shopCost=0;
        int familyCost=0;
        int personalCost=0;
        int liveCost=0;
        int doctorCost=0;
        int learnCost=0;
        int otherCost=0;
        for (Post post:postlist){
            String type=post.getTypeMom();
            switch(type) {
                case "飲食":
                    eatCost+=Integer.valueOf(post.getCost());
                    break;
                case "交通":
                    trafficCost+=Integer.valueOf(post.getCost());
                    break;
                case"娛樂":
                    playCost+=Integer.valueOf(post.getCost());
                    break;
                case "購物":
                    shopCost+=Integer.valueOf(post.getCost());
                    break;
                case"家具":
                    familyCost+=Integer.valueOf(post.getCost());
                    break;
                case"個人":
                    personalCost+=Integer.valueOf(post.getCost());
                    break;
                case"生活休閒":
                    liveCost+=Integer.valueOf(post.getCost());
                    break;
                case"醫療":
                    doctorCost+=Integer.valueOf(post.getCost());
                    break;
                case"學習":
                    learnCost+=Integer.valueOf(post.getCost());
                    break;
                case"其他支出":
                    otherCost+=Integer.valueOf(post.getCost());
                    break;
            }

        }

        Values.add(new PieEntry(eatCost,"飲食"));
        Values.add(new PieEntry(trafficCost,"交通"));
        Values.add(new PieEntry(playCost,"娛樂"));
        Values.add(new PieEntry(shopCost,"購物"));
        Values.add(new PieEntry(familyCost,"家居"));
        Values.add(new PieEntry(personalCost,"個人"));
        Values.add(new PieEntry(liveCost,"生活休閒"));
        Values.add(new PieEntry(doctorCost,"醫療"));
        Values.add(new PieEntry(learnCost,"學習"));
        Values.add(new PieEntry(otherCost,"其他支出"));
        list.add(new Cost2(String.valueOf(eatCost), "飲食"));
        list.add(new Cost2(String.valueOf(trafficCost), "交通"));
        list.add(new Cost2(String.valueOf(playCost), "娛樂"));
        list.add(new Cost2(String.valueOf(shopCost), "購物"));
        list.add(new Cost2(String.valueOf(familyCost), "家居"));
        list.add(new Cost2(String.valueOf(personalCost), "個人"));
        list.add(new Cost2(String.valueOf(liveCost), "生活休閒"));
        list.add(new Cost2(String.valueOf(doctorCost), "醫療"));
        list.add(new Cost2(String.valueOf(learnCost), "學習"));
        list.add(new Cost2(String.valueOf(otherCost), "其他支出"));
        setadapter();
        PieDataSet pieDataSet=new PieDataSet(Values,"");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);

        final int[] MY_COLORS = {getResources().getColor(R.color.r1),getResources().getColor(R.color.r2),getResources().getColor(R.color.r3),getResources().getColor(R.color.r4),getResources().getColor(R.color.r5),getResources().getColor(R.color.r6),getResources().getColor(R.color.r7),getResources().getColor(R.color.r8),getResources().getColor(R.color.r9),getResources().getColor(R.color.r10)};

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: MY_COLORS) colors.add(c);
        project_chart.setCenterText("");
        if((int)eatCost==(int)trafficCost&&(int)playCost==(int)shopCost&&(int)familyCost==(int)personalCost&&(int)liveCost==(int)doctorCost&&(int)learnCost==(int)otherCost&&(int)eatCost==(int)playCost&&(int)personalCost==(int)liveCost&&(int)shopCost==(int)familyCost&&(int)familyCost==(int)otherCost&&(int)eatCost==0){
            project_chart.setCenterText("沒有記帳紀錄");
        }
        project_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                DecimalFormat fomatter = new DecimalFormat("#,###");
                project_chart.setCenterText("NT$"+fomatter.format((int)h.getY()));
            }

            @Override
            public void onNothingSelected() {
                project_chart.setCenterText("");
            }
        });
        pieDataSet.setColors(colors);
        PieData data=new PieData(pieDataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        pieDataSet.setDrawValues(false);
        project_chart.setData(data);
    }
    public void setadapter(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProjectReportActivity.this));

        adapter = new CostAdapter2(ProjectReportActivity.this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new CostAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent=new Intent(ProjectReportActivity.this,report_category_show.class);
////                intent.putExtra("type",type.get(position));
////                intent.putExtra("month",month.get(position));
////                intent.putExtra("year",year.get(position));
////                intent.putExtra("type",type.get(position));
//                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

    }


}
