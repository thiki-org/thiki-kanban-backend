
[![Build Status](http://img.shields.io/travis/thiki-org/thiki-kanban-backend/go.svg?style=flat-square)](https://travis-ci.org/thiki-org/thiki-kanban-backend)
[![Coverage Status](http://img.shields.io/coveralls/thiki-org/thiki-kanban-backend/go.svg?style=flat-square)](https://coveralls.io/r/thiki-org/thiki-kanban-backend?branch=go)
[![Codacy Badge](https://img.shields.io/codacy/grade/096aad581d3b44f6bde20ab37862512e/go.svg?style=flat-square)](https://www.codacy.com/app/btao-cn/thiki-kanban-backend?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=thiki-org/thiki-kanban-backend&amp;utm_campaign=Badge_Grade)
[![CocoaPods](https://img.shields.io/badge/文档-API-green.svg?style=flat-square)](https://github.com/thiki-org/thiki-kanban-backend/blob/go/src/test/resources/APIDocument.md)
[![CocoaPods](https://img.shields.io/badge/博客-blog-ff69b4.svg?style=flat-square)](http://blog.thiki.org/)
[![CocoaPods](https://img.shields.io/badge/%E5%AE%98%E7%BD%91-WebSite-ff69b4.svg?style=flat-square)](http://www.thiki.org/)

thiki(思奇)是一个充满活力、具有技术追求并热爱创造的团队。我们希望在锤炼工程技艺、尝试软件创新、提升个人能力的同时,打造一款卓越的开源软件,以助力互联网研发团队提高研发效率,并将过程中的技术积累沉淀下来,帮助他人进步。

## thiki-kanban

thiki-kanban是一个精益看板系统,以看板方法为核心,内嵌精益思想,研发过程中覆盖了丰富的技术实践。

系统在设计上服务端与客户端独立演进。

基础技术点:

* spring-boot
* spring-hateoas
* mybatis+mysql
* HSQL
* RSA
* Gradle

DevOps技术点：

* HAProxy
* nginx
* KeepAlived
* ELK
* Shell

缓存：

* Redis

自动化测试技术点：

* RESTAssured
* Gauge

架构与设计

* RESTful
* DDD

**Note**:thiki-kanban-backend仅提供了RESTful服务端,客户端我们提供了[thiki-kanban-web](https://github.com/thiki-org/thiki-kanban-web),你需要两边配合使用。

##安装方法 

在安装前,请检查你的机器是否已经具备以下环境:  

* java 1.8;
* Gradle 3.2.x或更高版本.


### 1、配置数据库

**HSQL**

hsql是内存数据库,仅在集成测试中使用。所以,test目录下的测试你可以随意运行,无需任何配置。

**Mysql**

如果你想通过`Application.java`启动系统,并在浏览器中访问,请首先在你的机器上安装Mysql.然后,在`resources/config`中添加你本地的配置,具体配置方法请参考已经存在的`local_tao`|`local_zz`.

### 2、如何构建
通过gradle构建可独立运行的jar包：
```
./gradlew clean build
```

如过需要在构建时跳过测试，运行：

```
 ./gradlew clean build -x test
```

通过`./gradlew` 构建时，gradle会默认从官网下载。而由于众所周知的原因，下载会非常慢。为了加快构建速度，你可以先将gradle包下载到本地，并将`gradle/wrapper/gradle-wrapper.properties`中的`distributionUrl`指向你本地的gradle.

### 2、启动系统

目前启动有三种方式:

* 从Application.java直接运行
* 打成 jar后运行
* 通过gradle运行

通过gradle运行:

```
 ./gradlew bootRun
```

需要注意的是，`./gradlew bootRun`运行时的默认配置文件是`resources/config/application-local.properties`，你可以通过 `--spring.profiles.active`指定你的配置文件。通过`Application`的`main`函数运行时也同样需要指定配置文件。

另外，在通过`./gradlew clean build`构建的jar中并没有配置文件，这是出于生产环境的安全考虑，避免在代码库中暴露生产环境信息。当你在生产环境部署时，你需要把配置文件放在jar包所在的目录下，系统启动时会自动读取。

### 3、访问Web APIs

```
http://localhost:8096/kanban/entrance
 
{
  "_links": {
    "self": {
      "href": "http://localhost:8096/kanban/entrance",
      "actions": {
        "read": {
          "isAllowed": true
        }
      }
    },
    "publicKey": {
      "href": "http://localhost:8096/kanban/publicKey",
      "actions": {
        "read": {
          "isAllowed": true
        }
      }
    },
    "passwordRetrievalApplication": {
      "href": "http://localhost:8096/kanban/passwordRetrievalApplication",
      "actions": {
        "read": {
          "isAllowed": true
        }
      }
    }
  },
  "description": "Welcome!"
}
```

一般情况下，通过RESTAssured在开发时便已经满足对系统访问的需求。如果需要在系统之外访问，可以使用一些REST Client工具。谷歌和火狐浏览器都提供了类似插件。

### 4、Redis相关

为提高系统运行速度，我们使用了Redis作为缓存服务器。在现有的配置中，集成测试运行时已通过`redis.enabled=false`跳过Redis.如果你在运行系统时，而你的机器没有安装Redis或者纯粹想跳过它，你也可以在配置文件添加该属性跳过。

### 5、部署相关

当前项目已经在生产环境部署，点击访问[思奇精益看板](http://www.thiki.org)。当然，众多特性仍没有上线，我们仍在开发中，并通过[Go CD](https://www.go.cd)每日部署。

## API文档

系统在运行测试时可以根据测试用例生成相应的[文档](https://github.com/thiki-org/thiki-kanban-backend/blob/go/src/test/resources/APIDocument.md)。

## 学习资料

* RESTful

    [Architectural Styles and the Design of Network-based Software Architectures](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)
    
    [理解本真的REST架构风格](http://www.infoq.com/cn/articles/understanding-restful-style)

* [spring-hateoas](http://projects.spring.io/spring-hateoas/)

* [spring-boot](http://projects.spring.io/spring-boot/)

* [hsql](http://hsqldb.org)

* [rest-assured](https://github.com/rest-assured/rest-assured)

    





