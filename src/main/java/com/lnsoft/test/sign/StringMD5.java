package com.lnsoft.test.sign;

import java.security.MessageDigest;

/**
 * url+sign
 * 接口签名：一般用http://xxxx.com/xxx=xxx&xxx=xxx&sign=加密算法
 * sign：接口签名，最后校验位，由前面的参数 通过算法算出来的，通常使用MD5签名方式
 * <p>
 * Created By Chr on 2019/4/7/0007.
 */
public class StringMD5 {

    public static String getMD5(String str) {
        String result = "";
        try {
            MessageDigest md5 =
                    MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            byte b[] = md5.digest();
            int i;
            StringBuffer buffer = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            //32位：
            // result=buffer.toString();

            //返回16位
            result = buffer.toString().substring(8, 24);

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return result;
    }
}
