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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lobby extends AppCompatActivity {
Switch switch5,switch6;
Button button3;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        switch5=findViewById(R.id.switch5);
       // switch6=findViewById(R.id.switch6);
        button3=findViewById(R.id.button3);
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LobbyLight1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch5.setChecked(true);
                    progressDialog.dismiss();
                }
                else{
                    switch5.setChecked(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       /* DatabaseReference reference = FirebaseDatabase.getInstance().getReference("LobbyLight2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch6.setChecked(true);
                }
                else{
                    switch6.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                startActivity(new Intent(Lobby.this,verification.class));
                finish();
            }
        });
        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch5.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LobbyLight1");
                    databaseReference.setValue(1);
                }
                else if(!switch5.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LobbyLight1");
                    databaseReference.setValue(0);
                }
            }
        });
      /*  switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch6.isChecked()){
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LobbyLight2");
                    databaseReference.setValue(1);
                }
                else if(!switch6.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LobbyLight2");
                    databaseReference.setValue(0);
                }
            }
        });*/
    }
}