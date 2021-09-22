package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.auth.StudentLogin;
import com.example.teachersassistant.auth.TeachersLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TeacherMain extends AppCompatActivity {

    String teacherID;
    ImageView logout;
    DatabaseReference teacherRef, classTeacherRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        loadingBar = new ProgressDialog(this);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(teacherID);
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String classStatus = snapshot.child("classTeacher").getValue().toString();
                    if (classStatus.equals("T")){
                        Intent intent = new Intent(TeacherMain.this,ClassActivity.class);
                        intent.putExtra("ID",teacherID);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        classTeacherRef = FirebaseDatabase.getInstance().getReference().child("ClassTeacher");

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                
                finish();
            }
        });
    }

    public void goToStudent(View view) {
        Intent intent = new Intent(this,ViewStudent.class);
        intent.putExtra("teacherID",teacherID);
        startActivity(intent);
    }
}