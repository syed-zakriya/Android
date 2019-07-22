package com.rxjava.rxsample6;

import java.util.ArrayList;

public class Person {
    int id;
    String firstName;
    String  lastName;
    int age;
    char sex;

    public Person(int id, String firstName, String lastName, int age, char sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public static ArrayList<Person> getPersonList(){
        ArrayList<Person> persons = new ArrayList<>();

        persons.add(new Person(31,"Syed","Zakriya",29,'M'));
        persons.add(new Person(32,"Chandan","Dwivedi",28,'M'));
        persons.add(new Person(33,"Sanjeeb","Guru",30,'M'));
        persons.add(new Person(34,"Vishwanath","Reddy",35,'M'));

        return persons;
    }
}
