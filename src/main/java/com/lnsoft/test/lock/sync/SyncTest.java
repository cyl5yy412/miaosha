package com.lnsoft.test.lock.sync;

/**
 * Created By Chr on 2019/4/10/0010.
 */
public class SyncTest {
    public static void main(String args[]) {

        SyncTest syncTest=new SyncTest();

        //syncTest.show();

//        syncTest.show2();
//        syncTest.show3();

        new SyncTest().show2();
        new SyncTest().show3();
    }

    int i=1;
    public void show() {
        Object o1 = new Object();
        Object o2 = new Object();

        synchronized (o1){
            System.out.println(Thread.currentThread().getName()+i);
        }
        synchronized (o2){
            System.out.println(Thread.currentThread().getName()+i);
        }

    }

    public static synchronized void show2() {
//        System.out.println(i++);
        System.out.println(Thread.currentThread().getName()+":show2");
    }

    public static synchronized void show3() {
//        System.out.println(i++);

        System.out.println(Thread.currentThread().getName()+":show3");
    }
}
