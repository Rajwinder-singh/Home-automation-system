package com.example.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bathroom extends AppCompatActivity {
Switch switch10;
ProgressDialog progressDialog;
Button button9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);
        switch10=findViewById(R.id.switch10);
        button9=findViewById(R.id.button9);
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bathroom");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch10.setChecked(true);
                    progressDialog.dismiss();
                }
                else{
                    switch10.setChecked(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                startActivity(new Intent(Bathroom.this,verification.class));
                finish();

            }
        });
        switch10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch10.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bathroom");
                    databaseReference.setValue(1);
                }
                else if(!switch10.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Bathroom");
                    databaseReference.setValue(0);
                }
            }
        });
    }
}