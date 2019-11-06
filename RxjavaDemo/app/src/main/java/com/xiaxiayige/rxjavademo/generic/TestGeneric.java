package com.xiaxiayige.rxjavademo.generic;


import java.util.ArrayList;
import java.util.List;

public class TestGeneric {

    public static void main(String[] args) {


//        List<String> listStr = new ArrayList<>();
//        List<Integer> listInt = new ArrayList<>();
//
//        String item = getItem(listStr);
//        Integer item1 = getItem(listInt);
//
//        getClassInstance(A.class);

//        List<C> cList=new ArrayList<>();
//        addItem(cList,new C());

//        String classInstance = getClassInstance(String.class);

        List<Integer> integers = new ArrayList<>();
//        List<Integer> integers = new ArrayList<>();

//        addItem(integers, 2);

        Object[] temp = new Integer[2];

        temp[0]="11111";

        System.out.println("temp = [" + temp + "]");


        List<String> temp2=new ArrayList<>();
        List<Object> temp3=new ArrayList<>();
        temp3.add(temp2);
        temp3.add(1);

        System.out.println("args = [" + args + "]");
    }


    public static void addItem(List<? super Number> list, int number) {
        list.add(1);
        Object object = list.get(0);
    }


    public static <T> T getClassInstance(Class<T> clas) {
        try {
            return clas.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
