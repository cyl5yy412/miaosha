package com.lnsoft.test.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试Lombok：idea中需要先添加Plugins安装，重启idea，在添加lombok的依赖，即可使用
 * Created By Chr on 2019/4/3/0003.
 */
@Slf4j
//@Log
//@Log4j2
//@Log4j
@Getter
@Setter
@ToString
public class ChrSlf4j {


    private String pass;
    private String name;

    static class Test {
        public void show() {
            ChrSlf4j chrSlf4j = new ChrSlf4j();
            chrSlf4j.setName("chr");
            chrSlf4j.setPass("0527");
            //=====
            log.error("这个能打印吗？{}", "error");
            log.warn("这个能打印吗？{}", "warn");
            log.debug("这个能打印吗？{}", "debug");
            log.info("这个能打印吗？{}", "info");
            log.info("测试1:{}，测试2:{}，测试3:{}，测试4:{}", "能用吗?", "好像能用", "确定？", "确定");
            //====
            System.out.println(chrSlf4j);
        }
    }

    public static void main(String args[]) {
        Test test = new Test();
        test.show();
    }

}
