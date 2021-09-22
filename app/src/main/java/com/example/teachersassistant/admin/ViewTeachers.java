package com.example.teachersassistant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.teachersassistant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewTeachers extends AppCompatActivity {
    DatabaseReference allTeachersRef, classTeacherRef, subjectTeacherRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teachers);
        allTeachersRef = FirebaseDatabase.getInstance().getReference().child("AllTeachers");
        classTeacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("ClassTeacher");
        subjectTeacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("SubjectTeacher");
    }
}