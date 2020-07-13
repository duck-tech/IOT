//package com.example.iot;
//
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.format.DateFormat;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import java.util.Calendar;
//
//public class TimePicker_Activity extends AppCompatActivity {
//
//    TextView tvTimer1;
//    Switch swTimer;
//    int t1Hour,t1Minute;
//    Intent intent;
//
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timepicker);
//
//        tvTimer1 = (TextView) findViewById(R.id.tv_timer1);
//        swTimer = (Switch) findViewById(R.id.timer);
//
//        getData();
//    }
//
//    public void getData() {
//        intent = getIntent();
//        String hour = intent.getStringExtra("hour");
//        String minute = intent.getStringExtra("minute");
//
//        if (Integer.parseInt(hour) < 10) {
//            hour = "0" + hour;
//        }
//        if (Integer.parseInt(minute) <10) {
//            minute = "0" + minute;
//        }
//        tvTimer1.setText(hour + ":" + minute);
//    }
//
//    public void chooseTime(View view) {
//        tvTimer1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(
//                    TimePicker_Activity.this,
//                    new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                            t1Hour = hourOfDay;
//                            t1Minute=minute;
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.set(0,0,0,t1Hour,t1Minute);
//                            tvTimer1.setText(DateFormat.format("hh:mm aa",calendar));
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//                            DatabaseReference time = database.getReference("Hour");
//                            DatabaseReference minute1 = database.getReference("Minute");
//
//                            time.setValue(String.valueOf(t1Hour));
//                            minute1.setValue(String.valueOf(t1Minute));
//                        }
//                    },12,0,false
//                );
//                timePickerDialog.updateTime(t1Hour,t1Minute);
////              show dialog
//                timePickerDialog.show();
//            }
//        });
//    }
//
//    public void changeTimer(View view) {
//        swTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                tvTimer1.setVisibility(swTimer.isChecked() ? View.VISIBLE : View.INVISIBLE );
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference timer = database.getReference("Timer");
//
//                timer.setValue(swTimer.isChecked() ? "ON" : "OFF");
//
//            }
//        });
//    }
//}
