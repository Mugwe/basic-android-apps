package com.example.examination.logics;

/**
 * Created by efenstakes on 11/19/2017.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Exam {
    private List<Question> questions;
    private double score = 0;

    public Exam(){
        this.questions = new ArrayList<>();
    }

    // mutators
    public Exam setQuestions(List<Question> qns){
        this.questions = qns;
        return this;
    }
    public Exam addQuestion(Question qn){
        this.questions.add(qn);
        return this;
    }
    public Exam replaceQuestion(Question qn){
        // the qns we have
        List<Question> all_i_have = new ArrayList<>(this.getQuestions());
        this.questions.clear();

        for(Question q : all_i_have){
            if(q.getQn() == qn.getQn()){
                q.setUserAnswer(qn.getUser_answer());
            }
            this.questions.add(q);
        }
        return this;
    }
    public Exam setScore(double score){
        this.score = score;
        return this;
    }

    // accessors
    public List<Question> getQuestions(){
        return this.questions;
    }
    public double getScore(){
        return this.score;
    }

    // logic methods
    public Exam calculateScore(){
        // this.questions.stream().filter(x-> x.getCorrect_answer()==x.getUser_answer()).count();

        for (Question qn_n: questions){
            if(qn_n.getCorrect_answer() == qn_n.getUser_answer()){
                this.score++;
            }
        }

        return this;
    }


}
