package com.example.teachersassistant.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.admin.AdminMain;
import com.example.teachersassistant.modal.Teachers;
import com.example.teachersassistant.modal.Users;
import com.example.teachersassistant.admin.AddTeachers;
import com.example.teachersassistant.teacher.TeacherMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TeachersLogin extends AppCompatActivity {

    TextInputLayout teacherId, teacherPwd, bottomPwd;
    FirebaseAuth mAuth;
    DatabaseReference teacherRef;
    Button authenticate, generatePwd;
    String parentDBName = "admin";
    ProgressDialog loadingBar;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_login);

        mAuth = FirebaseAuth.getInstance();
        teacherRef = FirebaseDatabase.getInstance().getReference().child("admin");

        loadingBar = new ProgressDialog(this);

        teacherId = findViewById(R.id.teacherIdLay);
        teacherPwd = findViewById(R.id.teacherPwdLay);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
            intent.putExtra("teacherID",teacherId.getEditText().getText().toString());
            startActivity(intent);
        }
        else if (checkbox.equals("false")){
            Toast.makeText(TeachersLogin.this, "Please sign in", Toast.LENGTH_SHORT).show();
        }

        rememberMe = findViewById(R.id.rememberMe);
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });

        authenticate = findViewById(R.id.authenticateBtn);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentDBName = "admin";
                final String userid = teacherId.getEditText().getText().toString();
                final String password = teacherPwd.getEditText().getText().toString();

                if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(TeachersLogin.this, "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("admin").child(userid).exists()) {
                                Users usersData = dataSnapshot.child("admin").child(userid).getValue(Users.class);
                                assert usersData != null;
                                if (usersData.getUseridd().equals(userid)) {
                                    if (parentDBName.equals("admin")) {
                                        Toast.makeText(TeachersLogin.this, "Logged in as admin!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(TeachersLogin.this, AdminMain.class));
                                        loadingBar.dismiss();
                                    }
                                }
                            } else if (dataSnapshot.child("Teachers").child("ClassTeacher").child(userid).exists()) {
                                Teachers teachersDate = dataSnapshot.child("Teachers").child("ClassTeacher").child(userid).getValue(Teachers.class);
                                if (teachersDate.getTeacherID().equals(userid)) {
                                    if (teachersDate.getPassword().equals("dummy")) {
                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TeachersLogin.this);
                                        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
                                        bottomSheetDialog.setCanceledOnTouchOutside(false);

                                        //INITIALIZE YOUR VARIABLES
                                        bottomPwd = bottomSheetDialog.findViewById(R.id.teacherPasswordLay);
                                        generatePwd = bottomSheetDialog.findViewById(R.id.generatePwd);
                                        generatePwd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String password = bottomPwd.getEditText().getText().toString();
                                                final DatabaseReference classPwdRef,subjPwdRef;
                                                classPwdRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                                                subjPwdRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                                                HashMap<String, Object> pwdMap = new HashMap<String, Object>();
                                                pwdMap.put("Password", password);
                                                classPwdRef.child("ClassTeacher").child(userid).updateChildren(pwdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(TeachersLogin.this, "Logged in as Teacher!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
                                                        intent.putExtra("teacherID",userid);
                                                        startActivity(intent);
                                                        loadingBar.dismiss();
                                                    }
                                                });
                                                subjPwdRef.child("SubjectTeacher").child(userid).updateChildren(pwdMap);
                                            }
                                        });

                                        bottomSheetDialog.show();
                                    } else {
                                        teacherPwd.setVisibility(View.VISIBLE);
                                        rememberMe.setVisibility(View.VISIBLE);
                                        loadingBar.dismiss();
                                        if (teachersDate.getPassword().equals(password)) {
                                            Toast.makeText(TeachersLogin.this, "Logged in as teacher!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
                                            intent.putExtra("teacherID",userid);
                                            startActivity(intent);
                                            loadingBar.dismiss();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(TeachersLogin.this, "No record found!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

            }
        });

    }

}