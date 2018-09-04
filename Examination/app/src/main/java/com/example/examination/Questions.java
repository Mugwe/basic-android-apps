package com.example.examination;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.view.View;
import android.telephony.*;

import com.example.examination.logics.*;
import com.example.examination.logics.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.List;


public class Questions extends AppCompatActivity {
    private String student_name = "";
    private String student_id = "";
    private String student_phone = "";

    private TextView student_details, what_qn, question_end, qn_string, qn_answer, student_score;
    private RadioButton rd1, rd2, rd3, rd4;
    private RadioButton rds[];
    private Button prev_qn, next_qn, submit;

    // setup exam
    int qn_count = 0;
    int current_qn_index = 0;
    Question current_qn;
    Exam exam = new Exam();
    Exam answered = new Exam();
    Student this_student =  new Student();

    Question questions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        prev_qn = (Button) findViewById(R.id.prev_qn);
        next_qn = (Button) findViewById(R.id.next_qn);
        submit = (Button) findViewById(R.id.submit);
        student_details = (TextView) findViewById(R.id.student_details);
        what_qn = (TextView) findViewById(R.id.what_qn);
        question_end = (TextView) findViewById(R.id.question_end);
        qn_string = (TextView) findViewById(R.id.qn_string);
        qn_answer = (TextView) findViewById(R.id.qn_answer);
        student_score = (TextView) findViewById(R.id.student_score);

        rd1 = (RadioButton) findViewById(R.id.ans1);
        rd2 = (RadioButton) findViewById(R.id.ans2);
        rd3 = (RadioButton) findViewById(R.id.ans3);
        rd4 = (RadioButton) findViewById(R.id.ans4);

        List<RadioButton> rdss = new ArrayList<>();
        rdss.add(rd1);
        rdss.add(rd2);
        rdss.add(rd3);
        rdss.add(rd4);

        rds = rdss.toArray(new RadioButton[rdss.size()]);

        prev_qn.setEnabled(false);
        submit.setVisibility(View.GONE);


        // create qns
        Question one = new Question();
        one.setQn("What is the capital city of Kenya")
           .addAnswer("A. Mombasa").addAnswer("B. Nairobi").addAnswer("C. Kisumu").addAnswer("D. Makueni")
                .setCorrectAnswer("B. Nairobi");

        Question two = new Question();
        two.setQn("What is the tallest Mountain")
                .addAnswer("A. Kilimanjaro").addAnswer("B. Kenya").addAnswer("C. Elgon").addAnswer("D. Kisigo")
                .setCorrectAnswer("A. Kilimanjaro");

        Question three = new Question();
        three.setQn("What is the best Day of the week")
                .addAnswer("A. Tuesday").addAnswer("B. Wednesday").addAnswer("C. None").addAnswer("D. Saturday")
                .setCorrectAnswer("D. Saturday");

        Question four = new Question();
        four.setQn("What is the long form of HD")
                .addAnswer("A. Hi Dudes").addAnswer("B. Highly Dim").addAnswer("C. High Definition").addAnswer("D. Makueni")
                .setCorrectAnswer("C. High Definition");

        exam.addQuestion(one).addQuestion(two).addQuestion(three).addQuestion(four);

        Bundle extras = getIntent().getExtras();
        //Toast.makeText(getApplicationContext(), "Welcome "+ extras.getString("student_name"), Toast.LENGTH_LONG).show();

        qn_count = exam.getQuestions().size();
        questions = exam.getQuestions().toArray(new Question[qn_count]); // addAll(exam.getQuestions());


        // set students data
        student_name = extras.getString("student_name");
        student_id = extras.getString("student_id");
        student_phone = extras.getString("student_phone");

        this_student.setId(student_id).setName(student_name).setPhone(student_phone);

        student_details.setText("Name: "+ student_name.toUpperCase() +"    id: "+ student_id);


