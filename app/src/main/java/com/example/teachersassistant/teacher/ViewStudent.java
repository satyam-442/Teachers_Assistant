package com.example.teachersassistant.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teachersassistant.R;

public class ViewStudent extends AppCompatActivity {

    String teacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        teacherID = getIntent().getExtras().get("teacherID").toString();

    }

    public void goToViewRequest(View view) {
        Intent intent = new Intent(this,ViewStudentsRequest.class);
        intent.putExtra("teacherID",teacherID);
        startActivity(intent);
    }
}