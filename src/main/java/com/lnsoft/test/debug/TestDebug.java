package com.lnsoft.test.debug;


import java.util.ArrayList;
import java.util.List;

/**
 * Created By Chr on 2019/3/9/0009.
 */
public class TestDebug {

    public static void main(String args[]) {
        show();
        System.out.println("!!!");
        show("a");
    }

    public static void show() {
        int arr[] = {11, 23, 454, 652, 123, 542, 21, 325, 67};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        List list=new ArrayList<>();

    }
    public static void show(String a) {
        int arr[] = {11, 23, 454, 652, 123, 542, 21, 325, 67};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        List list=new ArrayList<>();


    }
}
