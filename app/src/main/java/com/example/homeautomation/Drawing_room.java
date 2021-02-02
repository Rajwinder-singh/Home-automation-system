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

public class Drawing_room extends AppCompatActivity {
Switch switch8;
Button button7;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_room);
        switch8=findViewById(R.id.switch8);
        button7=findViewById(R.id.button7);
        this.progressDialog=new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drawinglight");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Value=snapshot.getValue().toString();
                int i=Integer.parseInt(Value);
                if(i==1)
                {
                    switch8.setChecked(true);
                    progressDialog.dismiss();
                }
                else{
                    switch8.setChecked(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                startActivity(new Intent(Drawing_room.this,verification.class));
                finish();
            }
        });
        switch8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch8.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drawinglight");
                    databaseReference.setValue(1);
                }
                else if(!switch8.isChecked()){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Drawinglight");
                    databaseReference.setValue(0);
                }
            }
        });
    }
}