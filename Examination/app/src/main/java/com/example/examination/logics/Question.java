package com.example.examination.logics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efenstakes on 11/19/2017.
 */

public class Question {
    private String question="";
    private String correct_answer, user_answer;
    private List<String> answers;

    public Question(){
        this.answers = new ArrayList<>();
    }

    // mutators
    public Question setQn(String qn){
        this.question = qn;
        return this;
    }
    public Question setCorrectAnswer(String ans){
        this.correct_answer = ans;
        return this;
    }
    public Question setUserAnswer(String ans){
        this.user_answer = ans;
        return this;
    }
    public Question setAnswers(List <String> anss){
        this.answers.addAll(anss);
        return this;
    }
    public Question addAnswer(String ans){
        this.answers.add(ans);
        return this;
    }

    // accessors
    public String getQn(){
        return this.question;
    }
    public String getCorrect_answer(){
        return this.correct_answer;
    }
    public String getUser_answer(){
        return this.user_answer;
    }
    public List<String> getAnswers(){
        return this.answers;
    }




}
