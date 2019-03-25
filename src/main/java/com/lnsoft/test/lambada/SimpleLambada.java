package com.lnsoft.test.lambada;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created By Chr on 2019/3/15/0015.
 */
public class SimpleLambada {

    public static void main(String args[]) {

/*        //原始
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();
        //lambada
        new Thread(() -> System.out.println("hello")).start();


        //
        Integer ages = getAges(a -> Integer.parseInt(a), "23");
        System.out.println(ages);*/

        //=====测试1==== BeanUtils.copyProperties================================================
        SimpleLambada simpleLambada = new SimpleLambada();
//        User user = simpleLambada.userSet();
//        UserModel userModel = simpleLambada.userModel();
//        UserModel userModel1 = simpleLambada.convertModelFromUser(user, userModel);
//
//        System.out.println(userModel1.getNameMo());
//        System.out.println(userModel1.getPassMo());
//        System.out.println(userModel1.getName());
//        System.out.println(userModel1.getPass());
        //====测试2========================================================
        List<User> userList = simpleLambada.userList();
        List<UserModel> userModelList = userList.stream().map(user -> {
            UserModel userModel = simpleLambada.convertFromUser(user);
            return userModel;
        }).collect(Collectors.toList());

        userModelList.forEach(userModel -> {
            System.out.println(userModel.getName());
//            System.out.println(userModel.getName());
//            System.out.println(userModel.getPass());
//            System.out.println(userModel.getNameMo());
//            System.out.println(userModel.getPassMo());
        });
        //=================================================================

        //=================================================================

    }


    public static Integer getAges(Function<String, Integer> function, String args) {
        return function.apply(args);
    }

    class User {
        private String name;
        private String pass;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }
    }

    class UserModel {

        private String name;
        private String pass;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        private String nameMo;
        private String passMo;

        public String getNameMo() {
            return nameMo;
        }

        public void setNameMo(String nameMo) {
            this.nameMo = nameMo;
        }

        public String getPassMo() {
            return passMo;
        }

        public void setPassMo(String passMo) {
            this.passMo = passMo;
        }
    }

    public List<User> userList() {
        User user = new User();
        user.setPass("1");
        user.setName("a");
        User user1 = new User();
        user1.setPass("2");
        user1.setName("b");
        User user2 = new User();
        user2.setPass("3");
        user2.setName("c");

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);
        return list;

    }

    public User userSet() {
        User user = new User();
        user.setName("1");
        user.setPass("a");
        return user;
    }

    public UserModel userModel() {
        return new UserModel();
    }

    public UserModel convertModelFromUser(User user, UserModel userModel) {
        if (user == null) return null;

        //测试:只会复制相同名字的遍历
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }

    public UserModel convertFromUser(User user) {
        if (user == null) return null;

        UserModel userModel = new UserModel();
        //测试:只会复制相同名字的遍历
        BeanUtils.copyProperties(user, userModel);
        return userModel;
    }
}
