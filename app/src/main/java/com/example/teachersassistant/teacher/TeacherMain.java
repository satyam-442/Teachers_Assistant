package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.teachersassistant.prevalent.SessionManager;
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
    DatabaseReference teacherRef;
    ProgressDialog loadingBar;
    TextView mainClass,mainStd, mainName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        //Paper.init(this);
        loadingBar = new ProgressDialog(this);
        mainClass = findViewById(R.id.teacherMainClass);
        mainStd = findViewById(R.id.teacherMainStd);
        mainName = findViewById(R.id.teacherMainName);

        teacherID = getIntent().getExtras().get("teacherID").toString();

        loadingBar.setMessage("please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.getWindow().setElevation(15);
        loadingBar.show();
        teacherRef = FirebaseDatabase.getInstance().getReference();
        teacherRef.child("Teachers").child("ClassTeacher").child(teacherID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String classStd = snapshot.child("Class").getValue().toString();
                    String grade = snapshot.child("Grade").getValue().toString();
                    String name = snapshot.child("FirstName").getValue().toString();
                    mainClass.setText(classStd);
                    mainStd.setText(grade);
                    mainName.setText(name);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(TeacherMain.this, SessionManager.SESSION_REMEMBERME);
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(TeacherMain.this, WelcomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }

    public void goToStudent(View view) {
        String classStd = mainClass.getText().toString() + mainStd.getText().toString();
        Intent intent = new Intent(this, ViewStudent.class);
        intent.putExtra("teacherID", teacherID);
        intent.putExtra("classStd", classStd);
        startActivity(intent);
    }

    public void goToLab(View view) {
        Intent intent = new Intent(this, TeacherViewLabActivity.class);
        intent.putExtra("teacherID", teacherID);
        intent.putExtra("teacherName", mainName.getText().toString());
        startActivity(intent);
    }
}