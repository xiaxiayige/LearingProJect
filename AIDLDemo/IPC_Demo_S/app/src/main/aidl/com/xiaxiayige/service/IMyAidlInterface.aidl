// IMyAidlInterface.aidl
package com.xiaxiayige.service;

// Declare any non-default types here with import statements
import com.xiaxiayige.service.Person;

interface IMyAidlInterface {

    void addPerson(in Person person);
    List<Person> getPersons();

}
