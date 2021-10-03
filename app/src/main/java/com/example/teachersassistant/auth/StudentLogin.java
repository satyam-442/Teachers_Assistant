package com.example.teachersassistant.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.teachersassistant.R;
import com.example.teachersassistant.admin.AdminMain;
import com.example.teachersassistant.modal.AllStudents;
import com.example.teachersassistant.modal.Teachers;
import com.example.teachersassistant.modal.Users;
import com.example.teachersassistant.prevalent.SessionManager;
import com.example.teachersassistant.student.StudentMain;
import com.example.teachersassistant.teacher.TeacherMain;
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

public class StudentLogin extends AppCompatActivity{

    LinearLayout loginLO, registerLO, linLO;
    TextInputLayout studentFirstName, studentLastName, studentPhone, studentEmail, studentGender,studentClass, studentGrade, studentTeacherList;
    TextInputLayout studentIDET,studentPasswordET;
    TextInputLayout teacherPwd, bottomPwd;
    Spinner classSpinner, gradeSpinner, teacherSpinner;
    ViewFlipper v_flip;
    Button loginStdBtn, registerStdBtn;
    DatabaseReference classTeacherRef;
    ProgressDialog loadingBar;
    Button generatePwd;
    CheckBox rememberMeStudent;
    String loginType = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        classTeacherRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);

        //Image Slider
        int images[] = {R.drawable.one,R.drawable.two,R.drawable.three};
        v_flip = findViewById(R.id.v_flip);

        for (int image: images){
            flipperImages(image);
        }

        SessionManager sessionManager = new SessionManager(StudentLogin.this, SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails = sessionManager.getRememberMeDetailsFromSession();
            Intent intent = new Intent(StudentLogin.this,StudentMain.class);
            intent.putExtra("teacherID",rememberMeDetails.get(SessionManager.KEY_SESSION_ID));
            startActivity(intent);
        }

        rememberMeStudent = findViewById(R.id.rememberMeStudent);

        //SPINNER VALUE
        classSpinner = findViewById(R.id.class_spinner);
        gradeSpinner = findViewById(R.id.grade_spinner);
        teacherSpinner = findViewById(R.id.teacher_spinner);

        loginLO = findViewById(R.id.loginLayout);
        registerLO = findViewById(R.id.registerLayout);
        linLO = findViewById(R.id.linDemo);

        studentFirstName = findViewById(R.id.studentFirstNameLay);
        studentLastName = findViewById(R.id.studentLastNameLay);
        studentPhone = findViewById(R.id.studentPhoneLay);
        studentEmail = findViewById(R.id.studentEmailLay);
        studentGender = findViewById(R.id.teacherGenderLay);
        studentClass = findViewById(R.id.studentClassLay);
        studentGrade = findViewById(R.id.studentGradeLay);
        studentTeacherList = findViewById(R.id.studentTeacherLay);

        studentIDET = findViewById(R.id.studentIDLay);
        studentPasswordET = findViewById(R.id.studentPasswordLay);

        loginStdBtn = findViewById(R.id.loginStdBtn);
        loginStdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid = studentIDET.getEditText().getText().toString();
                final String password = studentPasswordET.getEditText().getText().toString();

                if (TextUtils.isEmpty(userid)) {
                    Toast.makeText(StudentLogin.this, "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    final DatabaseReference rootRef;
                    rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("AllStudents").child(userid).exists()) {
                                AllStudents teachersDate = dataSnapshot.child("AllStudents").child(userid).getValue(AllStudents.class);
                                if (teachersDate.getStudentID().equals(userid)) {
                                    if (teachersDate.getPassword().equals("dummy")) {
                                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(StudentLogin.this);
                                        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
                                        bottomSheetDialog.setCanceledOnTouchOutside(false);

                                        //INITIALIZE YOUR VARIABLES
                                        bottomPwd = bottomSheetDialog.findViewById(R.id.teacherPasswordLay);
                                        generatePwd = bottomSheetDialog.findViewById(R.id.generatePwd);
                                        generatePwd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String password = bottomPwd.getEditText().getText().toString();
                                                final DatabaseReference classTeacherStudsPwdRef, studPwdRef;
                                                //classPwdRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                                                studPwdRef = FirebaseDatabase.getInstance().getReference().child("AllStudents");
                                                HashMap<String, Object> pwdMap = new HashMap<String, Object>();
                                                pwdMap.put("Password", password);
                                                studPwdRef.child(userid).updateChildren(pwdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(StudentLogin.this, "Logged in as Student!", Toast.LENGTH_SHORT).show();
                                                        if (rememberMeStudent.isChecked()) {
                                                            SessionManager sessionManager = new SessionManager(StudentLogin.this, SessionManager.SESSION_REMEMBERME);
                                                            sessionManager.createRememberMeSession(userid, password,loginType);
                                                        }
                                                        Intent intent = new Intent(StudentLogin.this, StudentMain.class);
                                                        intent.putExtra("studID", userid);
                                                        startActivity(intent);
                                                        loadingBar.dismiss();
                                                    }
                                                });
                                            }
                                        });
                                        bottomSheetDialog.show();
                                    } else {
                                        studentPasswordET.setVisibility(View.VISIBLE);
                                        rememberMeStudent.setVisibility(View.VISIBLE);
                                        loadingBar.dismiss();
                                        if (teachersDate.getPassword().equals(password)) {
                                            Toast.makeText(StudentLogin.this, "Logged in as Student!", Toast.LENGTH_SHORT).show();
                                            if (rememberMeStudent.isChecked()) {
                                                SessionManager sessionManager = new SessionManager(StudentLogin.this, SessionManager.SESSION_REMEMBERME);
                                                sessionManager.createRememberMeSession(userid, password, loginType);
                                            }
                                            Intent intent = new Intent(StudentLogin.this, StudentMain.class);
                                            intent.putExtra("studID", userid);
                                            startActivity(intent);
                                            loadingBar.dismiss();
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(StudentLogin.this, "No record found!", Toast.LENGTH_SHORT).show();
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

        registerStdBtn = findViewById(R.id.registerStdBtn);
        registerStdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = studentFirstName.getEditText().getText().toString();
                String lname = studentLastName.getEditText().getText().toString();
                String phone = studentPhone.getEditText().getText().toString();
                String email = studentEmail.getEditText().getText().toString();
                String gender = studentGender.getEditText().getText().toString();
                String classStd = studentClass.getEditText().getText().toString();
                String grade = studentGrade.getEditText().getText().toString();
                String teacher = studentTeacherList.getEditText().getText().toString();
                String studentID = fname.substring(0,3).toUpperCase()+lname.substring(0,3).toUpperCase()+phone.substring(0,4);
                String classgrade = classStd+grade;

                if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(gender) ||
                        TextUtils.isEmpty(classStd) || TextUtils.isEmpty(grade) || TextUtils.isEmpty(teacher)){
                    Toast.makeText(getApplicationContext(), "Field's are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    HashMap<String, Object> studentMap = new HashMap<String, Object>();
                    studentMap.put("FirstName",fname);
                    studentMap.put("LastName",lname);
                    studentMap.put("Email",email);
                    studentMap.put("Phone",phone);
                    studentMap.put("Gender",gender);
                    studentMap.put("Password","dummy");
                    studentMap.put("classStd",classStd);
                    studentMap.put("grade",grade);
                    studentMap.put("image","default");
                    studentMap.put("teacher",teacher);
                    studentMap.put("studentID",studentID);
                    classTeacherRef.child("StudRequest").child(classgrade).child(studentID).updateChildren(studentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                loadingBar.dismiss();
                                Alert("Data submitted successfully. Will update you once your data get verified");
                            }
                            else {
                                loadingBar.dismiss();
                                String msg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(this, R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter4Class);
        classSpinner.setOnItemSelectedListener(new ClassSpinner());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter4Grade);
        gradeSpinner.setOnItemSelectedListener(new GradeSpinner());

        ArrayAdapter<CharSequence> adapter4Teacher = ArrayAdapter.createFromResource(this, R.array.teacher_array, android.R.layout.simple_spinner_item);
        adapter4Teacher.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(adapter4Teacher);
        teacherSpinner.setOnItemSelectedListener(new TeacherStudentList());
    }

    private void Alert(String msg){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Acknowledge")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentFirstName.getEditText().getText().clear();
                        studentLastName.getEditText().getText().clear();
                        studentPhone.getEditText().getText().clear();
                        studentEmail.getEditText().getText().clear();
                        studentGender.getEditText().getText().clear();
                        studentClass.getEditText().getText().clear();;
                        studentGrade.getEditText().getText().clear();
                        dialog.cancel();
                        /*Intent intent = new Intent(getApplicationContext(), StudentLogin.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);*/
                    }
                })
                .create();
        dialog.show();
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flip.addView(imageView);
        v_flip.setFlipInterval(4000);
        v_flip.setAutoStart(true);
        v_flip.setInAnimation(this, android.R.anim.slide_in_left);
        v_flip.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    public void loginLayoutVisibility(View view) {
        loginLO.setVisibility(View.VISIBLE);
        registerLO.setVisibility(View.GONE);
        linLO.setVisibility(View.GONE);
    }

    public void registerLayoutVisibility(View view) {
        registerLO.setVisibility(View.VISIBLE);
        loginLO.setVisibility(View.GONE);
        linLO.setVisibility(View.GONE);
    }

    public void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMale:
                if (checked)
                    studentGender.getEditText().setText("Male");
                break;
            case R.id.radioFemale:
                if (checked)
                    studentGender.getEditText().setText("Female");
                break;
        }
    }

    public void openBottomSheet(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_table);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    private class ClassSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentClass.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class GradeSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentGrade.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class TeacherStudentList implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            studentTeacherList.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}