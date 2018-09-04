package com.example.harsh.scheduler2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button signup;
    EditText firstname;
    EditText lastname;
    EditText username;
    EditText password;
    TextView login;

    myDbHelper db;

    String uname, fname , lname, pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new myDbHelper(getApplicationContext());


        signup = (Button)findViewById(R.id.signUp);
        firstname = (EditText)findViewById(R.id.fname);
        lastname = (EditText)findViewById(R.id.lname);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (TextView)findViewById(R.id.login);

        final Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter correct Username or Password", Toast.LENGTH_LONG).show();
                }
                else{
                    uname = username.getText().toString();
                    fname = firstname.getText().toString();
                    lname = lastname.getText().toString();
                    pword = password.getText().toString();

                   /* db.addUser( fname, lname); */
                    db.addUser( uname, pword);
                    Toast.makeText(getApplicationContext(),"Sign up Successful", Toast.LENGTH_LONG).show();

                    finish();
                    startActivity(myIntent);


                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(myIntent);
            }
        });

    }
}
