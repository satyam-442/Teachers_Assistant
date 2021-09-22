package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.WelcomeScreen;
import com.example.teachersassistant.auth.StudentLogin;
import com.example.teachersassistant.auth.TeachersLogin;
import com.example.teachersassistant.modal.Teachers;
import com.example.teachersassistant.prevalent.Prevalent;
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

import io.paperdb.Paper;

public class TeacherMain extends AppCompatActivity {

    String teacherID;
    ImageView logout;
    DatabaseReference teacherRef, classTeacherRef;
    ProgressDialog loadingBar;
    TextView mainClassStd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        Paper.init(this);

        loadingBar = new ProgressDialog(this);

        mainClassStd = findViewById(R.id.teacherMainClassStd);

        teacherID = getIntent().getExtras().get("teacherID").toString();

        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("ClassTeacher").child(teacherID);
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String classStd = snapshot.child("Class").getValue().toString();
                    String grade = snapshot.child("Grade").getValue().toString();
                    String classGrade = classStd + grade;
                    mainClassStd.setText(classGrade);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        classTeacherRef = FirebaseDatabase.getInstance().getReference().child("ClassTeacher");

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent intent = new Intent(TeacherMain.this, WelcomeScreen.class);
                startActivity(intent);
            }
        });


    }

    public void goToStudent(View view) {
        Intent intent = new Intent(this, ViewStudent.class);
        intent.putExtra("teacherID", teacherID);
        intent.putExtra("classStd", mainClassStd.getText().toString());
        startActivity(intent);
    }
}