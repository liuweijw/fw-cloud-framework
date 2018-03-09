# fw-cloud-framework
基于springcloud全家桶开发分布式框架（支持oauth2认证授权、Shardingdbc分库分表、常见服务监控、链路监控、异步日志、redis缓存等功能），实现基于Vue全家桶等前后端分离项目工程

# 技术模块相关说明
1、 fw-cloud-system 模块
- fw-cloud-system-eureka  [端口1001] 服务注册中心
- fw-cloud-system-config  [端口1002] 服务配置和发现
- fw-cloud-system-gateway [端口1003] zuul服务网关（外部接口接入入口）
- fw-cloud-system-auth    [端口1004] 权限接入服务(支持oauth2、单点登录)
- fw-cloud-system-xxx     [端口待定] 服务状态监控
- fw-cloud-system-xxx     [端口待定] zipkin监控
- fw-cloud-system-xxx     [端口待定] elk监控
- fw-cloud-system-xxx     [端口待定] 缓存监控
- fw-cloud-system-xxx     [端口待定] 日志等监控

2、fw-cloud-core 基础公共模块
- fw-cloud-core-beans 常用全局bean
- fw-cloud-core-commons 常量、切面等
- fw-cloud-core-configuration 基础配置
- fw-cloud-core-exception Exception定义
- fw-cloud-core-utils 常用工具类

3、fw-cloud-business 业务模块服务模块
- fw-cloud-business-admin   [端口2002] 权限管理等接口服务
- fw-cloud-business-client  [端口待定] 前端接口服务
- fw-cloud-business-commons 业务公共组件等

4、前端模块 [Vue2-Admin](https://github.com/liuweijw/Vue2-Admin)
- 基于element ui 的Vue全家桶
- webpack3
- axios
- ...
- 初始学习Vue项目,请移步参考 [Vue2-All](https://github.com/liuweijw/Vue2-All)

# 开发环境
- Redis
- MySQL
- RabbitMQ
- Java8
- ...

# 说明
- xxx 待开发和完善的模块

# 常见问题
- lombok 安装
  * 下载lombok.jar包 https://projectlombok.org/download.html
  * 在eclipse.ini 配置文件最后添加：-javaagent:F:\xxx\lombok.jar 注：F:\xxx 为lombok.jar 放置的目录地址
  * 重启eclipse
- idea 安装
  * 查看地址[guide](http://blog.csdn.net/zhglance/article/details/54931430)
