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
	 * @param request 传入的请求参数的容器
	 * @return
	 */
	RESTfulResponse create(RESTfulRequest request);

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

	/**
	 * 通过resourceName和id，关联查询
	 * @param resourceName 关联的资源
	 * @param id 关联资源的id
	 * @param request 可能包含其他查询条件
	 * @return
	 */
	RESTfulResponse findBy(String resourceName, String id, RESTfulRequest request);

	/**
	 * 创建与指定资源相关联的另外一类资源实例
	 * @param resourceName
	 * @param id
	 * @param request
	 * @return
	 */
	RESTfulResponse create(String resourceName, String id, RESTfulRequest request);

	/**
	 * 增加已有两个资源的关联
	 * @param resource1Name
	 * @param id1
	 * @param resource2Name
	 * @param id2
	 * @param request
	 * @return
	 */
	RESTfulResponse add(String resource1Name, String id1, String resource2Name, String id2, RESTfulRequest request);

	/**
	 * 移除一个关联
	 * @param resource1Name
	 * @param id1
	 * @param resource2Name
	 * @param id2
	 * @param request
	 * @return
	 */
	RESTfulResponse remove(String resource1Name, String id1, String resource2Name, String id2, RESTfulRequest request);

}
