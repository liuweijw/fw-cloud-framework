# fw-cloud-framework
基于springcloud全家桶开发分布式框架（支持认证授权、常见服务监控、链路监控、异步日志、redis缓存等功能），实现基于Vue全家桶等前后端分离项目工程

# 技术模块相关说明
1、 fw-cloud-system 模块
- fw-cloud-system-eureka 服务注册中心
- fw-cloud-system-config 服务配置和发现
- fw-cloud-system-gateway zuul服务网关（外部接口接入入口）
- fw-cloud-system-auth 权限接入服务(支持oauth2、单点登录)
- fw-cloud-system-xxx 服务状态监控、zipkin、elk监控、缓存、日志等监控

2、fw-cloud-core 基础公共模块
- fw-cloud-core-beans 常用全局bean
- fw-cloud-core-commons 常量、切面等
- fw-cloud-core-configuration 基础配置
- fw-cloud-core-exception Exception定义
- fw-cloud-core-utils 常用工具类

3、fw-cloud-business 服务模块
- fw-cloud-business-admin 权限管理等接口服务
- fw-cloud-business-client 前端接口服务

4、fw-cloud-ui-xxx 前端模块
- 基于element ui 的Vue

# 开发环境
- Redis
- MySQL
- RabbitMQ
- Java8

# 说明
- xxx 待开发和完善的模块
