package com.example.teachersassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teachersassistant.auth.StudentLogin;
import com.example.teachersassistant.auth.TeachersLogin;
import com.example.teachersassistant.prevalent.SessionManager;
import com.example.teachersassistant.student.StudentMain;
import com.example.teachersassistant.teacher.TeacherMain;

import java.util.HashMap;

public class WelcomeScreen extends AppCompatActivity {

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        loadingBar = new ProgressDialog(this);

        SessionManager sessionManager = new SessionManager(WelcomeScreen.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            loadingBar.setMessage("fetching...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            /*Intent intent = new Intent(WelcomeScreen.this, StudentMain.class);
            intent.putExtra("teacherID",rememberMeDetails.get(SessionManager.KEY_SESSION_ID));
            startActivity(intent);*/
            if (rememberMeDetails.get(SessionManager.KEY_LOGIN_TYPE).equals("student")){
                Intent student = new Intent(WelcomeScreen.this, StudentMain.class);
                student.putExtra("studentID",rememberMeDetails.get(SessionManager.KEY_SESSION_ID));
                startActivity(student);
                loadingBar.dismiss();
            }
            else {
                Intent teacher = new Intent(WelcomeScreen.this, TeacherMain.class);
                teacher.putExtra("teacherID",rememberMeDetails.get(SessionManager.KEY_SESSION_ID));
                startActivity(teacher);
                loadingBar.dismiss();
            }
        }

    }

    public void teachersLogin(View view) {
        Intent intent = new Intent(this, TeachersLogin.class);
        intent.putExtra("loginType","teacher");
        startActivity(intent);
    }

    public void studentLogin(View view) {
        Intent intent = new Intent(this, StudentLogin.class);
        intent.putExtra("loginType","student");
        startActivity(intent);
    }
}