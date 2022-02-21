# WebFast Project for RAD

#### 介绍
SpringBoot与FreeMarker技术建立WebApp基础功能快速开发平台

#### 源代码结构

``` lua
webfast
├── webfast-common                   -- 工具类及通用代码
├── webfast-kernel                   -- 核心模块定义各类接口及其相关功能设计
├── webfast-kernel-dao-jdbc          -- 核心模块定义DAO接口的Spring-JDBC实现
├── webfast-kernel-webmvc            -- 核心功能Web的控制逻辑使用SpringMVC的Controller实现
├── webfast-kernel-webui-freemarker  -- 核心功能视图FreeMarker初始化及页面实现
├── webfast-module-bigapp            -- 扩展模块定义服务接口与具体实现及MVC控制器的控制逻辑
├── webfast-module-bigapp-dao-jdbc   -- 扩展模块定义DAO接口的Spring-JDBC实现
├── webfast-module-bigapp-freemarker -- 扩展模块视图FreeMarker页面实现
├── webfast-starter-demo             -- 框架搭建时的测试代码
└── webfast-support-shiro            -- Shiro封装公用模块
```

### 技术选型

#### 后端技术

| 技术                  | 版本                | 说明                 | 官网                                                 |
| -------------------- | -------------------| ------------------- | ---------------------------------------------------- |
| SpringBoot           | 2.2.13             | 容器+MVC框架          | https://spring.io/projects/spring-boot              |
| Shiro                | 1.7.0              | 认证和授权框架         | https://shiro.apache.org/                            |
| Druid                | 1.2.8              | 数据库连接池           | https://github.com/alibaba/druid                    |
| MySQL                | 5.7.x              | 关系数据库             | https://www.mysql.com/                              |
