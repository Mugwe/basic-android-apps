package com.example.harsh.scheduler2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView reports, issues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reports = (ImageView) findViewById(R.id.imageViewReport);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(r);
            }
        });

        issues = (ImageView) findViewById(R.id.imageView2);
        issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(MainActivity.this, IssuesActivity.class);
                startActivity(r);
            }
        });
    }
}