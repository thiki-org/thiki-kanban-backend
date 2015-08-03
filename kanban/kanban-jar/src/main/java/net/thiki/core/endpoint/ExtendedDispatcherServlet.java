package net.thiki.core.endpoint;

import org.springframework.web.servlet.DispatcherServlet;

public class ExtendedDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	public ExtendedDispatcherServlet() {
		super.setDetectAllHandlerMappings(false);
	}
	
}
