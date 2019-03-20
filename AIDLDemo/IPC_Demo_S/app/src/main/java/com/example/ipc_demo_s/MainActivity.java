package com.example.ipc_demo_s;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaxiayige.service.Person;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            System.out.println("msg = [" + msg + "]");
            Person person = msg.getData().getParcelable("person");
            textView.append(person.toString() + "\n");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getInstance().addActivity(this);
        Intent serviceIntent = new Intent(this, PersonAIDLService.class);
        startService(serviceIntent);
        textView = findViewById(R.id.text);
    }
}
