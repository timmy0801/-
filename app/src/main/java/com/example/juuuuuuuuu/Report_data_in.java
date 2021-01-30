package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.marcohc.robotocalendar.RobotoCalendarView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Report_data_in extends Fragment {
    PieChart pieChart;
    DatabaseReference databaseReference,costData;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    CostAdapter2 adapter;
    ImageButton previous,next;
    TextView showmonth;
    private ArrayList<Cost2> list;
    private ArrayList<String> month;
    private ArrayList<String> year;
    private ArrayList<String> type;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View data_out= inflater.inflate(R.layout.activity_report_data_in, container, false);


        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        auth=FirebaseAuth.getInstance();
        pieChart=(PieChart) data_out.findViewById(R.id.chart);
        previous=(ImageButton)data_out.findViewById(R.id.previous);
        next=(ImageButton)data_out.findViewById(R.id.next);
        showmonth=(TextView)data_out.findViewById(R.id.showmonth);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setRotationEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateY(500, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果
        recyclerView=(RecyclerView)data_out.findViewById(R.id.data_recycle);
        pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        pieChart.getLegend().setWordWrapEnabled(true);
        list = new ArrayList<>();
        month=new ArrayList<>();
        year=new ArrayList<>();
        type=new ArrayList<>();

        final ArrayList<PieEntry> Values=new ArrayList<>();

        SimpleDateFormat df=new SimpleDateFormat("MMMM,yyyy");
        SimpleDateFormat df1=new SimpleDateFormat("MM");
        Calendar cal=Calendar.getInstance();

        cal.setTime(cal.getTime());

        Date date=cal.getTime();
        showmonth.setText(df.format(date));
        String line=showmonth.getText().toString();
        String[] date1=line.split(",");
        costData=databaseReference.child(auth.getCurrentUser().getUid()).child("income").child(date1[1]).child(String.valueOf(df1.format(date)));
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PieEntry> Values=new ArrayList<>();
                list = new ArrayList<>();
                month=new ArrayList<>();
                year=new ArrayList<>();
                type=new ArrayList<>();

                cal.add(Calendar.MONTH, -1);
                Date date=cal.getTime();
                showmonth.setText(df.format(date));
                String line=showmonth.getText().toString();
                String[] date1=line.split(",");

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);

                pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

                pieChart.getLegend().setWordWrapEnabled(true);
                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHighlightPerTapEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setRotationEnabled(false);
                pieChart.setDrawEntryLabels(false);
                pieChart.animateY(500, Easing.EasingOption.EaseInOutQuad);
                costData=databaseReference.child(auth.getCurrentUser().getUid()).child("income").child(date1[1]).child(String.valueOf(df1.format(date)));
                costData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        float work=0;
                        float other=0;


                        for (int i=1;i<=31;i++){
                            for (int j=1;j<=dataSnapshot.child(Integer.toString(i)).getChildrenCount();j++){

                                if (dataSnapshot.child(Integer.toString(i)).getValue()==null){continue;}
                                else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("工作收入")){
                                    work=work+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                                }
                                else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("非工作收入")){
                                    other=other+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                                }
                            }
                        }
                        list.add(new Cost2(String.valueOf((int)work), "工作收入"));
                        type.add("工作收入");
                        year.add(date1[1]);
                        month.add(df1.format(date));
                        list.add(new Cost2(String.valueOf((int)other), "非工作收入"));
                        type.add("非工作收入");
                        year.add(date1[1]);
                        month.add(df1.format(date));


//                type.add();
                        setadapter();
                        Values.add(new PieEntry(work,"工作收入"));
                        Values.add(new PieEntry(other,"非工作收入"));

                        PieDataSet pieDataSet=new PieDataSet(Values,"");
                        pieDataSet.setSliceSpace(3f);
                        pieDataSet.setSelectionShift(10f);
                        final int[] MY_COLORS = {getResources().getColor(R.color.r1),getResources().getColor(R.color.r2),getResources().getColor(R.color.r3),getResources().getColor(R.color.r4),getResources().getColor(R.color.r5),getResources().getColor(R.color.r6),getResources().getColor(R.color.r7),getResources().getColor(R.color.r8),getResources().getColor(R.color.r9),getResources().getColor(R.color.r10)};
                        ArrayList<Integer> colors = new ArrayList<Integer>();

                        for(int c: MY_COLORS) colors.add(c);
                        pieChart.setCenterText("");
                        if((int)work==(int)other&&(int)work==0){
                            pieChart.setCenterText("沒有記帳紀錄");
                        }
                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                DecimalFormat fomatter = new DecimalFormat("#,###");
                                pieChart.setCenterText("NT$"+fomatter.format((int)h.getY()));
                            }
                            @Override
                            public void onNothingSelected() {
                                pieChart.setCenterText("");
                            }
                        });
                        pieDataSet.setColors(colors);
                        PieData data=new PieData(pieDataSet);
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.BLACK);
                        pieDataSet.setDrawValues(false);
                        pieChart.setData(data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<PieEntry> Values=new ArrayList<>();
                list = new ArrayList<>();
                month=new ArrayList<>();
                year=new ArrayList<>();
                type=new ArrayList<>();
                cal.add(Calendar.MONTH, 1);
                Date date=cal.getTime();
                showmonth.setText(df.format(date));
                String line=showmonth.getText().toString();
                String[] date1=line.split(",");

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);

                pieChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

                pieChart.getLegend().setWordWrapEnabled(true);
                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHighlightPerTapEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setRotationEnabled(false);
                pieChart.setDrawEntryLabels(false);
                pieChart.animateY(500, Easing.EasingOption.EaseInOutQuad);
                costData=databaseReference.child(auth.getCurrentUser().getUid()).child("income").child(date1[1]).child(String.valueOf(df1.format(date)));
                costData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        float work=0;
                        float other=0;


                        for (int i=1;i<=31;i++){
                            for (int j=1;j<=dataSnapshot.child(Integer.toString(i)).getChildrenCount();j++){

                                if (dataSnapshot.child(Integer.toString(i)).getValue()==null){continue;}
                                else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("工作收入")){
                                    work=work+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                                }
                                else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("非工作收入")){
                                    other=other+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                                }

                            }
                        }
                        list.add(new Cost2(String.valueOf((int)work), "工作收入"));
                        type.add("工作收入");
                        year.add(date1[1]);
                        month.add(df1.format(date));
                        list.add(new Cost2(String.valueOf((int)other), "非工作收入"));
                        type.add("非工作收入");
                        year.add(date1[1]);
                        month.add(df1.format(date));


