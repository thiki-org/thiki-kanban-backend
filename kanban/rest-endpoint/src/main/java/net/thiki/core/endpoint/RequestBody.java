package net.thiki.core.endpoint;

import java.util.HashMap;
import java.util.Map;


public class RequestBody extends HashMap<String, String> {

    private static final long serialVersionUID = 1L;

    public RequestBody(Map<String, String> map) {
        super(map);
    }
    public RequestBody() {
    }
    
    public int getInt(String intStr) {
        return Integer.parseInt(this.get(intStr));
    }
    
    

}
