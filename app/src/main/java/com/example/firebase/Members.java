package com.example.firebase;

public class Members {
    private String name;
    private String Location;
    private String Date_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }*/

    public Members() {
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate_time() {
        return Date_time;
    }

    public void setDate_time(String date_time) {
        Date_time = date_time;
    }
}