        if(qn_count == 0){
            question_end.setText("There Are No Questions");
            prev_qn.setEnabled(false);
            next_qn.setEnabled(false);
        }else{
            what_qn.setText("question :"+ (current_qn_index+1) +"/"+ qn_count );

            Question qn = questions[current_qn_index];
            Toast.makeText(getApplicationContext(), "qn count "+ qn_count + " cindex "+ current_qn_index , Toast.LENGTH_LONG).show();
            qn_string.setText(qn.getQn());
            List<String> choizes = qn.getAnswers();
            String choices[] = choizes.toArray(new String[choizes.size()]);

            // show choices
            rd1.setText(choices[0]);
            rd2.setText(choices[1]);
            rd3.setText(choices[2]);
            rd4.setText(choices[3]);

            qn_answer.setText( questions[current_qn_index].getUser_answer() );

        }



    }



    // on click handlers

    public void go_home(View v){
        // save it to session then proceed
        Intent home = new Intent(getApplicationContext(), Questions.class);
        home.putExtra("student_name", student_name);
        home.putExtra("student_id", student_id);
        home.putExtra("exam", new ArrayList<Exam>().add(exam));

        startActivity(home);
    }

    public void go_prev(View v){
        if(current_qn_index > 0){
            current_qn_index = current_qn_index-1;
            Toast.makeText(getApplicationContext(), "qn count "+ qn_count + " cindex "+ current_qn_index , Toast.LENGTH_LONG).show();

            v.setEnabled(true);
            //prev_qn.setEnabled(false);

            Question qn = questions[current_qn_index];
            qn_string.setText(qn.getQn());
            List<String> choizes = qn.getAnswers();
            String choices[] = choizes.toArray(new String[choizes.size()]);

            int qnc = 0;
            // show choices
            rd1.setText(choices[0]);
            rd2.setText(choices[1]);
            rd3.setText(choices[2]);
            rd4.setText(choices[3]);

            for(RadioButton rd: rds){
                if(questions[current_qn_index].getAnswers().contains(questions[current_qn_index].getUser_answer()) && questions[current_qn_index].getUser_answer() == rd.getText().toString() ){
                   rd.setSelected(true);
                }
            }

            qn_answer.setText( questions[current_qn_index].getUser_answer() );

            what_qn.setText("question: "+ (current_qn_index+1) +"/"+ qn_count );

        }else{
            v.setEnabled(false);
            next_qn.setEnabled(true);
        }
        qn_answer.setText("");
        submit.setVisibility(View.GONE);
    }

    public void go_next(View v) {
            current_qn_index = current_qn_index+1;

            if (current_qn_index == qn_count) {
                v.setEnabled(false);
                prev_qn.setEnabled(true);

                submit.setVisibility(View.VISIBLE);
                submit.setEnabled(true);

                return;
            }

            Toast.makeText(getApplicationContext(), "qn count "+ qn_count + " cindex "+ current_qn_index , Toast.LENGTH_LONG).show();

            Question qn = questions[current_qn_index];
            qn_string.setText(qn.getQn());
            List<String> choizes = qn.getAnswers();
            String choices[] = choizes.toArray(new String[choizes.size()]);

            int qnc = 0;
            // show choices
            rd1.setText(choices[0]);
            rd2.setText(choices[1]);
            rd3.setText(choices[2]);
            rd4.setText(choices[3]);

            for(RadioButton rd: rds){
              if(questions[current_qn_index].getAnswers().contains(questions[current_qn_index].getUser_answer()) && questions[current_qn_index].getUser_answer() == rd.getText().toString() ){
                 rd.setSelected(true);
              }
            }

            qn_answer.setText( questions[current_qn_index].getUser_answer() );

            what_qn.setText("question: "+ (current_qn_index+1) +"/"+ qn_count );

            prev_qn.setEnabled(true);

        qn_answer.setText("");
    }


    public void radio_handle(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String ans = "";

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ans1:
                if (checked)
                    ans = ((RadioButton) view).getText().toString();
                    break;
            case R.id.ans2:
                if (checked)
                    ans = ((RadioButton) view).getText().toString();
                    break;
            case R.id.ans3:
                if (checked)
                    ans = ((RadioButton) view).getText().toString();
                    break;
            case R.id.ans4:
                if (checked)
                    ans = ((RadioButton) view).getText().toString();
                    break;
            default:
                   break;
        }

        questions[current_qn_index].setUserAnswer(ans);
        exam.replaceQuestion(questions[current_qn_index]);
        qn_answer.setText("Answer :"+ ans);


    }


    public void submit_exam(View v){
        double score = exam.calculateScore().getScore();
        Toast.makeText(getApplicationContext(), "you did "+ exam.getQuestions().size() +" questions you got "+ score, Toast.LENGTH_LONG).show();
        student_score.setText("you did "+ exam.getQuestions().size() +" questions you got "+ score);

        String message = "Name: "+ this_student.getName() +" id: "+ this_student.getId() +" you did "+ exam.getQuestions().size() +" questions and scored "+ score;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(this_student.getPhone(), "0799919960", message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
        }


    }




}
