package org.thiki.kanban.auth;

import java.io.Serializable;


public class Client implements Serializable {

    private String id;
    private String clientname;
    private String clientid;
    //加密api签名
    private String clientsecret;
    private Long expires;
    //加密用户token
    private String clientdessecret;
    private Long desexpires;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public String getClientdessecret() {
        return clientdessecret;
    }

    public void setClientdessecret(String clientdessecret) {
        this.clientdessecret = clientdessecret;
    }

    public Long getDesexpires() {
        return desexpires;
    }

    public void setDesexpires(Long desexpires) {
        this.desexpires = desexpires;
    }

    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }

    private int delete_status;



}
