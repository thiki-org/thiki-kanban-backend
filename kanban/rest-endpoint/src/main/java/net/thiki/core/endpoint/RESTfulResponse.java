package net.thiki.core.endpoint;

import java.util.Map;

/**
 * 作为HttpResponse返回值的传递容器，包括Http state code、返回对象等
 * @author joeaniu
 *
 */
public class RESTfulResponse {
    
	public static final int STATE_CODE_OK = 200;
	
	/** http state code */
    private int stateCode;
    /** json response as an object */
	private Object responseBody;
	
	
	public RESTfulResponse() {
	    this.stateCode = STATE_CODE_OK;
    }
	
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public Object getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}
	
}
