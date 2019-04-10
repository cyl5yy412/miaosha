package com.lnsoft.test.sign;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By Chr on 2019/4/7/0007.
 */
public class ChrCookieUtils {
    public static final String COOKIE_NAME_SESSION = "Jsessionid";


    /**
     * 读cookie
     *
     * @param request
     * @return
     */
    public static String getRequestedToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie == null) {
                continue;
            }
            if (!COOKIE_NAME_SESSION.equals(cookie.getName())) {
                continue;
            }
            return cookie.getValue();
        }
        return "";
    }


    /**
     * 种cookie
     *
     * @param token
     * @param response
     */
    public static void flushCookie(String token, HttpServletResponse response) {

        Cookie cookie = new Cookie(COOKIE_NAME_SESSION, token);
        cookie.setHttpOnly(true);

        cookie.setPath("/");
        cookie.setDomain("dev.com");//cookie放到域名下 --，注意必须是该域名下访问

        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);//种cookie
    }
}
