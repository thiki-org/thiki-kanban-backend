package net.thiki.core.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author joeaniu
 *
 */
@RestController
@RequestMapping( value = "/v1" )
public class UrlBasedRoutingController implements ApplicationContextAware {

	private ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

	/**
	 * @return
	 */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> test() {
    	List<String> a = new ArrayList<>();
    	a.add("hhhh");
    	return a;
    }
    
	/**
	 * @return
	 */
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public String test1() {
    	return "hhhh";
    }
    
	/**
	 * @return
	 */
    @RequestMapping(value = "/{resource2}/{id2}", method = RequestMethod.GET)
    @RequestMappingPriority(value = 100)
    public Object test2(@PathVariable("resource2")String resource, @PathVariable("id2") Integer id) {
    	return "hhhh";
    }
    
	/**
	 * 慎用
	 * @return
	 */
    @RequestMapping(value = "/{resource}", method = RequestMethod.GET)
    public List<?> list(@PathVariable("resource")String resource) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
        return service.findAll();
    }
    

	/**
	 * 
	 * @return
	 */
    @RequestMapping(value = "/{resource}/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("resource")String resource, @PathVariable("id") Integer id) {
    	StandardRESTfulService<?> service = (StandardRESTfulService<?>) ctx.getBean(resource + "Service");
        return service.find(id);
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
