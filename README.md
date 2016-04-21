# restful-archetype

===============

RESTful架构原型 & 看板案例

#架构说明

由于url template的api模式被认为是一个反模式， 这个版本弃用了基于url template的rest-endpoint.jar，回归最原始的spring mvc controller 为每一个api进行路由。

#配置说明

## 准备数据库

数据库DDL放在这里（以后更新数据库schema，必须同步这个sql）：
restful-archetype\mbg\src\mbg\scripts\CreateDB.sql

安装mysql，创建一个数据库thiki，相应的参数定义在restful-archetype\kanban\kanban-war\src\main\resources\profile\localzz\jdbc.properties

jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/thiki?characterEncoding=utf-8
jdbc.username=thiki
jdbc.password=thiki

如果保持完全一致， 就可以直接不必改配置。 如果有不同的地方，就要新建一个maven profile，避免多个协作者之间相互影响。见 maven profile配置一节

## 运行

运行web容器（tomcat、jetty等）就不再赘述了，我们假定web-root名字是kanban，那么一个典型的api就是：

http://localhost:8080/kanban/api/v1/entries GET

注意不要忘写api和v1

## spring mvc controller和路由

TODO

## mybatis generator（MBG）的使用

TODO

## maven profile与运行环境配置隔离

TODO
