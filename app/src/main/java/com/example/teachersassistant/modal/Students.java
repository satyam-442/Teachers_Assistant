package com.example.teachersassistant.modal;

import androidx.annotation.NonNull;

public class Students {
    String classStd, Email, FirstName, Gender, Grade, LastName, Password, Phone, StudentID;

    public Students() {
    }

    public Students(String classStd, String email, String firstName, String gender, String grade, String lastName, String password, String phone, String studentID) {
        this.classStd = classStd;
        Email = email;
        FirstName = firstName;
        Gender = gender;
        Grade = grade;
        LastName = lastName;
        Password = password;
        Phone = phone;
        StudentID = studentID;
    }

    public String getClassStd() {
        return classStd;
    }

    public void setClassStd(String classStd) {
        this.classStd = classStd;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
}
