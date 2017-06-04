package com.example.sheliza.grid_nav.AttendanceFingerPrint;

/**
 * Created by Sheliza on 18-05-2017.
 */

public class AttendanceBean {
    String date;
    String attendance;
    String location;
    String name;

    public AttendanceBean(String name, String email, String location, String date, String attendance) {
    }

    public AttendanceBean(String date, String attendance, String location, String name) {
        this.date = date;
        this.attendance = attendance;
        this.location = location;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}