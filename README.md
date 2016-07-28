
[![Build Status](http://img.shields.io/travis/thiki-org/thiki-kanban-backend/go.svg?style=flat-square)](https://travis-ci.org/thiki-org/thiki-kanban-backend)
[![Coverage Status](http://img.shields.io/coveralls/thiki-org/thiki-kanban-backend/go.svg?style=flat-square)](https://coveralls.io/r/thiki-org/thiki-kanban-backend?branch=go)
[![Codacy Badge](https://img.shields.io/codacy/grade/096aad581d3b44f6bde20ab37862512e/go.svg?style=flat-square)](https://www.codacy.com/app/btao-cn/thiki-kanban-backend?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=thiki-org/thiki-kanban-backend&amp;utm_campaign=Badge_Grade)


thiki(思奇)是一个充满活力、具有技术追求并热爱创造的团队。我们希望在锤炼工程技艺、尝试软件创新、提升个人能力的同时,打造一款卓越的开源软件,以助力互联网研发团队提高研发效率,并将过程中的技术积累沉淀下来,帮助他人进步。

## thiki-kanban

thiki-kanban是一个精益看板系统,以看板方法为核心,内嵌精益思想,研发过程中覆盖了丰富的技术实践。

系统在设计上使用RESTful的架构风格,服务端与客户端独立演进。

基础技术点:
* spring-hateoas
* spring-boot
* mybatis+mysql
* HSQL
* RestAssured
* RSA
* MD5


**Note**:thiki-kanban-backend仅提供了RESTful服务端,客户端我们提供了[thiki-kanban-web](https://github.com/thiki-org/thiki-kanban-web),你需要两边配合使用。当然,你也可以自行开发适合自己的客户端。

##安装方法 

在安装前,请检查你的机器是否已经具备以下环境:  
* java 1.8;  
* maven 3.2.x or higher version.


### 1、配置数据库

**HSQL**

hsql是内存数据库,仅在集成测试中使用。所以,test目录下的测试你可以随意运行,无需任何配置。

**Mysql**

如果你想通过`Application.java`启动系统,并在浏览器中访问,请首先在你的机器上安装Mysql.然后,在`resources/profiles`中添加你本地的配置,具体配置方法请参考已经存在的`local_tao`|`local_zz`.

配置文件添加完成后,在`pom`的`profiles`节点中配置你的`profile`,参考如下:

```
 <profiles>
     <profile>
         <id>local_zz</id>
         <activation>
              <activeByDefault>true</activeByDefault>
          </activation>
          <properties>
              <prop.dir.name>local_zz</prop.dir.name>
          </properties>
     </profile>
 </profiles>
```

### 2、启动系统

目前启动有三种方式:

* 从Application.java直接运行
* 打成 jar后运行
* 通过maven运行

如果你是一个开发者,建议通过maven运行:

```
 mvn clean install exec:java -P local_tao
```
如需在启动时跳过测试，运行：

```
 clean install exec:java -P local_tao -Dmaven.test.skip=true
```
注意:运行时请把 `local_tao`修改成你的`profile` id.


### 3、访问Web APIs

```
http://localhost:8080/entrance
 
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

## 学习资料

* RESTful

    [Architectural Styles and the Design of Network-based Software Architectures](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)
    
    [理解本真的REST架构风格](http://www.infoq.com/cn/articles/understanding-restful-style)

* [spring-hateoas](http://projects.spring.io/spring-hateoas/)

* [spring-boot](http://projects.spring.io/spring-boot/)

* [hsql](http://hsqldb.org)

* [rest-assured](https://github.com/rest-assured/rest-assured)

    





