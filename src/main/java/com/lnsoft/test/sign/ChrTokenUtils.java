package com.lnsoft.test.sign;

import java.util.HashSet;
import java.util.Set;

/**
 * Created By Chr on 2019/4/7/0007.
 */
public class ChrTokenUtils {

    private static Set<String> tokenSet = new HashSet<>();

    //登录过程服务器保存

    public static void addToken(String token) {

        tokenSet.add(token);
    }

    //校验登陆
    public static boolean hasToken(String token) {

        return tokenSet.contains(token);
    }

}
