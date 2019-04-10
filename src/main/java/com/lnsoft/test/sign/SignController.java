package com.lnsoft.test.sign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 接口签名：
 * EditThisCookie
 * springmvc是一个aop，==》拦截器
 * 过滤器就是servlet实现
 * Created By Chr on 2019/4/7/0007.
 */
@Api(value = "user信息", description = "user信息api")
@RestController
@RequestMapping("/sign")
public class SignController {

    //动态盐
    private static String key;

    @ApiOperation(value = "取用户信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "用户id",required = true, dataType = "String")
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public String getInfo(@RequestParam("id") String id,
//                          @RequestParam("token") String token,
                          HttpServletRequest request) {

        //request获取cookie
        String token = ChrCookieUtils.getRequestedToken(request);

        //校验token
        if (!ChrTokenUtils.hasToken(token)) {
            return "请登录";
        }


        System.out.println("查询用户信息~~~~~");
        return "返回用户信息";
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/toLogin", method = {RequestMethod.POST})
    public String toLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam("name") String name,
                          @RequestParam("passwd") String passwd) {

        //限制次数，时间-------------密文加有效期
        /**
         * 请看
         */
//        passwd = RSA.priDecode(passwd);//升级 key=aaa
//        //rsa值赋值给动态盐
//        key = passwd;


        //passwd=123456 1554295149722 ---------时间固定到13位
        String time = passwd.substring(passwd.length() - 13);
        if (System.currentTimeMillis() - Long.parseLong(time) > 1 * 60 * 1000) {//大于1分钟
            return "登陆异常·时间超时";
        }

        passwd = passwd.substring(0, passwd.length() - 13);
        if ("chr".equals(name)) {//模拟登录验证
            String token = UUID.randomUUID().toString();

            /**
             * 请看
             */
            //得到token，服务器进行缓存
            ChrTokenUtils.addToken(token);

            //response种cookie
            ChrCookieUtils.flushCookie(token, response);

            return "登陆成功:" + token;
        }
        return "登陆失败";

    }


    @ApiOperation(value = "充值")
    @RequestMapping(value = "/recharge", method = {RequestMethod.POST})
    public String recharge(@RequestParam("phone") String phone,//
                           @RequestParam("money") int money,//
                           @RequestParam("sign") String sign,//该sign必须是phone和money通过计算得到的
                           HttpServletRequest request) {

        /**
         * 请求方式：客户端到服务器（浏览器到后台）
         * 组合方式：
         *        组合方式可能还被知道，还需要加盐(&chr就是盐)，
         *        但是静态的盐也会被破解，前端可以查看js代码，
         *        此时就需要动态盐(key就是动态盐)，只要把key设置成无法抓取的就可以解决。
         * 动态盐key需要从客户端到服务器，服务器可以明白这个盐，黑客不懂
         *          可以专门写一个js：为rsa接口，固定往后台传递rsa加密之后的串，后台接到串之后，解密
         *         再加一次密：用rsa 加密 实现动态盐。前端随机的串，用rsa再一次密。传送给服务器，黑客只能得到rsa加密后的串，无法知道实际意义
         *         因为rsa无法被解密。而服务器能够解密，下面的接口，就用这个服务器解密的rsa串为url的盐
         *
         *          RSA：解密非对称，没有私钥无法解密。该aaa就是浏览器在内存生成的随机值，用完就扔掉
         *          可以专门做一个rsa传输值的接口
         *         #######################################################################################################
         *         ######   就是在充值之前：                                                                          #######
         *         #####    key=aaa ：前端随机一个值，通过rsa加密 传输给后台，下一个接口的sign签名就用这个值来做盐             #######
         *         #######################################################################################################

         */
        //########未理解##################################################################
        //模拟rsa：可以直接做一个rsa接口
        //（1）解密：前端随机出一个值，该值通过rsa传递给后台，解密rsa，赋值给动态盐。
        //String rsa = RSA.priDecode("前端发送的rsa串");//升级 例如：前端发送的rsa为：*3）-=1ld ，通过私钥，进行解密得到：aaa
        //（2）赋值给盐：rsa值赋值给动态盐
        String rsa = "a";
        key = rsa;
        //（3）签名比对：再把盐进行比对接口签名
        String sb = "money=" + money + "&phone=" + phone + "&" + key;

        //（4）判断撞库：撞库，是不是能够找出这个规律
        String md5Str = StringMD5.getMD5(sb);
        if (!sign.equals(md5Str)) {
            return "警告，接口被篡改";
        }
        //##########################################################################
        System.out.println("调用接口进行充值~~~~~");

        return "充值成功";
    }

    //客户端
    public static void main(String args[]) {
        String sb = "money=10&phone=12345&" + key;

        System.out.println(StringMD5.getMD5(sb));


    }
}