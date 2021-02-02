package com.example.homeautomation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Master_Bedroom extends AppCompatActivity {
Switch switch1,switch2;
Button button2;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master__bedroom);
        switch1=(Switch)findViewById(R.id.switch1);
       // switch2=(Switch)findViewById(R.id.switch3);
        button2=findViewById(R.id.button2);
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTube");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch1.setChecked(true);
                    progressDialog.dismiss();
                }
                else{
                    switch1.setChecked(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      /*  DatabaseReference reference=FirebaseDatabase.getInstance().getReference("MasterLight");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch2.setChecked(true);
                }
                else{
                    switch2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
           switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if (switch1.isChecked()) {
                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTube");
                       databaseReference.setValue(1);
                   } else if (!switch1.isChecked()) {
                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterTube");
                       databaseReference.setValue(0);
                   }
               }
           });
          /* switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if (switch2.isChecked()) {
                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterLight");
                       databaseReference.setValue(1);
                   } else if (!switch2.isChecked()) {
                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MasterLight");
                       databaseReference.setValue(0);
                   }
               }
           });*/
           button2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                   SharedPreferences.Editor editor=preferences.edit();
                   editor.putString("remember","false");
                   editor.apply();
                   startActivity(new Intent(Master_Bedroom.this,verification.class));
                   finish();

               }
           });

        }
}