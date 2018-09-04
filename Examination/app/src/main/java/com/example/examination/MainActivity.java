package com.example.examination;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.content.Context.*;
import android.widget.Toast;

import com.example.examination.logics.*;

public class MainActivity extends Activity {
    private Student this_student = new Student();

    private Button submit;
    private EditText student_name, student_id, student_phone;
    private TextView input_errors;
    private boolean has_errors = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        submit = (Button) findViewById(R.id.submit);
        student_name = (EditText) findViewById(R.id.name_edittext);
        student_id = (EditText) findViewById(R.id.id_edittext);
        student_phone = (EditText) findViewById(R.id.phone_edittext);
        input_errors = (TextView) findViewById(R.id.input_errors);

    }

    public void submit(View v){
        Toast.makeText(getApplicationContext() , "processing credential submission", Toast.LENGTH_LONG);
        input_errors.setText("");
        has_errors = false;

        String name = student_name.getText().toString();
        String id = student_id.getText().toString();
        String phone = student_phone.getText().toString();

        if(name == "" || name.length() < 2){
           has_errors = true;
        }
        if(id == "" || id.length() < 2){
            has_errors = true;
        }
        if(phone == "" || phone.length() < 2){
            has_errors = true;
        }

        if(has_errors){
            input_errors.setText("Check Your Student Credentials");
        }else{
            // save it as session data
            this_student.setName(name);
            this_student.setId(id);

            // save it to session then proceed
            Intent qn_sheet = new Intent(getApplicationContext(), Questions.class);
            qn_sheet.putExtra("student_name", name);
            qn_sheet.putExtra("student_id", id);
            qn_sheet.putExtra("student_phone", phone);

            startActivity(qn_sheet);

        }
    }


}
