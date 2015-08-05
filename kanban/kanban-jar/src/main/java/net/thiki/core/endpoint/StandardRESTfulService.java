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
	 * @param request 
	 * @return
	 */
	List<T> findAll(RESTfulRequest request);

	/**
	 * 找到某一个对象
	 * @param id
	 * @param request 
	 * @return
	 */
	T find(Integer id, RESTfulRequest request);

	/**
	 * 创建新对象
	 * @param requestHolder 传入的请求参数的容器
	 * @return
	 */
	RESTfulResponse create(RESTfulRequest requestHolder);

	/**
	 * 修改对象
	 * @param request
	 * @return
	 */
	RESTfulResponse modify(RESTfulRequest request);

	/**
	 * 删除或移除对象
	 * @param request
	 * @return
	 */
	RESTfulResponse delete(RESTfulRequest request);

}