//                type.add();
                        setadapter();
                        Values.add(new PieEntry(work,"工作收入"));
                        Values.add(new PieEntry(other,"非工作收入"));

                        PieDataSet pieDataSet=new PieDataSet(Values,"");
                        pieDataSet.setSliceSpace(3f);
                        pieDataSet.setSelectionShift(10f);
                        final int[] MY_COLORS = {getResources().getColor(R.color.r1),getResources().getColor(R.color.r2),getResources().getColor(R.color.r3),getResources().getColor(R.color.r4),getResources().getColor(R.color.r5),getResources().getColor(R.color.r6),getResources().getColor(R.color.r7),getResources().getColor(R.color.r8),getResources().getColor(R.color.r9),getResources().getColor(R.color.r10)};

                        ArrayList<Integer> colors = new ArrayList<Integer>();

                        for(int c: MY_COLORS) colors.add(c);
                        pieChart.setCenterText("");
                        if((int)work==(int)other&&(int)work==0){
                            pieChart.setCenterText("沒有記帳紀錄");
                        }
                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                DecimalFormat fomatter = new DecimalFormat("#,###");
                                pieChart.setCenterText("NT$"+fomatter.format((int)h.getY()));
                            }
                            @Override
                            public void onNothingSelected() {
                                pieChart.setCenterText("");
                            }
                        });
                        pieDataSet.setColors(colors);
                        PieData data=new PieData(pieDataSet);
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.BLACK);
                        pieDataSet.setDrawValues(false);
                        pieChart.setData(data);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        costData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float work=0;
                float other=0;


                for (int i=1;i<=31;i++){
                    for (int j=1;j<=dataSnapshot.child(Integer.toString(i)).getChildrenCount();j++){

                        if (dataSnapshot.child(Integer.toString(i)).getValue()==null){continue;}
                        else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("工作收入")){
                            work=work+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                        }
                        else if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("非工作收入")){
                            other=other+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("income").getValue().toString());
                        }

                    }
                }
                list.add(new Cost2(String.valueOf((int)work), "工作收入"));
                type.add("工作收入");
                year.add(date1[1]);
                month.add(df1.format(date));
                list.add(new Cost2(String.valueOf((int)other), "非工作收入"));
                type.add("非工作收入");
                year.add(date1[1]);
                month.add(df1.format(date));


//                type.add();
                setadapter();
                Values.add(new PieEntry(work,"工作收入"));
                Values.add(new PieEntry(other,"非工作收入"));

                PieDataSet pieDataSet=new PieDataSet(Values,"");
                pieDataSet.setSliceSpace(3f);
                pieDataSet.setSelectionShift(10f);
                final int[] MY_COLORS = {getResources().getColor(R.color.r1),getResources().getColor(R.color.r2),getResources().getColor(R.color.r3),getResources().getColor(R.color.r4),getResources().getColor(R.color.r5),getResources().getColor(R.color.r6),getResources().getColor(R.color.r7),getResources().getColor(R.color.r8),getResources().getColor(R.color.r9),getResources().getColor(R.color.r10)};

                ArrayList<Integer> colors = new ArrayList<Integer>();

                for(int c: MY_COLORS) colors.add(c);
                pieChart.setCenterText("");
                if((int)work==(int)other&&(int)work==0){
                    pieChart.setCenterText("沒有記帳紀錄");
                }
                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        DecimalFormat fomatter = new DecimalFormat("#,###");
                        pieChart.setCenterText("NT$"+fomatter.format((int)h.getY()));
                    }
                    @Override
                    public void onNothingSelected() {
                        pieChart.setCenterText("");
                    }
                });
                pieDataSet.setColors(colors);
                PieData data=new PieData(pieDataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.BLACK);
                pieDataSet.setDrawValues(false);
                pieChart.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return data_out;
    }
    public void setadapter(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CostAdapter2(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new CostAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(),report_category_show.class);
//                intent.putExtra("type",type.get(position));
                intent.putExtra("month",month.get(position));
                intent.putExtra("year",year.get(position));
                intent.putExtra("type",type.get(position));
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

    }

}
