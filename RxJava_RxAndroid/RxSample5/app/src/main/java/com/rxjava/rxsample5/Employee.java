package com.rxjava.rxsample5;

import java.util.ArrayList;

public class Employee {
    String name;
    int id;
    int age;
    String address;

    public Employee(String name, int id, int age, String address) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> list = new ArrayList<>();

        list.add(new Employee("Syed",30,29,"Bangalore"));
        list.add(new Employee("Chandan",31,29,"Bangalore"));
        list.add(new Employee("Guru",32,29,"Bangalore"));
        list.add(new Employee("Vishwanath",33,35,"Bangalore"));

        return list;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
