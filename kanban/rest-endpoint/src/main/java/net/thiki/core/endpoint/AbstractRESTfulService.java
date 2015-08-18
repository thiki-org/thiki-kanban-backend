package net.thiki.core.endpoint;

import java.util.List;

import net.thiki.core.exception.NotSupportedOperationException;

public class AbstractRESTfulService<T> implements StandardRESTfulService<T> {

	@Override
	public List<T> findAll(RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public T find(String id, RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse create(RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse modify(RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse delete(RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse findBy(String resourceName, String id, RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse create(String resourceName, String id, RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse add(String resource1Name, String id1, String resource2Name, String id2,
			RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

	@Override
	public RESTfulResponse remove(String resource1Name, String id1, String resource2Name, String id2,
			RESTfulRequest request) {
		throw new NotSupportedOperationException();
	}

}
