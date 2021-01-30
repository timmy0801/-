package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class accounting_report extends AppCompatActivity {
    PieChart pieChart;
    DatabaseReference databaseReference,costData;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_report);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        auth=FirebaseAuth.getInstance();
        pieChart=findViewById(R.id.chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10,10,10,10);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        costData=databaseReference.child(auth.getCurrentUser().getUid()).child("record/2019/9");
        final ArrayList<PieEntry> Values=new ArrayList<>();

        costData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float eatCost=0;
                float clothCost=0;
                float liveCost=0;
                float walkCost=0;
                float teachCost=0;
                float happyCost=0;
                float otherCost=0;

                for (int i=1;i<=31;i++){
                    for (int j=1;j<=dataSnapshot.child(Integer.toString(i)).getChildrenCount();j++){


                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("食")){
                            eatCost=eatCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("衣")){
                            clothCost=clothCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("住")){
                            liveCost=liveCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("行")){
                            walkCost=walkCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("育")){
                            teachCost=teachCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("樂")){
                            happyCost=happyCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                        if (dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("typeMom").getValue().toString().equals("其他")){
                            otherCost=otherCost+Float.valueOf(dataSnapshot.child(Integer.toString(i)).child(Integer.toString(j)).child("cost").getValue().toString());
                        }
                    }
                }

                Values.add(new PieEntry(eatCost,"食"));
                Values.add(new PieEntry(clothCost,"衣"));
                Values.add(new PieEntry(liveCost,"住"));
                Values.add(new PieEntry(walkCost,"行"));
                Values.add(new PieEntry(teachCost,"育"));
                Values.add(new PieEntry(happyCost,"樂"));
                Values.add(new PieEntry(otherCost,"其他"));
                PieDataSet pieDataSet=new PieDataSet(Values,"種類");
                pieDataSet.setSliceSpace(3f);
                pieDataSet.setSelectionShift(10f);
                final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                        Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
                ArrayList<Integer> colors = new ArrayList<Integer>();

                for(int c: MY_COLORS) colors.add(c);

                pieDataSet.setColors(colors);
                PieData data=new PieData(pieDataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);
                pieChart.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
