package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewStudent extends AppCompatActivity {

    String teacherID,classStd;
    DatabaseReference classStdRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        classStd = getIntent().getExtras().get("classStd").toString();
    }

    public void goToViewRequest(View view) {
        Intent intent = new Intent(this,ViewStudentsRequest.class);
        intent.putExtra("teacherID",teacherID);
        intent.putExtra("classStd",classStd);
        startActivity(intent);
    }
}