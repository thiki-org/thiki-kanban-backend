package org.thiki.kanban.foundation.common.security;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thiki.kanban.auth.Client;
import org.thiki.kanban.auth.ClientService;
import org.thiki.kanban.auth.User;
import org.thiki.kanban.auth.UserService;
import org.thiki.kanban.foundation.config.SpringContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Access Token拦截器
 * <p/>
 * Created by winie on 16/5/31.
 */
@Resource
public class AccessTokenVerifyInterceptor extends HandlerInterceptorAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(AccessTokenVerifyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOG.info("AccessToken executing ...");
        boolean flag = false;
        //客户端生成的消息摘要
        String clientDigest = request.getParameter(Constants.PARAM_DIGEST);
        //客户端传入的client
        String clientid = request.getParameter(Constants.PARAM_CLIENTID);
        //时间
        String expires = request.getParameter(Constants.PARAM_EXPIRES);
        //判断时间在15分钟内
        Long contentDate= Long.parseLong(expires);
        Date currentDate = new Date();
        Long currentDatel= currentDate.getTime();
        long diffMinutes = (currentDatel-contentDate) / 60000;
        if (diffMinutes>15){
            return  flag;
        }

        //根据客户端在服务器的信息
        Client clients= SpringContextHolder.getBean(ClientService.class).findByClientId(clientid);
        //clientid 获取对应密钥失效时间
        if (contentDate<clients.getExpires()){
            return  flag;
        }
        //如果需要用户登录，需要传access token  需要用配置文件或者在resource里面加
        String uri=request.getRequestURI();
        if (uri.equals("/entrance")){
            String access_token = request.getParameter(Constants.PARAM_ACCESSTOKEN);

            if(StringUtils.isBlank(access_token)){
                return  flag;
            }
            //获取token 解密对应数据流，并将数据流对应到been上
            DESPlus des = new DESPlus(clients.getClientdessecret());// 自定义密钥
            String access_tokendecrypt=des.decrypt(access_token);
            String[] stoken = access_tokendecrypt.split("\\|");
            StatelessToken statelessToken =new StatelessToken();
            for (int i = 0 ; i <stoken.length ; i++ ) {
                switch(i)
                {
                    case 0:statelessToken.setUserid(stoken[i]); break;
                    case 1:statelessToken.setIs_login(stoken[i]); break;
                    case 2:statelessToken.setExpires( Long.parseLong(stoken[i])); break;
                    case 3: statelessToken.setData(stoken[i]); break;
                    case 4:statelessToken.setRandom(stoken[i]);
                }
            }
           //获取用户信息 根据用户id和随机码
           User user=  SpringContextHolder.getBean(UserService.class).findByIdandsalt(statelessToken.getUserid(),statelessToken.getRandom());
            //用户id为空，登陆状态为0，登陆失效时间过期
            if (StringUtils.isBlank(statelessToken.getUserid())){
                return  flag;
            }else if(statelessToken.getIs_login()=="0"){
                return  flag;
            } else if(statelessToken.getExpires()>currentDatel){
            return  flag;
            }else if (user.getId()==null){
                return  flag;
        }
        }
        //在服务器端生成客户端参数消息摘要
//        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
//        params.remove(Constants.PARAM_DIGEST);
        String serverDigest = HmacSHA256Utils.digest(clients.getClientsecret(),getParameterMap(request,Constants.PARAM_DIGEST));
        System.out.println(clientDigest);
        System.out.println(serverDigest);
        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        if (serverDigest.equals(clientDigest)){
            flag=true;
        }
        return flag;
    }

    public static Map getParameterMap(HttpServletRequest request,String key) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            if (!name.equals(key)) {
                returnMap.put(name, value);
            }
        }
        return returnMap;
    }

}