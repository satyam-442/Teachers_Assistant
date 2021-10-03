package com.example.teachersassistant.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachersassistant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;

public class AddLaboratoryActivity extends AppCompatActivity {

    EditText bachelorClassET, masterClassET, bothClassET;
    TextInputLayout laboratoryNoLay, laboratoryCompNoLay;
    Button addLabBtn;
    DatabaseReference labsRef;
    ProgressDialog loadingBar;
    TextView bothLay;
    RadioGroup radioGroupBoth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laboratory);

        bachelorClassET = findViewById(R.id.bachelorClassET);
        masterClassET = findViewById(R.id.masterClassET);
        bothClassET = findViewById(R.id.bothClassET);

        bothLay = findViewById(R.id.bothLay);
        radioGroupBoth = findViewById(R.id.radioGroupBoth);

        laboratoryNoLay = findViewById(R.id.laboratoryNoLay);
        laboratoryCompNoLay = findViewById(R.id.laboratoryCompNoLay);

        labsRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new ProgressDialog(this);
        addLabBtn = findViewById(R.id.addLabBtn);
        addLabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setMessage("stay tuned...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                String labno = laboratoryNoLay.getEditText().getText().toString();
                String compCount = laboratoryCompNoLay.getEditText().getText().toString();
                String forBachelor = bachelorClassET.getText().toString();
                String forMaster = masterClassET.getText().toString();
                String forBoth = bothClassET.getText().toString();

                if (TextUtils.isEmpty(labno)||TextUtils.isEmpty(compCount)){
                    Toast.makeText(getApplicationContext(), "Field is empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap labMap = new HashMap();
                    labMap.put("labNo",labno.toUpperCase());
                    labMap.put("compCount",compCount);
                    labMap.put("forBachelor",forBachelor);
                    labMap.put("forMaster",forMaster);
                    labMap.put("forBoth",forBoth);
                    labMap.put("isEngage","N");
                    labsRef.child("Laboratory").child(labno.toUpperCase()).updateChildren(labMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    public void onBachelorRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBachelorYes:
                if (checked)
                    bachelorClassET.setText("Yes");
                break;
            case R.id.radioBachelorNo:
                if (checked)
                    bachelorClassET.setText("No");
                break;
        }
    }

    public void onMastersRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioMastersYes:
                if (checked){
                    masterClassET.setText("Yes");
                    bothLay.setVisibility(View.VISIBLE);
                    radioGroupBoth.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radioMastersNo:
                if (checked){
                    masterClassET.setText("No");
                    bothLay.setVisibility(View.GONE);
                    radioGroupBoth.setVisibility(View.GONE);
                }

                break;
        }
    }

    public void onBothRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBothYes:
                if (checked)
                    bothClassET.setText("Yes");
                break;
            case R.id.radioBothNo:
                if (checked)
                    bothClassET.setText("No");
                break;
        }
    }
}