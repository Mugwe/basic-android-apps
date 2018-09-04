package com.example.ajoshi.schedulertwo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username;
    EditText password;
    TextView signUp;
    ImageView splash;
    String uname, pword, query, resulta;
    HttpURLConnection con;
    ProgressDialog mProgressDialog;
    JSONObject json_data;

    public void splash (View view){

        ImageView splash = (ImageView)findViewById(R.id.splash);
        splash.animate().alpha(0).setDuration(2000);
        splash.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Intent myIntent = new Intent(LoginActivity.this, SignUp.class);


        login = (Button)findViewById(R.id.login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        signUp = (TextView)findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(myIntent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString();
                pword = password.getText().toString();

                String type = "login";
              /*  SQLiteConnect1 backgroundWorker = new SQLiteConnect1(LoginActivity.this);
                backgroundWorker.execute(type, uname, pword); */



               /* if (db.validation(uname, pword)){
                    Toast.makeText(LoginActivity.this, "Hurray!!", Toast.LENGTH_LONG).show();
                    Intent mynew = new Intent(LoginActivity.this,calenderView.class);
                    startActivity(mynew);
                }else{
                    Toast.makeText(LoginActivity.this, "Did not work!!", Toast.LENGTH_LONG).show();
                }*/


                final class Des extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected void onPreExecute() {


                        super.onPreExecute();

                        mProgressDialog = new ProgressDialog(LoginActivity.this);
                        mProgressDialog.setMessage("Verifying your account..A moment please");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("user_name", uname.trim())
                                .appendQueryParameter("password", pword.trim());
                        query = builder.build().getEncodedQuery();


                    }

                    @Override
                    protected Void doInBackground(Void... params) {


                        InputStream is = null;
                        try {

                            String url = "http://canny-intensities.000webhostapp.com/login.php";
                            URL obj = new URL(url);
                            con = (HttpURLConnection) obj.openConnection();
                            con.setRequestMethod("POST");
                            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
                            con.setRequestProperty("Accept-Language", "UTF-8");
                            con.setDoInput(true);
                            con.setDoOutput(true);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                            outputStreamWriter.write(query);
                            outputStreamWriter.flush();
                            Log.e("pass 1", "connection success ");
                        } catch (Exception e) {
                            Log.e("Fail 1", e.toString());

                        }


                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String line;
                            StringBuffer sb = new StringBuffer();

                            while ((line = in.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            //   is.close();
                            resulta = sb.toString();
                            Log.e("pass 2", "connection success ");
                        } catch (Exception e) {
                            Log.e("Fail 2", e.toString());
                        }
                        return null;
                    }


                    @Override
                    protected void onPostExecute(Void result) {


                        try {
                            json_data = new JSONObject(resulta);
                            int code = (json_data.getInt("code"));
                            if (code == 1) {

                                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(LoginActivity.this);
                                //LinearLayout lila1 = new LinearLayout(MainActivity.this);
                                //lila1.setOrientation(LinearLayout.VERTICAL);
                                //alert.setView(lila1);
                                //alert.setTitle("Success");

                                //alert.setMessage("Successful login");
                                // alert.setIcon(R.drawable.succ);
                                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                                // alert.show();
                                Toast.makeText(LoginActivity.this, "Hurray!!", Toast.LENGTH_LONG).show();
                                Intent mynew = new Intent(LoginActivity.this,Calendar.class);
                                startActivity(mynew);
                            }  else {
                                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(LoginActivity.this);
                                LinearLayout lila1 = new LinearLayout(LoginActivity.this);
                                lila1.setOrientation(LinearLayout.VERTICAL);
                                alert.setView(lila1);
                                alert.setTitle("Failed!");
                                alert.setMessage("Please make sure that you have put in correct username or password");
                                // alert.setIcon(R.drawable.e);
                                Log.e("Fail 3", "Value " + code);
                                alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }


                                });
                                alert.show();

                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this,"Failed", Toast.LENGTH_LONG).show();
                            Log.e("Fail 4", e.toString());

                        }

                        mProgressDialog.dismiss();


                    }





                }//end of else
                new Des().execute();


            }
        });

    }
}