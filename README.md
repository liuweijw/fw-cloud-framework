<br/>
<p align="center">
 <img src="https://img.shields.io/circleci/project/vuejs/vue/dev.svg" alt="Build Status">
 <img src="https://img.shields.io/badge/Spring%20Cloud-Edgware-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-1.5.9-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/npm/l/vue.svg" alt="License">
</p>
<br/>

# fw-cloud-framework
基于springcloud全家桶开发分布式框架（支持oauth2认证授权、统一下单、公众号服务、Shardingdbc分库分表、常见服务监控、链路监控、异步日志、redis缓存等功能），实现基于Vue全家桶等前后端分离项目工程。[其它说明](https://github.com/liuweijw/fw-cloud-framework/wiki)

# 项目特点
* 框架方面独立、模块相互之间非常独立，第三方工程服务能够轻松接入
* 服务通过网关统一接入，鉴权（网关和业务模块权限独立分开）、监控、日志、缓存等统计分析更加清晰
* 采用分布式模式，部署方便，易于扩展
* 统一下单服务接入（目前只支持微信、后续扩展支付宝等渠道）
* 微信公众号服务接入
* 采用分布式模式，[docker部署](https://github.com/liuweijw/fw-cloud-framework/wiki/08-Docker-%E9%83%A8%E7%BD%B2%E7%AE%80%E6%B4%81%E8%AF%B4%E6%98%8E)方便，易于扩展
* 前端可以基于element ui 、Vue全家桶、React 等主流，实现前后端分离

# 技术模块相关说明
1、 fw-cloud-system 模块
- fw-cloud-system-eureka  [端口1001] 服务注册中心
- fw-cloud-system-config  [端口1002] 服务配置和发现[远程配置 guide](https://github.com/liuweijw/fw-cloud-framework/wiki/02-%E9%83%A8%E5%88%86%E6%8A%80%E6%9C%AF%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E)
- fw-cloud-system-gateway [端口1003] zuul服务网关（外部接口接入入口）
- fw-cloud-system-auth    [端口1004] 权限接入服务(支持oauth2、单点登录) [查看详情 guide](https://github.com/liuweijw/fw-cloud-framework/wiki/05-Auth-%E6%A8%A1%E5%9D%97%E5%90%AF%E5%8A%A8%E8%AF%B4%E6%98%8E)
- fw-cloud-system-xxx     [端口待定] 服务状态监控
- fw-cloud-system-xxx     [端口待定] zipkin监控
- fw-cloud-system-xxx     [端口待定] elk监控
- fw-cloud-system-xxx     [端口待定] 缓存监控
- fw-cloud-system-xxx     [端口待定] 日志等监控
- fw-cloud-system-commons system各模块基础功能依赖

2、fw-cloud-core 基础公共模块
- fw-cloud-core-beans 常用全局bean
- fw-cloud-core-commons 常量、切面等
- fw-cloud-core-configuration 基础配置
- fw-cloud-core-exception Exception定义
- fw-cloud-core-utils 常用工具类

3、fw-cloud-business 业务模块服务模块
- fw-cloud-business-commons business各业务公共组件等
- fw-cloud-business-admin   [端口2002] 权限管理等接口服务
- fw-cloud-business-sso     [端口3001] 单点登录模块接入demo
- fw-cloud-business-pay     [端口2003] 微信等支付统一下单模块服务
- fw-cloud-business-wechat  [端口2004] 微信公众号模块服务
- fw-cloud-business-client  [端口待定] 前端接口服务

4、前端模块 [Vue2-Admin](https://github.com/liuweijw/Vue2-Admin)
- 基于element ui 的Vue全家桶
- Webpack3
- Axios
- Nodejs
- 初始学习Vue项目,请移步参考 [Vue2-All](https://github.com/liuweijw/Vue2-All)

# 开发部署环境
- Redis
- MySQL
- RabbitMQ
- Java8
- Lombok
- Feign
- docker
- nginx

# 项目说明
- xxx 待开发和完善的模块
- 项目部分技术配置说明[guide](https://github.com/liuweijw/fw-cloud-framework/wiki/02-%E9%83%A8%E5%88%86%E6%8A%80%E6%9C%AF%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E)
- 项目配置文件加密说明[guide](https://github.com/liuweijw/fw-cloud-framework/wiki/03-%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E5%8A%A0%E5%AF%86%E8%AF%B4%E6%98%8E)
- 服务启动说明[guide](https://github.com/liuweijw/fw-cloud-framework/wiki/04-%E6%9C%8D%E5%8A%A1%E5%90%AF%E5%8A%A8)
- Auth模块启动说明[guide](https://github.com/liuweijw/fw-cloud-framework/wiki/05-Auth-%E6%A8%A1%E5%9D%97%E5%90%AF%E5%8A%A8%E8%AF%B4%E6%98%8E)
- SSO单点登录模块说明[guide](https://github.com/liuweijw/fw-cloud-framework/wiki/06-%E5%8D%95%E7%82%B9%E7%99%BB%E5%BD%95sso-demo)
- pay统一下单模块扩展依赖[guide](https://github.com/liuweijw/cloud-commons)

# 学习交流

![image](doc/pic/cloud-framework.png)

# 常见问题
- lombok 安装
  * 下载lombok.jar包 https://projectlombok.org/download.html
  * 在eclipse.ini 配置文件最后添加：-javaagent:F:\xxx\lombok.jar 注：F:\xxx 为lombok.jar 放置的目录地址
  * 重启eclipse

- 项目导入工程遇到 maven 插件问题
~~~
You need to run build with JDK or have tools.jar on the classpath. If this occurs during eclipse build make sure you run eclipse under JDK as well (com.mysema.maven:apt-maven-plugin:1.1.3:process:default:generate-sources

解决：在eclipse.ini 配置文件添加
-vm
{path_to_jdk}\jdk{your_version}\bin\javaw.exe
~~~
- idea 安装
  * 查看地址[guide](http://blog.csdn.net/zhglance/article/details/54931430)
  * idea 导入工程方法建议参考[guide](http://blog.csdn.net/sinat_36246371/article/details/52914448)
