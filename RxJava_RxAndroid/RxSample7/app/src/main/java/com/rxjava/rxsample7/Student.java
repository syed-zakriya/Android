package com.rxjava.rxsample7;

import java.util.ArrayList;

public class Student {
    int regNum;
    String name;
    int age;

    public Student(int regNum, String name, int age) {
        this.regNum = regNum;
        this.name = name;
        this.age = age;
    }

    public int getRegNum() {
        return regNum;
    }

    public void setRegNum(int regNum) {
        this.regNum = regNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "regNum=" + regNum +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static ArrayList<Student> getAllStudents(){
        ArrayList<Student> allStudents = new ArrayList<>();
        allStudents.add(new Student(30,"Syed",29));
        allStudents.add(new Student(29,"Chandan",28));
        allStudents.add(new Student(31,"Guru", 30));
        //allStudents.add(new Student(32,"Vishwanath", 35));
        //allStudents.add(new Student(33,"Vivek", 35));
        //allStudents.add(new Student(34,"Krishna", 35));
        //allStudents.add(new Student(35,"Sid", 32));
        //allStudents.add(new Student(36,"Shijin", 35));
        //allStudents.add(new Student(37,"PubG", 29));
        return allStudents;
    }
}
