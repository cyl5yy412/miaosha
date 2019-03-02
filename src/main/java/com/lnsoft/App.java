package com.lnsoft;

import com.lnsoft.dataobject.UserInfoDO;
import com.lnsoft.mapper.UserInfoDOMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"com.lnsoft"})
@MapperScan("com.lnsoft.mapper")
@RestController
public class App {

    @Autowired
    private UserInfoDOMapper userInfoDOMapper;

    @RequestMapping("/")
    public String home() {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(1);
        if (userInfoDO == null) {
            return "用户不存在";
        } else {
            return userInfoDO.getName();
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class, args);
    }
}
