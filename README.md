# SpringBoot
本文通过学习方志朋老是的博客进行的总结<br/>
参考链接：https://blog.csdn.net/forezp/article/details/70341818


### springboot如何实现热部署
访问spring.io官网，找到所有启动器列表<br/>
https://docs.spring.io/spring-boot/docs/2.1.0.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-starter <br/>

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<optional>true</optional>
</dependency>
```
intellij idea需要修改一些配置，详见https://blog.csdn.net/hz_940611/article/details/80594788<br/>
ctrl + F9 重新编译之后，重新访问<br/>


### YMAL文件单词联想
```
<!-- 配置文件处理器，可以在配置文件中实现单词联想 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```

### @ConfigurationProperties
将yml配置文件中的属性和对象中的属性关联起来

### @PropertySource
用来指定配置文件

### @ImportResource
导入spring配置文件

### 配置文件profiles
指定配置文件，对于只有一个yml配置文件，可以通过下面这种方式
```
spring:
  profiles:
    active: pro

---
server:
  port: 8083
spring:
  profiles: dev

---
server:
  port: 8084
spring:
  profiles: pro
```

### spring.factories
spring-boot-autoconfigurate-xxx.release.jar<br/>
核心配置文件
