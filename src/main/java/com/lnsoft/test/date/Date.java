package com.lnsoft.test.date;

import java.text.SimpleDateFormat;

/**
 * Created By Chr on 2019/3/11/0011.
 */
public class Date {
    public static void main(String[] args) {

        System.out.println(new Date());

        System.out.println(new java.util.Date().getTime());
//        System.out.println(new Date(new java.util.Date().getTime()));

        String d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date().getTime());

        System.out.println(d);



    }
}
