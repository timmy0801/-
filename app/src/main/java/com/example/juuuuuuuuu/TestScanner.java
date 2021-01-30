package com.example.juuuuuuuuu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TestScanner extends AppCompatActivity {
    private TextView scan_textview;
    private Button scan_btn;
    private IntentIntegrator scanIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scanner);

        scan_textview=(TextView)findViewById(R.id.scan_txview);
    }
    public void onbuttonclick(View v)
    {
        View button1 = (View) findViewById(R.id.scan_btn);

        scanIntegrator = new IntentIntegrator(TestScanner.this);
        scanIntegrator.setPrompt("請掃描");
        scanIntegrator.setTimeout(300000);
        scanIntegrator.setOrientationLocked(false);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null)
        {
            if(scanningResult.getContents() != null)
            {
                String scanContent = scanningResult.getContents();
                if (!scanContent.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"掃描內容: "+scanContent.toString(), Toast.LENGTH_LONG).show();
                    String a;
                    a=scanContent.substring(89,scanContent.length());
                    scan_textview.setText(a);
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, intent);
            Toast.makeText(getApplicationContext(),"發生錯誤",Toast.LENGTH_LONG).show();
        }
    }
}

//        scan_btn = (Button)findViewById(R.id.scan_btn);
//        final Activity activity = this;
//        scan_btn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                IntentIntegrator integrator = new IntentIntegrator(activity);
//                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//                integrator.setPrompt("Scan");
//                integrator.setCameraId(0);
//                integrator.setBeepEnabled(false);
//                integrator.setBarcodeImageEnabled(false);
//                integrator.initiateScan();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//        if (result!= null)
//        {
//            if (result.getContents()==null)
//            {
//                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        else
//        {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//
//
//



