package org.thiki.kanban.foundation.common.security;


import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RESTClient {
    private RestTemplate template = new RestTemplate();
    private final static String url = "http://localhost:8080/";

    public String query() {
        String username = "1101";
        String key = "dadadswdewq2ewdwqdwadsadasd";
        String token = "c068e51024c91fef070128bf92507f2e0bde36bdb59a2752172356f9fcd0682b";
        Date currentDate = new Date();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_CLIENTID, username);
        params.put(Constants.PARAM_EXPIRES, Long.toString(new Date().getTime()));
        params.put(Constants.PARAM_ACCESSTOKEN,token);
        params.put(Constants.PARAM_DIGEST, HmacSHA256Utils.digest(key, params));
        String fparams = HttpUtil.makeparam(params);
        return template.getForObject(url + "entrance1?" + fparams, String.class, new String[]{});
    }

}