package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Button mVoice;
    SwitchCompat swLed;
    SwitchCompat swFan;
    SwitchCompat swTime;
    TextView tvClock;
    FirebaseDatabase database;
    DatabaseReference fan;
    DatabaseReference led;
    DatabaseReference timer;
    DatabaseReference time;
    DatabaseReference minute1;
    private int t1Hour,t1Minute;
    private static final int REQ_SPEECH_RESULT =1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoice = (Button) findViewById(R.id.voice);
        swLed = (SwitchCompat) findViewById(R.id.switchled);
        swFan = (SwitchCompat) findViewById(R.id.switchfan);
        swTime = (SwitchCompat) findViewById(R.id.timer);
        tvClock = (TextView) findViewById(R.id.clock);

        database = FirebaseDatabase.getInstance();
        led = database.getReference("LED_STATUS");
        fan = database.getReference("FAN_STATUS");
        timer = database.getReference("Timer");
        time = database.getReference("Hour");
        minute1 = database.getReference("Minute");
        getData();
    }

    public void getData() {
        Intent intent = getIntent();
        String hour = intent.getStringExtra("hour");
        String minute = intent.getStringExtra("minute");
        String led = intent.getStringExtra("led");
        String fan = intent.getStringExtra("fan");

        swLed.setChecked(led.equalsIgnoreCase("on") ? true : false);
        swFan.setChecked(fan.equalsIgnoreCase("on") ? true : false);

        if (Integer.parseInt(hour) < 10) {
            hour = "0" + hour;
        }
        if (Integer.parseInt(minute) <10) {
            minute = "0" + minute;
        }
        tvClock.setText(hour + ":" + minute);
    }

    public void setLed(View view)
    {
        swLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    led.setValue("ON");
                }else {
                    led.setValue("OFF");
                }
            }
        });
    }

    public void Fan(View view)
    {
        swFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    fan.setValue("ON");
                }else {
                    fan.setValue("OFF");
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check the Request code
        if (requestCode == REQ_SPEECH_RESULT && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String command = results.get(0);
            // Now we send commands to the IoT device
            if(command.equals("Turn on all")) {
                fan.setValue("ON");
                led.setValue("ON");
            }
            if(command.equals("Turn off all")) {
                fan.setValue("OFF");
                led.setValue("OFF");
            }
            if(command.equals("Turn on the light")) {
                led.setValue("ON");
            }
            if(command.equals("Turn off the light")) {
                led.setValue("OFF");
            }
            if(command.equals("Turn on the fan")) {
                fan.setValue("ON");
            }
            if(command.equals("Turn off the fan")) {
                fan.setValue("OFF");
            }
        }
        else
        {
            Toast.makeText(this, "Vui lòng ra lệnh", Toast.LENGTH_SHORT).show();
        }
    }

    public void startVoiceCommand(View v) {
       try {
           Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
           i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
           i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
           i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tell me, I'm ready!");
           startActivityForResult(i, REQ_SPEECH_RESULT);
       }catch (NullPointerException e)
       {
           throw new IllegalStateException("Lỗi ");
       }
    }

    public void changeTimer(View view) {
        swTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvClock.setVisibility(swTime.isChecked() ? View.VISIBLE : View.INVISIBLE);
                timer.setValue(swTime.isChecked() ? "ON" : "OFF");
            }

        });
    }

    public void chooseTime(View view) {
        tvClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour = hourOfDay;
                                t1Minute=minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,t1Hour,t1Minute);
                                tvClock.setText(DateFormat.format("hh:mm aa",calendar));

                                time.setValue(String.valueOf(t1Hour));
                                minute1.setValue(String.valueOf(t1Minute));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);
//              show dialog
                timePickerDialog.show();
            }
        });
    }

}

