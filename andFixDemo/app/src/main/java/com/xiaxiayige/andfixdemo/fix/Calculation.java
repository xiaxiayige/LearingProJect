package com.xiaxiayige.andfixdemo.fix;

import com.xiaxiayige.andfixdemo.Replace;

public class Calculation {

    public Calculation() {
    }

    @Replace(className = "com.xiaxiayige.andfixdemo.Calculation", methodName = "calculation")
    public int calculation() {
        return 100;
    }
}
