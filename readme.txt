crawler-plus

crawler-plus是一款学习型JavaWeb多功能前后端完全分离的管理系统，该系统包含如下若干功能：

功能简介：
-------------------------
1. 用户管理
2. 角色管理
3. 系统API管理
4. 系统监控
5. 实现了一个简单的爬虫模块，模块中的功能有：爬取简单页面元素，爬取二维码信息，爬取微信公众号标题，
爬取新闻消息（目前可以爬取热门的NBA和CBA新闻（用户可以新增爬取规则）,爬取债券市场（包括从对应网站下载相应Pdf文件到本地））
6. 实现了一个简单的邮件模块，用户根据此模块发送邮件

所用框架
-------------------------
前端：
1. H+(一款基于Bootstrap的后台管理框架)
2. Jquery
3. Bootstrap
4. Bootstrap-Table
5. Layer.js
6. ZTree.js
7. HandleBar.js
8. SessionStorage
......

后端：
-------------------------
1. SpringIO
2. SpringBoot
3. SpringCloud
4. MyBatis
5. Spring
6. Hibernate-Validator
7. Swagger
8. Jsoup
9. Ehcache
10. Spring-Scheduled
11. Druid
12. Hikari-CP
13. Redis(缓存服务器)
14. Nginx + Vsftpd (图片服务器)
......

项目模块介绍：
crawler-backend：后台API部分
crawler-cache：缓存模块
crawler-captcha-producer：验证码微服务
crawler-config-server：基于springcloud的配置文件管理微服务
crawler-core：核心方法和类工程
crawler-eureka-server：eureka微服务
crawler-mail：邮件服务
crawler-redis-service-provider：redis接口微服务
crawler-schedule-task：定时任务工程
crawler-sleuth-server：sleuth微服务
crawler-zuul-server：API网关
crawler_frontend：后台管理系统前台
it4u：新闻展示模块静态html工程
it4u-thymeleaf：新闻展示模块thymeleaf工程

项目特点：
--------------------------
1. 该项目是彻彻底底的前后端分离项目，前端工程和后端工程可以单独部署
2. 基于SpringBoot,采用Maven依赖的方式，简化了大量配置代码，方便部署
3. 使用部分SpringCloud模块的功能，如Eureka，Feign，Ribbon，Hystrix, zuul，sleuth，Config等，对模块进行拆分形成微服务,实现配置中心集中管理微服务配置
4. 支持多环境部署（开发，测试，生产环境）
5. 后台部分模块采用Redis缓存数据，提高响应速度
6. 采用Logback框架记录日志，对于开发，测试，生产环境的不同特点，采用不同日志级别
7. 该项目实现了一个简单的基于用户-角色-权限的管理模型
8. 该项目实现了一个简单的爬虫应用，基于Jsoup框架，用户可以在此基础上进行开发
9. 该项目采用了统一的异常处理机制
10. 采用Spring-Scheduled定时去网络抓取新闻数据
11. 前端封装了一个Ajax通用方法，简化Ajax操作
12. 登录验证码支持
13. 前后端交互基于Token模式校验用户身份，使用独创的@RequireToken注解结合AOP技术共同校验
14. 加入二次验证用户权限机制，使用独创的@RequirePermissions注解，防止用户越权访问没有权限的API
15. 支持双数据源配置，分别使用Druid和Hikari-CP数据库连接池管理这两个数据源
16. 系统集成Hutool工具包，简化常见的工具类操作
......

运行步骤：
---------------------------
后端：
1.本地创建spider数据库
2.将crawler-backend目录下的resources/db/migrations下的所有Sql文件按照版本号依次导入Mysql数据库中，（也可以省略该步骤，因为系统中已经集成Flyway）
3.依次运行如下java文件：
crawler-eureka-server工程中的CrawlerEurekaServerApplication.java
crawler-backend工程中的CrawlerApplication.java
crawler-captcha-producer工程中的CrawlerCaptchaProducerApplication.java
crawler-redis-service-provider工程中的CrawlerRedisServiceProviderApplication.java
crawler-zuul-server工程中的CrawlerZuulServerApplication.java

前端：
---------------------------
1. 运行crawler_frontend下的login.html
2. 将crawler_frontend/common/commonUtil.js中的
var comm = {
    url : 'http://127.0.0.1:8088/'
}
根据自己的后端环境修改正确
3. 生产环境建议部署Nginx下
4. 登录默认用户名：admin， 密码：123456

注意：
---------------------------
************生产环境下请将crawler-backend中的application.yml中的active: dev改成active: prod， 并且将application-prod.yml中的server.address，server.port和mysql链接地址改正确，
并将crawler-captcha-producer和crawler-eureka-server工程中的application.yml相应的host和port修改正确（使用验证码功能前提下）************
1.建议生产环境下采用Jar包运行，打Jar包命令：clean package
2.完整功能需要有Redis环境，Nginx环境，Vsftpd环境
备注：导出Mysql数据库脚本命令：mysqldump -uroot -proot spider > spider.sql