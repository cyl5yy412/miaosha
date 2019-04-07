package com.lnsoft;

import com.alibaba.fastjson.JSON;
import com.lnsoft.test.config.RestTemplateConfig;
import com.lnsoft.test.fastJson.other.TaotaoResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Chr on 2019/4/6/0006.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RestTemplateTest {

    @Test
    public void show() {
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8090/user/get?id=1", String.class);

        //----getForEntity
        //1：url直接传递
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName=emp&objId=7788", String.class);
        //2：url拼接{？}占位符传递
//        ResponseEntity<String> forEntity1 = new RestTemplate().getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={?}", String.class,"emp");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={?}&objId={?}", String.class,"emp","7788");
        //3：map传递
//        Map<String,String> params=new HashMap<>();
//        params.put("tableName","emp");
//        params.put("objId","7788");
//        ResponseEntity<String> forEntity2 = new RestTemplate().getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={tableName}&objId={objId}", String.class,params);


        //---postForEntity
        //1.url直接传递
//        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName=emp&objId=7788", null, String.class);
        //2.map传递
        // map(1)
//        Map<String,String> params2=new HashMap<>();
//        params2.put("tableName","emp");
//        params2.put("objId","7788");
//        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={tableName}&objId={objId}", null, String.class,params2);
        //map(2):使用MultiValueMap（spring的map）可以传单传url不带？传值，直接在MultiValueMap里封装key=value（只在postForEntity中的第二个参数）
//        MultiValueMap<String,String> request=new LinkedMultiValueMap<>();
//        request.add("tableName","emp");
//        request.add("objId","7788");
//        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms", request, String.class);

        //map(3):url拼接{？}占位符
//        ResponseEntity<String> stringResponseEntity = new RestTemplate().postForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={？}&objId={？}", null, String.class, "emp","7788");


//        System.err.println(stringResponseEntity.getBody());
//        System.err.println(forEntity.getBody());
//        System.err.println(forEntity1.getBody());
//        System.err.println(forEntity2.getBody()+"=1");
//        System.err.println(forEntity2.getStatusCode()+"=2");
//        System.err.println(forEntity2.getStatusCodeValue()+"=3");
//        System.err.println(forEntity2.getHeaders()+"=4");
//        System.err.println(forEntity2.getClass()+"=5");
    }


    @Resource
    private RestTemplate restTemplate;

    @Test
    public void show2(){
        Map<String, String> params = new HashMap<>();
        params.put("tableName", "emp");
        params.put("objId", "7788");
        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:8085/Inter-Manage2/im/pms?tableName={tableName}&objId={objId}", String.class, params);


        String body = forEntity2.getBody();
        //rest接受的http文本转为javaBean
        TaotaoResult taotaoResult = JSON.parseObject(body, TaotaoResult.class);
        System.err.println(taotaoResult.getData());
    }
}
