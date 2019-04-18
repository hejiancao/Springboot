# Springboot
springboot练习

## springboot如何实现热部署
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


## yml文件单词联想
```
<!-- 配置文件处理器，可以在配置文件中实现单词联想 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```
