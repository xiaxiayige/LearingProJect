package com.example.ipc_demo_s;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.xiaxiayige.service.IMyAidlInterface;
import com.xiaxiayige.service.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonAIDLService extends Service {

    ArrayList<Person> personArrayList;

    @Override
    public IBinder onBind(Intent intent) {
        personArrayList = new ArrayList<>();
        return binder;
    }


    Binder binder = new IMyAidlInterface.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {

            Log.e("<===我是服务端", person.toString());
            personArrayList.add(person);
        }

        @Override
        public List<Person> getPersons() throws RemoteException {
            return personArrayList;
        }
    };
}
