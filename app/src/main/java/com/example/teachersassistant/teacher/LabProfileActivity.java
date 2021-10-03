package com.example.teachersassistant.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersassistant.R;

public class LabProfileActivity extends AppCompatActivity {

    ImageView addLabRemarksImg, closeLabRemarksImg;
    TextView remarkTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_profile);

        remarkTxt = findViewById(R.id.remarkTxt);

        addLabRemarksImg = findViewById(R.id.addLabRemarks);
        addLabRemarksImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLabRemarksImg.setVisibility(View.GONE);
                closeLabRemarksImg.setVisibility(View.VISIBLE);
                remarkTxt.setText("Write remarks below");
            }
        });

        closeLabRemarksImg = findViewById(R.id.closeLabRemarks);
        closeLabRemarksImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLabRemarksImg.setVisibility(View.VISIBLE);
                closeLabRemarksImg.setVisibility(View.GONE);
                remarkTxt.setText("Remarks");
            }
        });
    }
}