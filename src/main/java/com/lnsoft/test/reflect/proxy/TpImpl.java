package com.lnsoft.test.reflect.proxy;

/**
 * Created By Chr on 2019/4/18.
 */
public class TpImpl implements TP {
    @Override
    public String sayHello(String name,Integer age) {
        System.out.println("hello~"+name+":age:"+age);
        return "hello~" + name+":"+age;
    }

    @Override
    public void bye(String bye) {
        System.out.println("bye bye");
    }
}
