package com.example.examination.logics;

/**
 * Created by efenstakes on 11/19/2017.
 */

public class Student {
    private String name="", id="", phone="";

    public Student(){
        this.name = "";
        this.id = "";
    }

    public Student(String nam, String id){
        this.name = nam;
        this.id = id;
    }

    // mutators
    public Student setName(String nam){
        this.name = nam;
        return this;
    }
    public Student setId(String id){
        this.id = id;
        return this;
    }
    public Student setPhone(String phone){
        this.phone = phone;
        return this;
    }

    // accessors
    public String getName(){
        return this.name;
    }
    public String getId(){
        return this.id;
    }
    public String getPhone(){
        return this.phone;
    }

}
