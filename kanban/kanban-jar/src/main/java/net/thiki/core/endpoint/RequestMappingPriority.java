package net.thiki.core.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author joeaniu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestMappingPriority {
	
	public static final int PRIORITY_LOW = Integer.MIN_VALUE;
	public static final int PRIORITY_MEDIUM = 0;
	public static final int PRIORITY_HIGHT = Integer.MAX_VALUE;
	public static final int PRIORITY_DEFAULT = 0;
	
	int value() default 0;
	
	
}
