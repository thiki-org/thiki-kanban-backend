# restful-archetype

===============

RESTful架构原型 & 看板案例

#架构说明

rest-endpoint.jar是一个web端点的框架，基于spring mvc，设定和实现了一些RESTful API的convention， 解决了CRUD和简单的关联的操作，这些操作都抽象在StandardRESTfulService中，这样省了程序员每次都去写路由的麻烦。
同时，提供了convention之外自定义的路径的机制：在Controller的类或方法前标记annotation @RequestMappingPriority，来定义更高优先级的路由。

#使用说明

1、spring mvc的配置
除了常用的adapter、message converter等配置以外，需要覆盖handlerMapping的实现：
```xml
    <bean id="handlerMapping" class="net.thiki.core.endpoint.PriorityRequestMappingHandlerMapping"/>
```
这个handlerMapping是用于支持@RequestMappingPriority的。  可参见【看板案例】里的springmvc-servlet.xml

2、编写入口的spring mvc controller
姑且叫做AAAController，它须继承UrlBasedRoutingController（仅需要实现Map<String, String> serviceMap()来返回服务列表）。  另外需要指定AAAController的根路径，例如【看板案例】是定义为：@RequestMapping( value = "/v1" )

3、编写应用层逻辑，入口是XYZsService， 其中XYZ是某个资源（resource）的名称，继承AbstractRESTfulService<XYZ>。
到此为止，把应用跑起来，我们就可以用convention的url来跑CRUD和简单关联服务了，例如：
http://host:port/${approot}/${controller-root}/taskList/10000 的GET请求， 就会被路由到：
```java
taskListsService.find(id, request)
```
这个方法。
其他路由规则的convention都定义在UrlBasedRoutingController，在下一节列出。

#路由规则
TODO

#定制路由
convention定义的路由不一定能满足所有的业务需求，因此，rest-endpoint提供了定制路由的机制。这个机制很简单，只需要另外定义一个spring mvc controller，为这个类定义大于零的路由优先级，即可在冲突的时候覆盖默认的convention。
例如：

```java
@RestController
@RequestMapping( value = "/v1" )
@RequestMappingPriority(value = 100)
public class AdminController  extends UrlBasedRoutingController {

    @RequestMapping(value = "/taskLists/20000", method = RequestMethod.GET)
    //@RequestMappingPriority(value = 100)
    public Object getSpecialTaskLists(HttpServletRequest httpServletRequest,
            @RequestBody(required=false) String resourceBody, 
            @RequestHeader Map<String, String> header
            ) {

        // use other service method whatever you want.
        ...

        return ...;
    }

}

```

以上代码故意将Admin的路由前缀和AAAController的“/v1”相冲突，里面一个方法定义了/v1/taskLists/20000的另外一种路由，这样会产生一个冲突，spring mvc不知道应该选AAAController，还是AdminController，于是会出错。 但如果给Controller定义了优先级，spring mvc就可以判定用优先级高的路由。

定义优先级的方法是：
```java
@RequestMappingPriority(value = 100)
```

可以标注在类上，也可以标注在方法上。默认的路由优先级是0，只要自定义的路由优先级大于0，即可覆盖默认的路由。







