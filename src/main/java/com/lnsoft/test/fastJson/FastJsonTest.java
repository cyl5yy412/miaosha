package com.lnsoft.test.fastJson;

import com.alibaba.fastjson.JSON;
import com.lnsoft.test.fastJson.other.TaotaoResult;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By Chr on 2019/4/6/0006.
 */
public class FastJsonTest {


    public static void main(String args[]) {
        Map<String, String> params = new HashMap<>();
        params.put("tableName", "emp");
        params.put("objId", "7788");
        ResponseEntity<String> forEntity2 = new RestTemplate().getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={tableName}&objId={objId}", String.class, params);


        String body = forEntity2.getBody();
        //rest接受的http文本转为javaBean
        TaotaoResult taotaoResult = JSON.parseObject(body, TaotaoResult.class);
        //javaBean转为json文本
        String s = JSON.toJSONString(taotaoResult);
        //json文本转为javaBean
        TaotaoResult taotaoResult1 = JSON.parseObject(s, TaotaoResult.class);


        System.out.println(taotaoResult.getData());
        System.out.println("=" + body);
        System.out.println("==" + taotaoResult.getData());
        System.out.println("===" + s);


//        System.err.println(forEntity2.getStatusCode() + "=2");
//        System.err.println(forEntity2.getStatusCodeValue() + "=3");
//        System.err.println(forEntity2.getHeaders() + "=4");
//        System.err.println(forEntity2.getClass() + "=5");



        //======================================================
        //======================================================
        //map(2):使用MultiValueMap（spring的map）可以传单传url不带？传值，直接在MultiValueMap里封装key=value（只在postForEntity中的第二个参数）
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("tableName", "emp");
        request.add("objId", "7788");
        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms", request, String.class);

        TaotaoResult taotaoResult2 = JSON.parseObject(stringResponseEntity.getBody(), TaotaoResult.class);
        System.out.println("ces:" + taotaoResult2.getData());

        //map(3):url拼接{？}占位符
        ResponseEntity<String> stringResponseEntity2 = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={？}&objId={？}", null, String.class, "emp", "7788");

        TaotaoResult taotaoResult3 = JSON.parseObject(stringResponseEntity2.getBody(), TaotaoResult.class);
        System.out.println("ces2:" + taotaoResult3.getData());
    }
}
