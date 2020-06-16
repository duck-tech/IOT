package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btnOnAll;
    Button btnOffAll;
    Button btnLighting;
    Button btnFan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLighting = (Button) findViewById(R.id.btn_lighting);
        btnFan = (Button) findViewById(R.id.btn_fan);
        btnOnAll = (Button) findViewById(R.id.btn_onall);
        btnOffAll = (Button) findViewById(R.id.btn_offAll);
    }

    public void onAll(View view) {
        btnOnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference led = database.getReference("LED_STATUS");
                DatabaseReference fan = database.getReference("FAN_STATUS");

                led.setValue("ON");
                fan.setValue("ON");
            }
        });
    }

    public void offAll(View view) {
        btnOffAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference led = database.getReference("LED_STATUS");
                DatabaseReference fan = database.getReference("FAN_STATUS");

                led.setValue("OFF");
                fan.setValue("OFF");

            }
        });
    }

    public void Lighting(View view) {
        Toast.makeText(this, "Lighting", Toast.LENGTH_LONG).show();
    }

    public void Fan(View view) {
        Toast.makeText(this, "Fan", Toast.LENGTH_LONG).show();
    }

}

