package com.example.teachersassistant.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teachersassistant.R;
import com.example.teachersassistant.teacher.AddTeachers;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class TeachersLogin extends AppCompatActivity {

    TextInputEditText teacherId;
    FirebaseAuth mAuth;
    DatabaseReference teacherRef;
    Button authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_login);

        teacherId = findViewById(R.id.teacherId);
        authenticate = findViewById(R.id.authenticateBtn);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mAuth = FirebaseAuth.getInstance();
        teacherRef = FirebaseDatabase.getInstance().getReference().child("admin");

    }
}