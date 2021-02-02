package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
EditText mail,Pass;
TextView textView;
Button button;
AwesomeValidation validation;
private  ProgressDialog progressDialog;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mail= findViewById(R.id.editTextTextEmailAddress2);
       Pass= findViewById(R.id.editTextTextPassword2);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        final String Mail=mail.getText().toString();
        final String pass=Pass.getText().toString();
        validation=new AwesomeValidation(ValidationStyle.BASIC);
        validation.addValidation(this,R.id.editTextTextEmailAddress2, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        validation.addValidation(this,R.id.editTextTextPassword2, RegexTemplate.NOT_EMPTY,R.string.invalid_pass);
        mAuth=FirebaseAuth.getInstance();
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("Please wait while we create your account");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,verification.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation.validate()) {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent i = new Intent(MainActivity.this, verification.class);
                                            Toast.makeText(MainActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Unsuccesfull", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }  }
        });
    }

}
