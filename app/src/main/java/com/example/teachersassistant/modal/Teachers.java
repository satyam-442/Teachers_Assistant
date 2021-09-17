package com.example.teachersassistant.modal;

public class Teachers {
    String Email, Gender, Name, Password, Phone, classTeacher, image, subjectTeacher, teacherID;

    public Teachers() {
    }

    public Teachers(String email, String gender, String name, String password, String phone, String classTeacher, String image, String subjectTeacher, String teacherID) {
        Email = email;
        Gender = gender;
        Name = name;
        Password = password;
        Phone = phone;
        this.classTeacher = classTeacher;
        this.image = image;
        this.subjectTeacher = subjectTeacher;
        this.teacherID = teacherID;
    }

    public String getEmaill() {
        return Email;
    }

    public void setEmaill(String email) {
        Email = email;
    }

    public String getGenderr() {
        return Gender;
    }

    public void setGenderr(String gender) {
        Gender = gender;
    }

    public String getNamee() {
        return Name;
    }

    public void setNamee(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPasswordd(String password) {
        Password = password;
    }

    public String getPhonee() {
        return Phone;
    }

    public void setPhonee(String phone) {
        Phone = phone;
    }

    public String getClassTeacherr() {
        return classTeacher;
    }

    public void setClassTeacherr(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getImagee() {
        return image;
    }

    public void setImagee(String image) {
        this.image = image;
    }

    public String getSubjectTeacherr() {
        return subjectTeacher;
    }

    public void setSubjectTeacherr(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherIDD(String teacherID) {
        this.teacherID = teacherID;
    }
}
