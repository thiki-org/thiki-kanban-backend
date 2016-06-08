package org.thiki.kanban.foundation.common.security;



/**
 * Created by winie on 5/30/2016.
 */
public class ApiTest {
    public static void main(String[] args) throws Exception {
        RESTClient restClient=new RESTClient();
       String n= restClient.query();
        System.out.print(n);
    }
}
