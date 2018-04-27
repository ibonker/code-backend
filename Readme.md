# Hotpotmaterial Code - Restful风格的项目快速开发平台

Hotpotmaterial Code快速开发平台旨在为团队提供一体化的前后端代码，后端代码基于Spring Boot + Spring Security + Mybatis，前端代码基于Vue + IView

## 特性

1. 提供一体化基于Spring Boot的后端框架
2. 提供一体化的基于Vue + IView的前段框架
3. 提供权限管理组件
4. 提供字典表组件
5. 提供单表代码下载
6. 提供界面化配置接口和非表实体 
7. 提供前端配置方式的单表和简单多表关联的增删改查功能
8. 提供单表的Excel导入导出功能
9. 界面化配置需要生成的代码，并将数据持久化于数据库（当前只支持Mysql），方便团队持续使用
10. 生成代码支持Mysql和Oracle

## 如何运行

#### 使用源码

该项目使用`Gradle`进行构建后端服务，需要先配置`Gradle`环境:

1. 下载源代码运行`gradle build -x test`

2. 在项目根目录下找到`build/libs/hotpotmaterial-code2-0.0.1-SNAPSHOT.jar`

3. 创建数据库(代码生成器自己使用的数据库)，数据库DDL在[init.sql](./init.sql)，项目本身在运行时会自动执行DDL，不需要手动执行

4. 运行该jar包`java -jar hotpotmaterial-code2-0.0.1-SNAPSHOT.jar --DB_URL=localhost:3306 --DB_USER=root --DB_PWD=123456 --GEN_ROOT_PATH=/home --DB_NAME=new_titancode_test`

程序参数：`DB_URL`为你的数据库地址，`DB_USER`为你的数据库用户名，`DB_PWD`为你的数据库密码，`DB_NAME`为你的数据库名称，`GEN_ROOT_PATH`生成代码根目录

5. 生成前端代码需要在`${GEN_ROOT_PATH}`目录下创建`ui-code`和`ui-code-temp`两个目录，`${GEN_ROOT_PATH}`的默认值为当前jar包运行目录下的`code-gen`目录。[需要生成的前端代码](https://github.com/hotpot-team/code-vue-ui)需要全部放置于`ui-code`目录下

6. 访问`http://localhost:8085/swagger-ui.html`看项目是否启动成功

7. 后端服务启动成功过后，请查看[前端服务](https://github.com/hotpot-team/code-frontend)启动

#### 下载jar包

1. 创建数据库(代码生成器自己使用的数据库)

2. 下载[项目jar包](https://github.com/hotpot-team/code-backend/releases)，该jar包包含了前端静态资源，不需要另行启动前端服务

3. 添加参数运行jar包

4. 访问`http://localhost:8085`看项目是否启动成功

#### 使用docker

请静候佳音~~

## 已知Bug，将在下个版本修复

1. 只提供了Mysql的安全管理组件初始化脚本

## 接下来应该要做的事情

1. 优化实体配置和API配置界面
2. 添加自定义模板功能
3. 添加代码预览功能

更多需求欢迎在Issue中提出，我们将根据需求持续改进我们的版本

## 如何使用代码生成器

[代码生成器使用](./doc/how_to_use.md)

## 如何使用生成的后端代码

[使用代码生成器生成的后端代码](./doc/how_to_use_backend_code.md)

## 如何使用生成的前端代码

[使用代码生成器生成的前端代码](./doc/how_to_use_frontend_code.md)

