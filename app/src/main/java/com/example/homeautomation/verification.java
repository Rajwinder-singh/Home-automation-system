package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class verification extends AppCompatActivity {
FirebaseAuth mAuth;
EditText mail,pass;
TextView textView;
CheckBox checkBox;
AwesomeValidation validation;
Button signin;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth=FirebaseAuth.getInstance();
        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        signin=findViewById(R.id.signin);
        textView=findViewById(R.id.textView4);
        checkBox=findViewById(R.id.checkBox);
        validation=new AwesomeValidation(ValidationStyle.BASIC);
        validation.addValidation(this,R.id.mail, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        validation.addValidation(this,R.id.pass, RegexTemplate.NOT_EMPTY,R.string.invalid_pass);
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait while we Log you in");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verification.this,MainActivity.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation.validate()){
                    progressDialog.show();
                mAuth.signInWithEmailAndPassword(mail.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           if(mAuth.getCurrentUser().isEmailVerified()){
                               progressDialog.dismiss();
                               startActivity(new Intent(verification.this,Rooms.class));
                           }
                           else
                           {
                               progressDialog.dismiss();
                               Toast.makeText(verification.this,"Unsuccdesfull",Toast.LENGTH_SHORT).show();
                           }
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(verification.this,"Email or Password in incorrect",Toast.LENGTH_SHORT).show();
                        }

                }
            });
        }}
       });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                }
                else if(!buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();

                }
            }
        });
    }
    }
