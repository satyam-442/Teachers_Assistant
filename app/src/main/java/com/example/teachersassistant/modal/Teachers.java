package com.example.teachersassistant.modal;

public class Teachers {
    String Email, Gender, FirstName, LastName, Password, Phone, classTeacher, image, subjectTeacher, teacherID;

    public Teachers() {
    }

    public Teachers(String email, String gender, String firstName, String lastName, String password, String phone, String classTeacher, String image, String subjectTeacher, String teacherID) {
        Email = email;
        Gender = gender;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Phone = phone;
        this.classTeacher = classTeacher;
        this.image = image;
        this.subjectTeacher = subjectTeacher;
        this.teacherID = teacherID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
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

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }

    public void setSubjectTeacher(String subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
}
