# ssmMvn
### Use the MAVEN build a better java web project base spring,spring-mvc and mybatis .
***
### 框架内容：
1. 基于maven + spring + spring-mvc + mybatis;
2. 基本的权限管理系统实现，分为权限、角色、用户模块；
3. 优化简化代码开发流程，提供代码生成工具，封装全局异常处理，返回处理等，尽量减少代码开发难度，让开发人员专注于业务；
4. 测试，基于junit4+spring-test， 简化测试开发；
5. 安全:
 - 完善的权限控制，使用自定义的拦截器来决定权限规则；
 - 全局事务支持，基于AOP切面编程，统一事务处理，保证数据一致性安全；
 - （规划）后台数据校验，基于JSR-349校验规则，由spring+hibernate-validator实现；
6. 代码规范，编码规范；

-----------------------------------
2015.8.10
@author Alan Zhang
***********************************
* new
 * 项目基础构建,maven环境配置；
 * 项目依赖管理 spring + spring-mvc + mybatis;
 * 项目构建管理 构建+打包+开发 maven支持；
 
* 目标
 1. 本权限提供目前是初版，并没有非要很完善，所以权限的管理设计以后还有继续优化的方向，如：
  * 当前用户所能管理的"权限/角色/用户"应当是其子孙级别。目前是：只要有系统管理权限就能管理所有的"权限/角色/用户"

-----------------------------------
2015.11.13
@author Alan Zhang
***********************************
* new
 * 重写项目README文件；
-----------------------------------
