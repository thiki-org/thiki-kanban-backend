package net.thiki.core.endpoint;

/**
 * 作为HttpResponse返回值的传递容器，包括Http state code、返回对象等
 * @author joeaniu
 *
 */
public class RESTfulResponse {
	private int stateCode;
	private Object responseBody;
	
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
