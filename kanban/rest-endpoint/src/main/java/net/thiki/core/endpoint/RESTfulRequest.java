package net.thiki.core.endpoint;

import java.util.Map;

/**
 * 作为HttpRequest的参数传递容器，包括?GET参数、 POST参数、 Header参数
 * @author joeaniu
 *
 */
public class RESTfulRequest {

	private Map<String, String> header;
	private RequestBody requestBody;

	public RESTfulRequest(RequestBody requestBody, Map<String, String> header) {
		this.requestBody = requestBody;
		this.header = header;
	}
	
	public RESTfulRequest(Map<String, String> requestBodyMap, Map<String, String> header) {
	    if (requestBodyMap != null){
	        this.requestBody = new RequestBody(requestBodyMap);
	    }else{
	        this.requestBody = new RequestBody();
	    }
        this.header = header;
    }


	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
		this.requestBody = requestBody;
	}


	
	

}
