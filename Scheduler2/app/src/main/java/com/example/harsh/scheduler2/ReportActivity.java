package com.example.harsh.scheduler2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {
    ImageView camera, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        camera = (ImageView) findViewById(R.id.imageViewcamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(ReportActivity.this, CameraActivity.class);
                startActivity(r);
            }
        });

        location = (ImageView) findViewById(R.id.imageViewlocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(ReportActivity.this, MapsActivity.class);
                startActivity(r);
            }
        });
        Button sendBtn = (Button) findViewById(R.id.btnSendSMS);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String ("+254714051488"));
       //smsIntent.putExtra("sms_body"  , "testing");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReportActivity.this,
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

}
