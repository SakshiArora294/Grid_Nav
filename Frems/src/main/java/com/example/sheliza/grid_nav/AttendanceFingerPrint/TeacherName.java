package com.example.sheliza.grid_nav.AttendanceFingerPrint;

/**
 * Created by Sheliza on 19-05-2017.
 */

public class TeacherName {

    String name;
    String email;

    public TeacherName() {
    }

    public TeacherName(String name,String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "TeacherName{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
