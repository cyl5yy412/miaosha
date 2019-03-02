package com.lnsoft.test.only;

/**
 * 单利练习
 * Created By Chr on 2019/2/27/0027.
 */
public class Cup {

    private Cup() {
    }

    ;
    private static Cup cup = null;

    public static Cup getCup() {
        if (cup == null) {
            synchronized (Cup.class) {
                if (cup == null) {
                    cup = new Cup();
                }
            }
        }
        return cup;
    }
}
