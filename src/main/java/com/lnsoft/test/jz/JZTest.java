package com.lnsoft.test.jz;

/**
 * Created By Chr on 2019/4/24/0024.
 */
public class JZTest {
    public static void main(String args[]){
        //        //字符串，16进制转换为10进制：24->36
//        String s1 = "24";
//        Integer integer = Integer.valueOf(s1, 16);//16进制的基数，返回10进制
//        System.out.println(integer);
//
//        //字符串，10进制转换为16进制：36->4
//        int i2 = 36;
//        String s2 = Integer.toHexString(i2);//返回16进制
//        System.out.println(s2);


        //十进制--->2进制:补全四位
        Integer i = 179 % 25;
        String i1 = Integer.toString(i, 2);//返回2进制的字符串
        if (i1.length() <= 3) {
            i1 = "0" + i1;
        }
        System.out.println(i1);



        //2进制-->10进制,返回int
        int i2 = Integer.parseInt(i1, 2);
        System.out.println(i2);

    }
}
