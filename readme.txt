crawler_plus

crawler_plus是一款学习型JavaWeb多功能前后端完全分离的管理系统，该系统包含如下若干功能：

功能简介：

1. 用户管理
2. 角色管理
3. 系统API管理
4. 系统监控
5. 实现了一个简单的爬虫模块，模块中的功能有：爬取简单页面元素，爬取二维码信息，爬取微信公众号标题，爬取新闻消息（目前可以爬取热门的NBA和CBA新闻（用户可以新增爬取规则））
6. 实现了一个简单的邮件模块，用户根据此模块发送邮件

所用框架

前端：

1. H+(一款基于bootstrap的后台管理框架)
2. jquery
3. Bootstrap
4. bootstrap-table
5. layer.js
6. zTree.js
7. handlebar.js
8. sessionStorage
......

后端：

1. SpringBoot 1.5.7.RELEASE
2. MyBatis
3. Spring 4.3.11.RELEASE
4. hibernate-validator
5. Swagger2
6. Jsoup 1.10.3
7. Ehcache 2.10.4
9. spring-scheduled
10.Druid
......

项目特点：

1. 该项目是彻彻底底的前后端分离项目，前端工程和后端工程可以单独部署
2. 基于SpringBoot,采用maven依赖的方式，简化了大量配置代码，方便部署
3. 支持多环境部署（开发，测试，生产环境）
4. 后台部分模块采用ehcache缓存数据，提高响应速度
5. 采用logback框架记录日志，对于开发，测试，生产环境的不同特点，采用不同日志级别
6. 该项目实现了一个简单的基于用户-角色-权限的管理模型
7. 该项目实现了一个简单的爬虫应用，基于jsoup框架，用户可以在此基础上进行开发
8. 该项目采用了统一的异常处理机制
9. 采用spring-scheduled定时去网络抓取新闻数据
10. 前端封装了一个ajax通用方法，简化ajax操作

运行步骤：

后端：
1.将crawler_backend目录下的spider.sql导入mysql数据库中
2.运行CrawlerApplication.java
注意：
************生产环境下请将application.yml中的active: dev改成active: prod， 并且将application-prod.yml中的server.address，server.port和mysql链接地址改正确************
3.建议生产环境下采用jar包运行，打jar包命令：clean package

前端：
1. 运行crawler_frontend下的login.html
2. 将crawler_frontend/common/commonUtil.js中的
var comm = {
    url : 'http://127.0.0.1:8088/'
}
根据自己的环境修改正确
3. 生产环境建议部署nginx下
4. 登录默认用户名：admin， 密码：123456