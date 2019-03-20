package com.example.ipc_demo_c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiaxiayige.service.IMyAidlInterface;
import com.xiaxiayige.service.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface iMyAidlInterface;


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.example.ipc_demo_s","com.example.ipc_demo_s.PersonAIDLService"));

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMyAidlInterface!=null){
                    try {
                        iMyAidlInterface.addPerson(new Person("xiaxiayige",18));
                        List<Person> persons = iMyAidlInterface.getPersons();
                        Log.e("我是客户端===>",persons.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
