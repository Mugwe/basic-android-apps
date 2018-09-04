package com.example.tmugwe.intent;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
        Button b1,b2,b3;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            b1=(Button)findViewById(R.id.button);

            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://www.usiu.ac.ke"));
                    startActivity(i);
                }
            });

            b2=(Button)findViewById(R.id.button2);
            b2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent("com.example.myapplication.LAUNCH",Uri.parse("http://www.usiu.ac.ke "));
                    startActivity(i);
                }
            });

            b3=(Button)findViewById(R.id.button3);
            b3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent("com.example.myapplication.LAUNCH",Uri.parse("https://www.usiu.ac.ke"));
                    startActivity(i);
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
           // getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.

            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
           // if (id == R.id.action_settings) {
             //   return true;
            //}
            return super.onOptionsItemSelected(item);
        }
    }

