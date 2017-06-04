package com.example.sheliza.grid_nav;

import java.io.Serializable;

/**
 * Created by Sheliza on 20-05-2017.
 */

public class Teachersprofilebean implements Serializable {



        // Attributes
        private String name, email ,password ,designation ,address , contact, salary, date, gender;

    public Teachersprofilebean() {
    }

    public Teachersprofilebean(String name, String email, String password, String designation, String address, String contact, String salary, String date, String gender) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.designation = designation;
        this.address = address;
        this.contact = contact;
        this.salary = salary;
        this.date = date;
        this.gender = gender;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
