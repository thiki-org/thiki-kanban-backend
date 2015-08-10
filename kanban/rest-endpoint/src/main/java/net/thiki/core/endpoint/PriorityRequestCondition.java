package net.thiki.core.endpoint;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 * <p>处理优先级。优先级由@RequestMappingPriority确定。</p>
 * <p>当type和method的priority不一致时，取高的priority</p>
 * @author joeaniu
 *
 */
final class PriorityRequestCondition implements RequestCondition<PriorityRequestCondition>{

	private static final Logger logger = LoggerFactory.getLogger(PriorityRequestCondition.class);

	/** 优先级 */
	private int priority;
	/** 路径信息 */
	private String pathInfo;
	/** 标注的地方 */
	private Target target;
	
	public enum Target{
		Type,
		Method;
	}
	
	public PriorityRequestCondition(Class<?> handlerType) {
		RequestMappingPriority annotation = handlerType.getAnnotation(RequestMappingPriority.class);
		this.priority = calPriority(annotation);
		this.pathInfo = "type:" + handlerType.getCanonicalName();
		this.target = Target.Type;
	}

	public PriorityRequestCondition(Method method) {
		RequestMappingPriority annotation = method.getAnnotation(RequestMappingPriority.class);
		this.priority = calPriority(annotation);
		this.pathInfo = "method:" + method.toGenericString();
		this.target = Target.Method;
	}
	
	public PriorityRequestCondition() {
		this.priority = RequestMappingPriority.PRIORITY_DEFAULT;
	}

	private int calPriority(RequestMappingPriority annotation) {
		if (annotation == null){
			return RequestMappingPriority.PRIORITY_DEFAULT;
		}
		return annotation.value();
	}

	/**
	 * 当type和method的priority不一致时，取method的priority
	 */
	@Override
	public PriorityRequestCondition combine(PriorityRequestCondition other) {
		
		if (other.target == this.target){
			throw new RuntimeException("ooops, it can't be here.");
		}
		if (other.target == Target.Method && this.target == Target.Type){
			logger.debug("combine {} and {}, and get {}", new Object[]{this, other, other});
			return other;
		}else{
			logger.debug("combine {} and {}, and get {}", new Object[]{this, other, this});
			return this;
		}

	}

	/**
	 * always match
	 */
	@Override
	public PriorityRequestCondition getMatchingCondition(HttpServletRequest request) {
		return this;
	}

	@Override
	public int compareTo(PriorityRequestCondition other, HttpServletRequest request) {
		int r = -1;
		if (this.priority > other.priority) r = 1;
		if (this.priority == other.priority) r = 0;
		logger.debug("compare:  {} and {} , result=={}", new Object[]{this, other, r});
		return r;
	}

	@Override
	public String toString() {
		return "PriorityRequestCondition [priority=" + priority + ", pathInfo=" + pathInfo + "]";
	}
	
}