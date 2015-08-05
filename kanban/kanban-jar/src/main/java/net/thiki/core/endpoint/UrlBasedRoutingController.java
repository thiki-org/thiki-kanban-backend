package net.thiki.core.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 尝试一下是否可以继承？ /v1后的path都是convention
 * @author joeaniu
 *
 */
//@RestController
//@RequestMapping( value = "/v1" )
public class UrlBasedRoutingController implements ApplicationContextAware {

	private ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
	
    
	/**
	 * 获取指定资源的所有实例列表，筛选条件在request里
	 * @return
	 */
    @RequestMapping(value = "/{resource}", method = RequestMethod.GET)
    public List<?> list(@PathVariable("resource")String resource,
    		@RequestBody Map<String, String> resourceBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
    	RESTfulRequest request = new RESTfulRequest(resourceBody, header);
        return service.findAll(request);
    }
    

	/**
	 * 获取指定id的资源
	 * @return
	 */
    @RequestMapping(value = "/{resource}/{id}", method = RequestMethod.GET)
    public Object get(
    		@PathVariable("resource")String resource, 
    		@PathVariable("id") Integer id,
    		@RequestBody(required=false) Map<String, String> resourceBody, 
    		@RequestHeader Map<String, String> header
    		) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
    	RESTfulRequest request = new RESTfulRequest(resourceBody, header);
        return service.find(id, request);
    }
    
    /**
     * 创建指定资源一份实例
     * @param resourceName
     * @param id
     * @return
     */
    @RequestMapping(value = "/{resource}", method = RequestMethod.POST)
    public RESTfulResponse create(@PathVariable("resource")String resourceName, 
    		@RequestBody Map<String, String> resourceBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(resourceBody, header);
        return service.create(request);
    }
    
    /**
     * 修改指定资源的一份实例
     * @param resourceName
     * @param resourceBody
     * @param header
     * @return
     */
    @RequestMapping(value = "/{resource}", method = RequestMethod.PUT)
    public RESTfulResponse modify(
    		@PathVariable("resource")String resourceName, 
    		@RequestBody Map<String, String> resourceBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(resourceBody, header);
        return service.modify(request);
    }
    
    @RequestMapping(value = "/{resource}", method = RequestMethod.DELETE)
    public RESTfulResponse delete(
    		@PathVariable("resource")String resourceName, 
    		@RequestBody Map<String, String> resourceBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(resourceBody, header);
        return service.delete(request);
    }
    
    /**
     * 在此注册所有已经测试通过的RESTful服务的
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> get() {
        HashMap<String, String> serviceMap = new HashMap<String, String>();
        
        serviceMap.put("/user", "用户相关服务");
        
		return serviceMap;
    }
    
    
}
