package com.example.iot;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    String hour_value;
    String minute_value;
    String led_value;
    String fan_value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                mainIntent.putExtra("hour" , hour_value);
                mainIntent.putExtra("minute", minute_value);
                mainIntent.putExtra("led" , led_value);
                mainIntent.putExtra("fan" , fan_value);
                startActivity(mainIntent);
                finish();
            }
        }, 2500);
        retrievingData();
    }

    public void retrievingData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hourdb = ref.child("Hour");
        DatabaseReference minutedb = ref.child("Minute");
        DatabaseReference leddb = ref.child("LED_STATUS");
        DatabaseReference fandb = ref.child("FAN_STATUS");

        hourdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hour_value = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        minutedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                minute_value = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        leddb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                led_value = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fandb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fan_value = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}