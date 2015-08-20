package net.thiki.core.endpoint;

import java.util.Map;


/**
 * 作为HttpRequest的参数传递容器，包括?GET参数、 POST参数、 Header参数
 * @author joeaniu
 *
 */
public class RESTfulRequest {

	private Map<String, String[]> requestParams;
	private Map<String, String> header;
	private RequestBody requestBody;

	public RESTfulRequest(Map<String, String[]> requestParams, RequestBody requestBody, Map<String, String> header) {
	    this.requestParams = requestParams;
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

	public RESTfulRequest(Map<String, String[]> requestParams, Map<String, String> requestBodyMap, Map<String, String> header) {
	    if (requestBodyMap != null){
	        this.requestBody = new RequestBody(requestBodyMap);
	    }else{
	        this.requestBody = new RequestBody();
	    }
	    this.requestParams = requestParams;
        this.header = header;
    }

	public Map<String, String[]> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(Map<String, String[]> requestParams) {
		this.requestParams = requestParams;
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
