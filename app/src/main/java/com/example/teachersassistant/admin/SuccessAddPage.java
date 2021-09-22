package com.example.teachersassistant.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.teachersassistant.R;

public class SuccessAddPage extends AppCompatActivity {

    TextView teacherID, teacherEmail;
    String ID, mailID, name, subject, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_add_page);

        ID = getIntent().getExtras().get("ID").toString();
        mailID = getIntent().getExtras().get("email").toString();
        name = getIntent().getExtras().get("name").toString();

        subject = "Welcome to AcaDroid! Activate your Account in 2 minutes.";
        body = "Hi " + name + " " + ID + " ,\n" +
                "Welcome to AcaDroid! We thank you for choosing us as your preferred partner for your " +
                "School Management. Your account is not activated yet and below are some important details\n" +
                "Your AcaDroid Login Credentials:\n" +
                "Here are your login ID " + ID + " for the AcaDroid platform.\n" +
                "Here are the steps how to generate password:\n" +
                "1. Go to AcaDroid App\n" +
                "2. Login as Teacher\n" +
                "3. Enter the ID provided by Superior\n" +
                "4. Click on Authenticate once you typed your ID\n" +
                "5. Create your password\n" +
                "6. All done!";

        teacherID = findViewById(R.id.teacherId);
        teacherEmail = findViewById(R.id.teacherEmailSA);

        teacherID.setText(ID);
        teacherEmail.setText(mailID);

    }

    public void sendMailBtn(View view) {
        Uri uri = Uri.parse(mailID);
        Intent intent = new Intent(Intent.ACTION_SEND, uri);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailID});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(Intent.createChooser(intent,"Choose an email client"));
    }
}