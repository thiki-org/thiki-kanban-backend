package net.thiki.core.endpoint;

import java.lang.reflect.Method;

import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class PriorityRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	@Override
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		return new PriorityRequestCondition(handlerType);
	}
	
	
	@Override
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		return new PriorityRequestCondition(method);
	}
	
}
