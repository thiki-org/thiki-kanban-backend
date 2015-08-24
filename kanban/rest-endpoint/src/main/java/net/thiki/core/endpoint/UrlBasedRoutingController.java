package net.thiki.core.endpoint;

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

/**
 * 
 * 此类应该被应用的controller继承，并设定根目录path
 * 注意， 要避免在component-scan中被包含进来，可以按下面的方式exclude：
 * <br/>
 * {@code
 * 	<context:component-scan base-package="net.thiki" >
		<context:exclude-filter type="regex" expression="net\.thiki\.core\.endpoint\..*"/>
	</context:component-scan>
	}
 * @author joeaniu
 *
 */
public abstract class UrlBasedRoutingController implements ApplicationContextAware {

	protected ApplicationContext ctx;
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
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.findAll(request);
    }
    

	/**
	 * 获取指定id的资源
	 * @return
	 */
    @RequestMapping(value = "/{resource}/{id}", method = RequestMethod.GET)
    public Object get(
    		@PathVariable("resource")String resource, 
    		@PathVariable("id") String id,
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header
    		) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.find(id, request);
    }
    
    /**
     * 创建指定资源一份实例
     * @param resourceName
     * @param id
     * @return
     */
    @RequestMapping(value = "/{resource}", method = RequestMethod.POST)
    public RESTfulResponse create(
            @PathVariable("resource")String resourceName, 
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header
    		) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.create(request);
    }
    
    /**
     * 修改指定资源的一份实例
     * @param resourceName
     * @param requestBody
     * @param header
     * @return
     */
    @RequestMapping(value = "/{resource}", method = RequestMethod.PUT)
    public RESTfulResponse modify(
    		@PathVariable("resource")String resourceName, 
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.modify(request);
    }
    
    @RequestMapping(value = "/{resource}", method = RequestMethod.DELETE)
    public RESTfulResponse delete(
    		@PathVariable("resource")String resourceName, 
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resourceName + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.delete(request);
    }
    
    /////////////////带有关联的资源路由/////////////////////
    @RequestMapping(value = "/{resource1}/{id1}/{resource2}", method = RequestMethod.GET)
    public RESTfulResponse get(
    		@PathVariable("resource1")String resource1Name,
    		@PathVariable("id1")String id1,
    		@PathVariable("resource2")String resource2Name,
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource2Name + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.findBy(resource1Name, id1, request);
    }
    
    /**
     * 创建一个与resource1关联的资源
     * @param resource1Name
     * @param id1
     * @param resource2Name
     * @param requestBody
     * @param header
     * @return
     */
    @RequestMapping(value = "/{resource1}/{id1}/{resource2}", method = RequestMethod.POST)
    public RESTfulResponse create(
    		@PathVariable("resource1")String resource1Name,
    		@PathVariable("id1")String id1,
    		@PathVariable("resource2")String resource2Name,
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource2Name + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.create(resource1Name, id1, request);
    }
    /**
     * 创建一个resource1 到 resource2的关联
     * @param resource1Name
     * @param id1
     * @param resource2Name
     * @param id2
     * @param requestBody
     * @param header
     * @return
     */
    @RequestMapping(value = "/{resource1}/{id1}/{resource2}/{id2}", method = RequestMethod.POST)
    public RESTfulResponse add(
    		@PathVariable("resource1")String resource1Name,
    		@PathVariable("id1")String id1,
    		@PathVariable("resource2")String resource2Name,
    		@PathVariable("id2")String id2,
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource2Name + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
        return service.add(resource1Name, id1, resource2Name, id2, request);
    }
    
    /**
     * 解除关联
     * @param resource1Name
     * @param id1
     * @param resource2Name
     * @param id2
     * @param requestBody
     * @param header
     * @return
     */
    @RequestMapping(value = "/{resource1}/{id1}/{resource2}/{id2}", method = RequestMethod.DELETE)
    public RESTfulResponse remove(
    		@PathVariable("resource1")String resource1Name,
    		@PathVariable("id1")String id1,
    		@PathVariable("resource2")String resource2Name,
    		@PathVariable("id2")String id2,
    		@RequestBody(required=false) String requestBody, 
    		@RequestHeader Map<String, String> header) {
    	
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource2Name + "Service");
    	
    	RESTfulRequest request = new RESTfulRequest(requestBody, header);
    	return service.remove(resource1Name, id1, resource2Name, id2, request);
    }
    
    ////////////////////////////////////////////////
    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> get() {
   
		return serviceMap();
    }
    
    /**
     * 在此注册所有已经测试通过的RESTful服务的
     * @return
     */
    public abstract Map<String, String> serviceMap();
    
    
}
