package com.example.juuuuuuuuu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class Currency_setting extends AppCompatActivity {
    String currency;
    ImageButton set;
    ImageButton backhome;
    int cset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_setting);
        ScrollChoice scrollChoice = (ScrollChoice) findViewById(R.id.scroll_choice);
        backhome=findViewById(R.id.currency_backtohome);
        set=findViewById(R.id.set);
        List<String> data = new ArrayList<>();
        data.add("新台幣 TWD");
        data.add("美元 USD");
        data.add("港幣 HKD");
        data.add("英鎊 GBP");
        data.add("澳幣 AUD");
        data.add("加拿大幣 CAD");
        data.add("新加坡幣 SGD");
        data.add("瑞士法郎 CHF");
        data.add("日幣 JPY");
        data.add("南非幣 ZAR");
        data.add("瑞典克郎 SEK");
        data.add("紐西蘭幣 NZD");
        data.add("泰銖 THB");
        data.add("菲律賓比索 PHP");
        data.add("印尼盧比 IDR");
        data.add("歐元 EUR");
        data.add("韓元 KRW");
        data.add("越南盾 VND");
        data.add("令吉 MYR");
        data.add("人民幣 CNY");
        scrollChoice.addItems(data,1);
        DatabaseReference a=FirebaseDatabase.getInstance().getReference("CurrencyPolicy");
        a.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue()!=null){
                    String choose=dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue().toString();
                    scrollChoice.setSelectedItemPosition(Integer.valueOf(choose));
                }else {
                    scrollChoice.setSelectedItemPosition(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        scrollChoice.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                // currency=name;
                cset=position;
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CurrencyPolicy");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(cset);
                Intent home = new Intent(Currency_setting.this,HomePage.class);
                Toast.makeText(Currency_setting.this,"設定完成",Toast.LENGTH_LONG).show();
                startActivity(home);
            }
        });
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Currency_setting.this,HomePage.class);
                startActivity(home);
            }
        });

    }
}
