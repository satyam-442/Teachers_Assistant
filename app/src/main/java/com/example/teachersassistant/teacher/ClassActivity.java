package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ClassActivity extends AppCompatActivity {

    Spinner classSpinner, gradeSpinner;
    TextInputLayout classInput, gradeInput;
    Button updateBtn;
    String ID;
    ProgressDialog loadingBar;
    DatabaseReference teacherRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        loadingBar = new ProgressDialog(this);
        //INITIALIZE YOUR VARIABLES
        ID = getIntent().getExtras().get("ID").toString();

        classSpinner = findViewById(R.id.class_spinner_update);
        gradeSpinner = findViewById(R.id.grade_spinner_update);
        classInput = findViewById(R.id.teacherClassLay);
        gradeInput = findViewById(R.id.teacherGradeLay);

        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(ID);
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String classStatus = snapshot.child("classTeacher").getValue().toString();
                    if (classStatus.equals("UP")){
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(ClassActivity.this, R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter4Class);
        classSpinner.setOnItemSelectedListener(new ClassSpinnerTeacher());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(ClassActivity.this, R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter4Grade);
        gradeSpinner.setOnItemSelectedListener(new GradeSpinnerTeacher());

        updateBtn = findViewById(R.id.classUpdateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setMessage("please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                String classStd = classInput.getEditText().getText().toString();
                String grade = gradeInput.getEditText().getText().toString();
                final DatabaseReference classRef;
                classRef = FirebaseDatabase.getInstance().getReference().child("Teachers");
                HashMap<String, Object> pwdMap = new HashMap<String, Object>();
                pwdMap.put("Class", classStd);
                pwdMap.put("Grade", grade);
                pwdMap.put("classTeacher", "UP");
                classRef.child(ID).updateChildren(pwdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            /*Intent intent = new Intent(ClassActivity.this, TeacherMain.class);
                            intent.putExtra("class",classStd);
                            intent.putExtra("grade",grade);
                            startActivity(intent);*/
                            Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            String msg = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                        loadingBar.dismiss();
                    }
                });
            }
        });
    }

    private class ClassSpinnerTeacher implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            classInput.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class GradeSpinnerTeacher implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            gradeInput.getEditText().setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}