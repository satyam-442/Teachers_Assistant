package com.example.teachersassistant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teachersassistant.R;

public class AdminMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
    }

    public void addTeacher(View view) {
        Intent intent = new Intent(this,AddTeachers.class);
        startActivity(intent);
    }

    public void viewTeacher(View view) {
        Intent intent = new Intent(this,ViewTeachers.class);
        startActivity(intent);
    }

    public void addLaboratory(View view) {
        Intent intent = new Intent(this,AddLaboratoryActivity.class);
        startActivity(intent);
    }
}