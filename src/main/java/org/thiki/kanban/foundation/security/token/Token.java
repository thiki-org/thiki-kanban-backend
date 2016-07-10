package org.thiki.kanban.foundation.security.token;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by xubt on 7/8/16.
 */
public class Token {
    private String userName;
    private String expirationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
