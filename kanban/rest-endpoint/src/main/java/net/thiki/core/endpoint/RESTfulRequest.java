package net.thiki.core.endpoint;

import java.util.Map;

/**
 * 作为HttpRequest的参数传递容器，包括?GET参数、 POST参数、 Header参数
 * @author joeaniu
 *
 */
public class RESTfulRequest {

	private Map<String, String> header;
	private String requestBody;

	public RESTfulRequest(String requestBody, Map<String, String> header) {
		this.requestBody = requestBody;
		this.header = header;
	}
	
	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
    
    public String getHeaderParam(Object key){
        return this.header.get(key);
    }
	

}
