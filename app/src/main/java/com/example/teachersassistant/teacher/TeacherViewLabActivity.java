package com.example.teachersassistant.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.example.teachersassistant.modal.Laboratory;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TeacherViewLabActivity extends AppCompatActivity {

    RecyclerView viewLabRecList;
    DatabaseReference labRef;
    TextInputLayout requestViewStudent;
    String teacherID, teacherName;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_lab);

        loadingBar = new ProgressDialog(this);

        teacherID = getIntent().getExtras().get("teacherID").toString();
        teacherName = getIntent().getExtras().get("teacherName").toString();

        viewLabRecList = findViewById(R.id.viewLab);
        viewLabRecList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewLabRecList.setLayoutManager(linearLayoutManager);

        labRef = FirebaseDatabase.getInstance().getReference().child("Laboratory");
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
        Query query = labRef.limitToLast(50);
        FirebaseRecyclerOptions<Laboratory> options = new FirebaseRecyclerOptions.Builder<Laboratory>().setQuery(query, Laboratory.class).build();
        FirebaseRecyclerAdapter<Laboratory, viewLaboratoryViewHolder> adapter = new FirebaseRecyclerAdapter<Laboratory, viewLaboratoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewLaboratoryViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Laboratory model) {
                //final String PostKey = getRef(position).getKey();
                holder.setLabNo(model.getLabNo());
                holder.setCompCount(model.getCompCount());
                holder.setAvailable(model.getIsEngage());
                holder.setForBach(model.getForBachelor());
                holder.setForMaster(model.getForMaster());
                //holder.setTeacherName(model.getTeacher());
                //if (model.getIsEngage().toString().equals("Y")) {
                //    holder.switchIcon.setEnabled(true);
                //}
                holder.switchIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (model.getIsEngage().toString().equals("Y")){
                            holder.switchIcon.setEnabled(true);
                        }
                        if (isChecked){
                            holder.listTeacherIDLay.setVisibility(View.VISIBLE);
                            holder.listTeacherNameLay.setVisibility(View.VISIBLE);
                            //holder.listTeacherPhoneLay.setVisibility(View.VISIBLE);
                            holder.setTeacherID(teacherID);
                            holder.setTeacherName(teacherName);
                            Toast.makeText(getApplicationContext(), "Lab Engaged", Toast.LENGTH_SHORT).show();
                        } else {
                            holder.listTeacherIDLay.setVisibility(View.GONE);
                            holder.listTeacherNameLay.setVisibility(View.GONE);
                            //holder.listTeacherPhoneLay.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Lab Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String labNo = model.getLabNo();
                        Toast.makeText(getApplicationContext(), labNo, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TeacherViewLabActivity.this,LabProfileActivity.class);
                        intent.putExtra("labNo",labNo);
                        intent.putExtra("teacherID",teacherID);
                        startActivity(intent);
                    }
                });
                loadingBar.dismiss();
            }

            @NonNull
            @Override
            public viewLaboratoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_lab_view, parent, false);
                return new viewLaboratoryViewHolder(view);
            }
        };
        viewLabRecList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class viewLaboratoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listTeacherIDLay, listTeacherNameLay, listTeacherPhoneLay;

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchIcon;

        public viewLaboratoryViewHolder(@NonNull View itemView) {
            super(itemView);
            /*mView = itemView;
            acceptBtn = itemView.findViewById(R.id.studRqstAcceptBtn);
            rejectBtn = itemView.findViewById(R.id.studRqstRejectBtn);*/
            switchIcon = itemView.findViewById(R.id.switchIcon);

            listTeacherIDLay = itemView.findViewById(R.id.listTeacherIDLay);
            listTeacherNameLay = itemView.findViewById(R.id.listTeacherNameLay);
            //listTeacherPhoneLay = itemView.findViewById(R.id.listTeacherPhoneLay);
        }

        public void setLabNo(String fname) {
            TextView firstname = (TextView) itemView.findViewById(R.id.listLabNo);
            firstname.setText(fname);
        }

        public void setCompCount(String lname) {
            TextView lastname = (TextView) itemView.findViewById(R.id.listCompCount);
            lastname.setText(lname);
        }

        public void setAvailable(String classStd) {
            TextView std = (TextView) itemView.findViewById(R.id.listIsAvailable);
            std.setText(classStd);
        }

        public void setForBach(String grade) {
            TextView studGrade = (TextView) itemView.findViewById(R.id.listForBachelors);
            studGrade.setText(grade);
        }

        public void setForMaster(String studID) {
            TextView stdID = (TextView) itemView.findViewById(R.id.listForMasters);
            stdID.setText(studID);
        }

        public void setTeacherID(String teachID) {
            TextView teacherID = (TextView) itemView.findViewById(R.id.listTeacherID);
            teacherID.setText(teachID);
        }

        public void setTeacherName(String teachName) {
            TextView teacherName = (TextView) itemView.findViewById(R.id.listTeacherName);
            teacherName.setText(teachName);
        }
        /*public void setImagee(Context ctx, String image)
        {
            CircleImageView donorimage = (CircleImageView) mView.findViewById(R.id.donor_profile_image);
            Picasso.with(ctx).load(image).into(donorimage);
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
    }
}