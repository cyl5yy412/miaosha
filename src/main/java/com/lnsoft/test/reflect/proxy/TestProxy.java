package com.lnsoft.test.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created By Chr on 2019/4/18.
 */
public class TestProxy {
    public static void main(String args[]) throws Exception {
        TP tp = TestProxy.show(TP.class);
        tp.sayHello("chr",12);
        tp.bye("chr");

    }


    public static <T> T show(Class<T> interfaceClazz) throws Exception {
        TpImpl t = new TpImpl();

        return (T) Proxy.newProxyInstance(interfaceClazz.getClassLoader(),
                new Class<?>[]{interfaceClazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {

                        //返回值类型
                        System.out.println(method.getReturnType());
                        //类的类型，包名+类名
                        System.out.println(method.getDeclaringClass());
                        //报名+类名
                        System.out.println(method.getDeclaringClass().getName());


                        Object result = method.invoke(t, args);


                        return result;
                    }
                }
        );

    }
}
