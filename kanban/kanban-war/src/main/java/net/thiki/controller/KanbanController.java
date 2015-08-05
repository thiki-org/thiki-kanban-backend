package net.thiki.controller;

import java.util.HashMap;
import java.util.Map;

import net.thiki.core.endpoint.UrlBasedRoutingController;

import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author joeaniu
 *
 */
@RestController
@RequestMapping( value = "/v1" )
public class KanbanController  extends UrlBasedRoutingController implements ApplicationContextAware {

	@Override
	public Map<String, String> serviceMap() {
		Map<String, String> serviceMap = new HashMap<String, String>();
		serviceMap.put("/user", "用户相关服务");
		return serviceMap;
	}

}
