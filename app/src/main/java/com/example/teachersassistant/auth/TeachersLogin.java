package com.example.teachersassistant.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.admin.AdminMain;
import com.example.teachersassistant.modal.Users;
import com.example.teachersassistant.admin.AddTeachers;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeachersLogin extends AppCompatActivity {

    TextInputLayout teacherId;
    FirebaseAuth mAuth;
    DatabaseReference teacherRef;
    Button authenticate;
    String parentDBName = "admin";
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_login);

        mAuth = FirebaseAuth.getInstance();
        teacherRef = FirebaseDatabase.getInstance().getReference().child("admin");

        loadingBar = new ProgressDialog(this);

        teacherId = findViewById(R.id.teacherIdLay);
        authenticate = findViewById(R.id.authenticateBtn);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parentDBName = "admin";

                final String userid = teacherId.getEditText().getText().toString();

                if (TextUtils.isEmpty(userid))
                {
                    Toast.makeText(TeachersLogin.this, "Field's are empty!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.child(parentDBName).child(userid).exists())
                            {
                                Users usersData = dataSnapshot.child(parentDBName).child(userid).getValue(Users.class);
                                assert usersData != null;
                                if (usersData.getUseridd().equals(userid))
                                {
                                    if (parentDBName.equals("admin"))
                                    {
                                        Toast.makeText(TeachersLogin.this, "Logged in as admin!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(TeachersLogin.this, AdminMain.class));
                                        loadingBar.dismiss();
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(TeachersLogin.this, "No record found!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        { }
                    });
                }

            }
        });

    }
}