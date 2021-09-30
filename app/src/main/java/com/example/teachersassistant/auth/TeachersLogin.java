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
import com.example.teachersassistant.prevalent.Prevalent;
import com.example.teachersassistant.prevalent.SessionManager;
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

import io.paperdb.Paper;

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

        rememberMe = findViewById(R.id.rememberMe);
        /*Paper.init(this);

        String UserTeacherIDKey = Paper.book().read(Prevalent.UserTeacherIDKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if (UserTeacherIDKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserTeacherIDKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                AllowTeacherToLogin(UserTeacherIDKey, UserPasswordKey);
            }
        }*/

        SessionManager sessionManager = new SessionManager(TeachersLogin.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            Intent intent = new Intent(TeachersLogin.this,TeacherMain.class);
            intent.putExtra("teacherID",rememberMeDetails.get(SessionManager.KEY_SESSION_ID));
            startActivity(intent);
        }

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
                                                final DatabaseReference classPwdRef, subjPwdRef;
                                                classPwdRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                                                subjPwdRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                                                HashMap<String, Object> pwdMap = new HashMap<String, Object>();
                                                pwdMap.put("Password", password);
                                                classPwdRef.child("ClassTeacher").child(userid).updateChildren(pwdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(TeachersLogin.this, "Logged in as Teacher!", Toast.LENGTH_SHORT).show();
                                                        if (rememberMe.isChecked()) {
                                                            SessionManager sessionManager = new SessionManager(TeachersLogin.this, SessionManager.SESSION_REMEMBERME);
                                                            sessionManager.createRememberMeSession(userid, password);
                                                        }
                                                        Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
                                                        intent.putExtra("teacherID", userid);
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
                                            if (rememberMe.isChecked()) {
                                                SessionManager sessionManager = new SessionManager(TeachersLogin.this, SessionManager.SESSION_REMEMBERME);
                                                sessionManager.createRememberMeSession(userid, password);
                                            }
                                            Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
                                            intent.putExtra("teacherID", userid);
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

    /*private void AllowTeacherToLogin(String userTeacherIDKey, String userPasswordKey) {
        loadingBar.setMessage("validating");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Teachers").child("ClassTeacher").child(userTeacherIDKey).exists()) {
                    Teachers teachersDate = dataSnapshot.child("Teachers").child("ClassTeacher").child(userTeacherIDKey).getValue(Teachers.class);
                    assert teachersDate != null;
                    if (teachersDate.getTeacherID().equals(userTeacherIDKey)) {
                        if (teachersDate.getPassword().equals(userPasswordKey)) {
                            //Toast.makeText(TeachersLogin.this, "Logged in as Teacher!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TeachersLogin.this, TeacherMain.class);
                            intent.putExtra("teacherID",userTeacherIDKey);
                            startActivity(intent);
                            loadingBar.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

}