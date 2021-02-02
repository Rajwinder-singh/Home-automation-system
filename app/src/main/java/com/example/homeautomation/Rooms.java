package com.example.homeautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Rooms extends AppCompatActivity {
ListView listView;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        listView=(ListView)findViewById(R.id.listview);
        button=findViewById(R.id.button6);
        final ArrayList<String> arraylist=new ArrayList<>();
        arraylist.add("Master Bedroom");
        arraylist.add("Bathroom");
        arraylist.add("Lobby");
        arraylist.add("Drawing Room");
        arraylist.add("Porch");
        arraylist.add("Window Alarm");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arraylist);
        listView.setAdapter(arrayAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                finish();
                startActivity(new Intent(Rooms.this,verification.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    startActivity(new Intent(Rooms.this,Master_Bedroom.class));
                }
                else if(position==1)
                {
                    startActivity(new Intent(Rooms.this,Bathroom.class));
                }
                else if(position==2)
                {
                    startActivity(new Intent(Rooms.this,Lobby.class));
                }
                else if(position==3)
                {
                    startActivity(new Intent(Rooms.this,Drawing_room.class));
                }
                else if(position==5)
                {
                    startActivity(new Intent(Rooms.this,Window_Alarms.class));
                }
                else if(position==4)
                {
                    startActivity(new Intent(Rooms.this, Porch.class));
                }
            }
        });
    }
}