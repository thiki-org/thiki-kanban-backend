package org.thiki.kanban.foundation.common.security;


import java.util.Map;

//无状态token
public class StatelessToken  {
   //userid|is_login|token expires|data（相关信息）|4位随机值
    private String userid;
    private String data;
    private String is_login;
    private Long expires ;
    private String random ;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIs_login() {
        return is_login;
    }

    public void setIs_login(String is_login) {
        this.is_login = is_login;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }
}
