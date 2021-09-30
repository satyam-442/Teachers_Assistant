package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teachersassistant.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentsProfile extends AppCompatActivity {

    String studID, teacherID;
    TextView firstNameText, lastNameText, genderText, phoneText, emailText, studIdText;
    TextInputLayout addRemarksText;
    Button addRemarksBtn, submitRemarksBtn, cancelRemarksBtn;
    LinearLayout remarksCancelLayout;
    DatabaseReference studentReference;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_profile);

        loadingBar = new ProgressDialog(this);

        studentReference = FirebaseDatabase.getInstance().getReference();

        studID = getIntent().getExtras().get("studID").toString();
        teacherID = getIntent().getExtras().get("teacherID").toString();

        firstNameText = findViewById(R.id.studentFName);
        lastNameText = findViewById(R.id.studentLName);
        genderText = findViewById(R.id.studentGender);
        phoneText = findViewById(R.id.studentPhone);
        emailText = findViewById(R.id.studentEmail);
        studIdText = findViewById(R.id.studentID);

        addRemarksText = findViewById(R.id.addRemarksEditText);
        addRemarksBtn = findViewById(R.id.addRemarksBtn);
        submitRemarksBtn = findViewById(R.id.submitRemarksBtn);
        cancelRemarksBtn = findViewById(R.id.cancelRemarksBtn);
        remarksCancelLayout = findViewById(R.id.remarksCancelLayout);

        loadingBar.setMessage("please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        studentReference.child("TeachersClass").child(teacherID).child("Students").child(studID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fname = snapshot.child("FirstName").getValue().toString();
                    String lname = snapshot.child("LastName").getValue().toString();
                    String gender = snapshot.child("Gender").getValue().toString();
                    String phone = snapshot.child("Phone").getValue().toString();
                    String email = snapshot.child("Email").getValue().toString();
                    String studId = snapshot.child("StudentID").getValue().toString();
                    firstNameText.setText(fname);
                    lastNameText.setText(lname);
                    genderText.setText(gender);
                    phoneText.setText(phone);
                    emailText.setText(email);
                    studIdText.setText(studId);
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void remarksVisibilityState(View view) {
        addRemarksBtn.setVisibility(View.GONE);
        addRemarksText.setVisibility(View.VISIBLE);
        remarksCancelLayout.setVisibility(View.VISIBLE);
    }

    public void remarksCancelVisibilityState(View view) {
        addRemarksBtn.setVisibility(View.VISIBLE);
        addRemarksText.setVisibility(View.GONE);
        remarksCancelLayout.setVisibility(View.GONE);
    }
}