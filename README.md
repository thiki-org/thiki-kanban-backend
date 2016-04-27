基础技术涉及spring-hateoas\spring-boot,数据层面采用Mybatis+mysql.


##安装方法 
1、下载[dolphin](https://github.com/xubitao/dolphin).

   本项目中的众多公共依赖都在dolphin中，所以请务必先下载dolphin，并install到本地；

2、配置pirate.properities中的jdbc信息,默认的数据库配置可直接使用,但请勿破坏表结构；如果使用自己的MySQL数据库，`/src/main/resources/init_db.sql`中有创建表所需的SQL语句；

3、可以在IDE中从Application类的main方法中直接启动，也可以通过`mvn package`打成jar包后运行；

4、契约平台完整使用时，需要配备相应的客户端，客户端程序暂不提供，目前仅供服务端的学习和研究。
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
