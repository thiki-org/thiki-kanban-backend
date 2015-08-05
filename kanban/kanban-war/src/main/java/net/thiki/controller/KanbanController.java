package net.thiki.controller;

import net.thiki.core.endpoint.UrlBasedRoutingController;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
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

}
