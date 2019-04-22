package com.lnsoft.test.encode;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created By Chr on 2019/4/22.
 */
public class EnTest {
    public static void main(String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String str = "123456";
        String s1 = enMD5andBASE64(str);
        String s2 = base64(str);
        String s3 = md5(str);
        System.out.println("s1:" + s1);
        System.out.println("s2:" + s2);
        System.out.println("s3:" + s3);

    }

    //base64+md5
    public static String enMD5andBASE64(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        String encode = base64Encoder.encode(md5.digest(s.getBytes("UTF-8")));

        return encode;
    }

    //base64
    public static String base64(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        BASE64Encoder base64Encoder = new BASE64Encoder();

        String encode = base64Encoder.encode(s.getBytes("UTF-8"));

        return encode;
    }

    //md5算法
    public static String md5(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte[] digest = md5.digest(s.getBytes("UTF-8"));

        for (int x=0;x<digest.length;x++) {
            System.out.print(digest[x]);
        }

        return digest.toString();

    }
}
