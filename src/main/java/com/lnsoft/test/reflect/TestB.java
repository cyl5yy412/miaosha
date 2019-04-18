package com.lnsoft.test.reflect;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Created By Chr on 2019/4/17.
 */
public class TestB {

    public static void main(String args[]) {

        //show();


        TestB t = new TestB();
        Runnable show = TestB::show;
        show.run();

    }

    public void show2() {
        List list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(5);
        list.add(6);
        list.forEach(System.out::print);
        System.out.println();
        list.forEach(a -> System.out.print(a));

        Function<String, String> function = String::toUpperCase;

        IntFunction aNew = int[]::new;
        aNew.apply(10);


        Function<HashMap, Object> f = HashMap::new;
//        f.apply();
        System.out.println();
    }

    public static void show() {
        int a = 3;
        int b = 6;

        int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};


        Arrays.fill(arr, a, b, 9);
        System.out.println(a);
        System.out.println(b);
        System.out.println("==");
        for (int x = 0; x < arr.length; x++) {

            System.out.println(arr[x]);
        }
    }
}
