package org.thiki.kanban;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.thiki.kanban.foundation.common.security.Constants;
import org.thiki.kanban.foundation.common.security.HmacSHA256Utils;

@RunWith(SpringJUnit4ClassRunner.class)
public class MockServerClientTest extends TestBase {

    private MockRestServiceServer mockServer;
    static RestTemplate restTemplate;

    @Before
    public void setUp() {
        // super.setUp();
        restTemplate = new RestTemplate();
        //模拟一个服务器
        //mockServer = mockServer.createServer(restTemplate);
    }

    @Test
    public void testServiceHelloSuccess() {
        String username = "admin";
        String key = "dadadswdewq2ewdwqdwadsadasd";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(Constants.PARAM_USERNAME, username);
//        params.add("param1", param11);
//        params.add("param1", param12);
//        params.add("param2", param2);
        params.add(Constants.PARAM_DIGEST, HmacSHA256Utils.digest(key, params));
        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/entrance")
                .queryParams(params).build().toUriString();
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        //Assert.assertEquals("entrance" + param11 + param12 + param2, responseEntity.getBody());
    }
}