package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.modal.ClassTeacher;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ViewStudentsRequest extends AppCompatActivity {

    RecyclerView viewRequestRecList;
    DatabaseReference teacherRef;
    TextInputLayout requestViewStudent;
    String teacherID, classStdGrade;
    ProgressDialog loadingBar;
    DatabaseReference reqRef, studentsRef, teacherStudentRef, removeStudRef, rejectedStudRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students_request);

        loadingBar = new ProgressDialog(this);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        classStdGrade = getIntent().getExtras().get("classStd").toString();

        reqRef = FirebaseDatabase.getInstance().getReference();
        teacherStudentRef = FirebaseDatabase.getInstance().getReference();
        studentsRef = FirebaseDatabase.getInstance().getReference();
        removeStudRef = FirebaseDatabase.getInstance().getReference();

        rejectedStudRef = FirebaseDatabase.getInstance().getReference();

        viewRequestRecList = findViewById(R.id.viewStudentRequest);
        viewRequestRecList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewRequestRecList.setLayoutManager(linearLayoutManager);

        requestViewStudent = findViewById(R.id.requestViewText);

        teacherRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child("ClassTeacher").child(teacherID);
        startListen();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingBar.setMessage("please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        startListen();
    }

    private void startListen() {
        Query query = FirebaseDatabase.getInstance().getReference().child("StudRequest").child(classStdGrade).limitToLast(50);
        FirebaseRecyclerOptions<ClassTeacher> options = new FirebaseRecyclerOptions.Builder<ClassTeacher>().setQuery(query, ClassTeacher.class).build();
        FirebaseRecyclerAdapter<ClassTeacher, DonorsViewHolder> adapter = new FirebaseRecyclerAdapter<ClassTeacher, DonorsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DonorsViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final ClassTeacher model) {
                //final String PostKey = getRef(position).getKey();
                holder.setFirstName(model.getFirstName());
                holder.setLastName(model.getLastName());
                holder.setClass(model.getClassStd());
                holder.setGrade(model.getGrade());
                holder.setStudentID(model.getStudentID());
                holder.setTeacherName(model.getTeacher());
                /*holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> studentMap = new HashMap<String, Object>();
                        studentMap.put("FirstName", model.getFirstName());
                        studentMap.put("LastName", model.getLastName());
                        studentMap.put("Gender", model.getGender());
                        studentMap.put("Class", model.getClassStd());
                        studentMap.put("Grade", model.getGrade());
                        studentMap.put("Phone", model.getPhone());
                        studentMap.put("StudentID", model.getStudentID());
                        studentMap.put("Email", model.getEmail());
                        studentMap.put("Password", "dummy");
                        //teacherStudentRef.child("TeachersClass").child(teacherID).child("Students").child(model.getStudentID()).updateChildren(studentMap);
                        //studentsRef.child(model.getStudentID()).updateChildren(studentMap);
                        //removeStudRef.child("StudRequest").child(classStdGrade).child(model.getStudentID()).removeValue();
                        Toast.makeText(getApplicationContext(), "Data Accepted", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> studentMap = new HashMap<String, Object>();
                        studentMap.put("FirstName", model.getFirstName());
                        studentMap.put("LastName", model.getLastName());
                        studentMap.put("Gender", model.getGender());
                        studentMap.put("Class", model.getClassStd());
                        studentMap.put("Grade", model.getGrade());
                        studentMap.put("Phone", model.getPhone());
                        studentMap.put("StudentID", model.getStudentID());
                        studentMap.put("Email", model.getEmail());
                        studentMap.put("Password", "dummy");
                        //teacherStudentRef.child("TeachersClass").child(teacherID).child("RejectStudents").child(model.getStudentID()).updateChildren(studentMap);
                        //rejectedStudRef.child(model.getStudentID()).updateChildren(studentMap);
                        //removeStudRef.child("StudRequest").child(classStdGrade).child(model.getStudentID()).removeValue();
                        Toast.makeText(getApplicationContext(), "Data Rejected", Toast.LENGTH_SHORT).show();
                    }
                });*/
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String studentID = model.getStudentID();
                        CharSequence options[] = new CharSequence[]
                                {
                                        "ACCEPT",
                                        "REJECT"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudentsRequest.this);
                        builder.setTitle("Want to save this student?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i == 0) {
                                    String fname = model.getFirstName();
                                    String lname = model.getLastName();
                                    String gender = model.getGender();
                                    String classStd = model.getClassStd();
                                    String grade = model.getGrade();
                                    String phone = model.getPhone();
                                    String studId = model.getStudentID();
                                    String email = model.getEmail();
                                    AcceptStudentRequest(fname,lname,gender,classStd,grade,phone,studId,email);
                                } else {
                                    String fname = model.getFirstName();
                                    String lname = model.getLastName();
                                    String gender = model.getGender();
                                    String classStd = model.getClassStd();
                                    String grade = model.getGrade();
                                    String phone = model.getPhone();
                                    String studId = model.getStudentID();
                                    String email = model.getEmail();
                                    RejectStudentRequest(fname,lname,gender,classStd,grade,phone,studId,email);
                                }
                            }
                        });
                        builder.show();
                    }
                });
                loadingBar.dismiss();
            }

            @NonNull
            @Override
            public DonorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_student_request, parent, false);
                return new DonorsViewHolder(view);
            }
        };
        viewRequestRecList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RejectStudentRequest(String fname, String lname, String gender, String classStd, String grade, String phone, String studId, String email) {
        HashMap<String, Object> studentMap = new HashMap<String, Object>();
        studentMap.put("FirstName", fname);
        studentMap.put("LastName", lname);
        studentMap.put("Gender", gender);
        studentMap.put("Class", classStd);
        studentMap.put("Grade", grade);
        studentMap.put("Phone", phone);
        studentMap.put("StudentID", studId);
        studentMap.put("Email", email);
        studentMap.put("Password", "dummy");
        teacherStudentRef.child("TeachersClass").child(teacherID).child("RejectStudents").child(studId).updateChildren(studentMap);
        //rejectedStudRef.child("RejectedStud").child(studentID).updateChildren(studentMap);
        removeStudRef.child("StudRequest").child(classStdGrade).child(studId).removeValue();
        Toast.makeText(getApplicationContext(), "Data Rejected", Toast.LENGTH_SHORT).show();
    }

    private void AcceptStudentRequest(String fname, String lname, String gender, String classStd, String grade, String phone, String studId, String email) {
        HashMap<String, Object> studentMap = new HashMap<String, Object>();
        studentMap.put("FirstName", fname);
        studentMap.put("LastName", lname);
        studentMap.put("Gender", gender);
        studentMap.put("Class", classStd);
        studentMap.put("Grade", grade);
        studentMap.put("Phone", phone);
        studentMap.put("StudentID", studId);
        studentMap.put("Email", email);
        studentMap.put("Password", "dummy");
        teacherStudentRef.child("TeachersClass").child(teacherID).child("Students").child(studId).updateChildren(studentMap);
        //studentsRef.child(studentID).updateChildren(studentMap);
        removeStudRef.child("StudRequest").child(classStdGrade).child(studId).removeValue();
        Toast.makeText(getApplicationContext(), "Data Accepted", Toast.LENGTH_SHORT).show();
    }

    /*And this is the static class*/
    public static class DonorsViewHolder extends RecyclerView.ViewHolder {
        Button acceptBtn, rejectBtn;
        public DonorsViewHolder(@NonNull View itemView) {
            super(itemView);
            //mView = itemView;
            /*acceptBtn = itemView.findViewById(R.id.studRqstAcceptBtn);
            rejectBtn = itemView.findViewById(R.id.studRqstRejectBtn);*/
        }

        public void setFirstName(String fname) {
            TextView firstname = (TextView) itemView.findViewById(R.id.studentFirstName);
            firstname.setText("FirstName: " + fname);
        }

        public void setLastName(String lname) {
            TextView lastname = (TextView) itemView.findViewById(R.id.studentLastName);
            lastname.setText("LastName: " + lname);
        }

        public void setClass(String classStd) {
            TextView std = (TextView) itemView.findViewById(R.id.studentClass);
            std.setText("Class: " + classStd);
        }

        public void setGrade(String grade) {
            TextView studGrade = (TextView) itemView.findViewById(R.id.studentGrade);
            studGrade.setText("Grade: " + grade);
        }

        public void setStudentID(String studID) {
            TextView stdID = (TextView) itemView.findViewById(R.id.studentStudID);
            stdID.setText("StudentID: " + studID);
        }

        public void setTeacherName(String teacherName) {
            TextView teachName = (TextView) itemView.findViewById(R.id.studentTeacherName);
            teachName.setText("Teacher: " + teacherName);
        }

        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/
    }
}