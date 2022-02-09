# webfast

#### 介绍
SpringBoot与FreeMarker技术建立WebApp基础功能快速开发平台

#### 源代码结构

``` lua
webfast
├── webfast-common -- 工具类及通用代码
├── webfast-kernel-dao-impl-jdbc -- 核心模块定义的DAO接口的Spring-JDBC实现
├── webfast-kernel-service-impl -- 核心模块定义的服务接口的具体实现
├── webfast-kernel -- 应用功能核心接口定义
├── webfast-starter-demo -- 框架搭建时的测试代码
├── webfast-support-shiro -- Shiro封装公用模块
├── webfast-webapp-freemarker -- 应用视图FreeMarker代码初始化及页面实现
└── webfast-webapp -- SpringMVC的Controller实现Web的控制逻辑
```

### 技术选型

#### 后端技术

| 技术                 | 版本                | 说明                | 官网                                                 |
| -------------------- | -------------------| ------------------- | ---------------------------------------------------- |
| SpringBoot           | 2.2.13             | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| Shiro                | 1.7.0              | 认证和授权框架      | https://shiro.apache.org/                             |
| Druid                | 1.2.8              | 数据库连接池        | https://github.com/alibaba/druid                     |

