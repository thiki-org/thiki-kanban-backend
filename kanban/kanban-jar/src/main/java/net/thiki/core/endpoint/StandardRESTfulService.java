package net.thiki.core.endpoint;

import java.util.List;
/**
 * 定义RESTful service的通用方法
 * @author joeaniu
 *
 */
public interface StandardRESTfulService<T> {

	/**
	 * 找到所有的对象
	 * @return
	 */
	List<T> findAll();

	/**
	 * 找到某一个对象
	 * @param id
	 * @return
	 */
	T find(Integer id);

}
