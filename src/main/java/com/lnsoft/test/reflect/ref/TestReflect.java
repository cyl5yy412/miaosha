package com.lnsoft.test.reflect.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Chr on 2019/4/18.
 */
public class TestReflect {


    public static void main(String args[]) throws Exception {
        User u = new User("c", "123");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("u", u);

        Object o = map.get("u");


        Method getNameMethod = o.getClass().getMethod("getName", null);
        //System.out.println(getNameMethod.invoke(o, null));

        User user = User.class.newInstance();//也是根据  无参构造方法来进行创建对象的
        //System.out.println(user.getName());

        Constructor<?>[] constructors = o.getClass().getConstructors();

        for (Constructor<?> constructor : constructors) {
           // System.out.println(constructor);
        }

        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        Method getPass = o.getClass().getMethod("getPass", null);
      //  System.out.println(getPass);
       // for (Method declaredMethod : declaredMethods) {
           // System.out.println(declaredMethod);
       // }



        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // System.out.println(declaredField);

        }
        Field name = o.getClass().getDeclaredField("name");
//        System.out.println(name);

        name.setAccessible(true);
        System.out.println(name.get(o));
    }

}
