# 日志异常消息通知的spring-boot-start框架：logpolice

## 注意：
此版本为<动态加载>报警配置版本，依赖于logbak，只需在application.properties中和logback.xml中增加配置即可

## 功能
1. 监听log输出，异步推送堆栈信息，快速接入
2. 提供推送策略，避免消息轰炸（超频/超时）
3. 提供本地数据存储，后续可根据需要增加redis
4. 提供邮件 推送类型，提供扩展接口，可扩展其它发送类型消息
5. 提供异常过滤白名单（异常类型、类）

## 快速接入
1.工程``mvn clean install``打包本地仓库。
2. 在引用工程中的``pom.xml``中做如下依赖
```
    <dependency>
        <groupId>com.rjpk</groupId>
        <artifactId>logpolice</artifactId>
        <version>1.0.1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
```
3.在logback.xml中增加appender
```
<appender name="logSendAppender" class="com.rjpk.logpolice.appender.LogSendAppender"></appender>
并加入到root下
```
4.在application.properties增加配置
```
    spring.mail.host=smtp.exmail.qq.com
    spring.mail.port=25
    spring.mail.username=***
    spring.mail.password=***
    spring.mail.default-encoding=UTF-8
    
    log.police.properties.project=monitor        #项目名称
    log.police.properties.enabled=true           #（必填）是否开启日志监控
    log.police.properties.cleanTimeInterval=180  #（必填）日志报警清除时间（重置）,根据具体需求配置
    log.police.properties.noticeTimeInterval=60  #（必填）通知间隔时间，根据具体需求配置
    log.police.properties.exceptionWhiteList=    #异常白名单 多个使用逗号隔开，根据具体需求配置
    log.police.properties.classWhiteList=        #异常类白名单，根据具体需求配置
    log.police.cache=local                       #（必填）缓存类型
    log.police.send=mail                         #（必填）消息类型
```
