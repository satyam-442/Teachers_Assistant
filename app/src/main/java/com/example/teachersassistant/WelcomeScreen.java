package com.example.teachersassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teachersassistant.auth.StudentLogin;
import com.example.teachersassistant.auth.TeachersLogin;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void teachersLogin(View view) {
        Intent intent = new Intent(this, TeachersLogin.class);
        startActivity(intent);
    }

    public void studentLogin(View view) {
        Intent intent = new Intent(this, StudentLogin.class);
        startActivity(intent);
    }
}