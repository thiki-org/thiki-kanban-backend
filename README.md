基础技术涉及spring-hateoas\spring-boot,数据层面采用Mybatis+mysql.


##安装方法 
0、requirement:  
* java 1.8;  
* maven 3.2.x or higher version; 


1、下载[dolphin](https://github.com/xubitao/dolphin).

   本项目中的众多公共依赖都在dolphin中，所以请务必先下载dolphin，并install到本地；

2、配置kanban.properities中的jdbc信息,默认的数据库配置可直接使用,但请勿破坏表结构；如果使用自己的MySQL数据库，`/src/main/resources/init_db.sql`中有创建表所需的SQL语句；

3、可以在IDE中从Application类的main方法中直接启动，也可以通过`mvn package`打成jar包后运行;

4、spring-hateoas学习资料：[http://projects.spring.io/spring-hateoas/](http://projects.spring.io/spring-hateoas/)
## API入口
```
http://localhost:8080/entrance
```
```json
{
  "description": "Welcome!",
  "_links": {
    "self": {
      "href": "http://localhost:8080/entrance"
    },
    "entries": {
      "href": "http://localhost:8080/entries"
    }
  }
}

```

# 有争议（疑问）的地方

* Resource作为DTO， 结构与对应的Business Object基本就是一致的， 是不是有代码重复之嫌？
* UUID vs 整数递增id
* uri path中包含userId 利弊如何？


# 测试覆盖率

[![Coverage Status](https://coveralls.io/repos/github/thiki-org/thiki-kanban-backend/badge.svg?branch=go)](https://coveralls.io/github/thiki-org/thiki-kanban-backend?branch=go)
