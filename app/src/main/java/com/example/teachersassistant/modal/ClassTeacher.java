package com.example.teachersassistant.modal;

public class ClassTeacher {
    String Email, FirstName, Gender, LastName, Phone, classStd, grade, studentID, teacher;

    public ClassTeacher() {}

    public ClassTeacher(String email, String firstName, String gender, String lastName, String phone, String classStd, String grade, String studentID, String teacher) {
        this.Email = email;
        this.FirstName = firstName;
        this.Gender = gender;
        this.LastName = lastName;
        this.Phone = phone;
        this.classStd = classStd;
        this.grade = grade;
        this.studentID = studentID;
        this.teacher = teacher;
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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getClassStd() {
        return classStd;
    }

    public void setClassStd(String classStd) {
        this.classStd = classStd;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
