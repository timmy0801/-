package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Project_AddNewProject extends AppCompatActivity {
    EditText set_project_name,project_note;
    Spinner Project_setFriend;
    SearchView Project_search;
    ArrayList<String> friendlist,namelist,dblist,idlist;
    ArrayAdapter<String> adapter;
    ChipGroup Project_chipgroup;
    ImageButton project_backtohome,addnewproject_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project__add_new_project);
        set_project_name=findViewById(R.id.set_project_name);
        Project_setFriend=findViewById(R.id.Project_setFriend);
        Project_search=findViewById(R.id.Project_search);
        Project_chipgroup=findViewById(R.id.Project_chipgroup);
        project_backtohome=findViewById(R.id.project_backtohome);
        addnewproject_btn=findViewById(R.id.addnewproject_btn);
        project_note=findViewById(R.id.project_note);
        friendlist=new ArrayList<>();
        namelist=new ArrayList<>();
        dblist=new ArrayList<>();
        idlist=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //取得好友id並取得名字放入Spinner中
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendlist.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    friendlist.add(snapshot.getKey());
                }
                getname(friendlist);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Spinner點擊事件
        Project_setFriend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String addFriend=(String) Project_setFriend.getSelectedItem();
                if (!addFriend.equals("好友")){
                    final Chip entryChip = getChip(Project_chipgroup, addFriend);
                    Project_chipgroup.addView(entryChip);
                    dblist.add(addFriend);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //回上頁
        project_backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Project_AddNewProject.this,HomePage.class);
                startActivity(intent);
            }
        });

        //創建專案
        addnewproject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(set_project_name.getText())){
                    if (dblist.size()!=0){
                        DatabaseReference DBgo=FirebaseDatabase.getInstance().getReference("Project");
                        String key=DBgo.push().getKey();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("project_id",key);
                        hashMap.put("name",set_project_name.getText().toString());
                        hashMap.put("user",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        if (!TextUtils.isEmpty(project_note.getText())){
                            hashMap.put("description",project_note.getText().toString());
                        }
                        DBgo.child(key).setValue(hashMap);
                        DatabaseReference nameref=FirebaseDatabase.getInstance().getReference("User");
                        nameref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 0; i < dblist.size(); i++) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        if (snapshot.child("ris_Username").getValue().toString().equals(dblist.get(i))) {
                                            idlist.add(snapshot.getKey());
                                        }
                                    }
                                    DBgo.child(key).child("friends").child(Integer.toString(i+1)).setValue(idlist.get(i));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        for (int k=0;k<idlist.size();k++){
//                            HashMap<String,Object> Friend_hashMap=new HashMap<>();
//                            String key2=DBgo.push().getKey();
//                            Friend_hashMap.put("project_id",key2);
//                            Friend_hashMap.put("name",set_project_name.getText().toString());
//                            Friend_hashMap.put("user",idlist.get(k));
//                            DBgo.child(key2).setValue(Friend_hashMap);
//                            for (int j=0;j<idlist.size();j++){
//                                if (idlist.get(j).equals(idlist.get(k))){
//                                    DBgo.child(key2).child("friends").child(Integer.toString(j+1)).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                }
//                                else {
//                                    DBgo.child(key2).child("friends").child(Integer.toString(j+1)).child(idlist.get(j));
//                                }
//                            }
//
//                        }
                        Intent intent=new Intent(Project_AddNewProject.this,ProjectPage.class);
                        startActivity(intent);
                    }
                    else {
                        DatabaseReference DBgo=FirebaseDatabase.getInstance().getReference("Project");
                        String key=DBgo.push().getKey();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("project_id",key);
                        hashMap.put("name",set_project_name.getText().toString());
                        hashMap.put("user",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        if (!TextUtils.isEmpty(project_note.getText())){
                            hashMap.put("description",project_note.getText().toString());
                        }
                        DBgo.child(key).setValue(hashMap);
                        Intent intent=new Intent(Project_AddNewProject.this,ProjectPage.class);
                        startActivity(intent);
                    }
                }
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Project_AddNewProject.this);
                    dialog.setTitle("小提醒");
                    dialog.setMessage("有資料尚未填寫");
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {}
                    });
                    dialog.show();
                }
            }
        });

    }

    private void getname(ArrayList<String> friendlist) {
        namelist.add("好友");
        for (int i=0;i<friendlist.size();i++){
            DatabaseReference nameref=FirebaseDatabase.getInstance().getReference("User").child(friendlist.get(i));
            nameref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name=dataSnapshot.child("ris_Username").getValue().toString();
                    namelist.add(name);
                    adapter=new ArrayAdapter<String>(Project_AddNewProject.this,android.R.layout.simple_spinner_dropdown_item,namelist);
                    Project_setFriend.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private Chip getChip( final ChipGroup entryChipGroup, final String text) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.my_chip));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(text);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryChipGroup.removeView(chip);
                for (int n=0;n<dblist.size();n++){
                    if (dblist.get(n).equals(text)){
                        dblist.remove(n);
                        n--;
                        break;
                    }
                }
            }
        });
        return chip;
    }

}
