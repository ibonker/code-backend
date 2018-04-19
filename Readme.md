﻿# Hotpotmaterial Code - Restful风格的项目代码生成器

## 说明

该代码生成器旨在为团队提供快速的前后端代码，后端代码基于Spring Boot + Spring Security + Mybatis，前端代码基于Spring Boot + Spring Security

## 如何搭建

该项目使用`Gradle`进行构建后端服务，需要先配置`Gradle`环境:

下载源代码运行`gradle build -x test`

在项目根目录下找到`build/libs/hotpotmaterial-code2-0.0.1-SNAPSHOT`

运行该jar包`java -jar hotpotmaterial-code2-0.0.1-SNAPSHOT`

访问`http:localhost:8080/swagger-ui.html`看项目是否启动成功

后端服务启动成功过后，请查看[前端服务](https://github.com/hotpotmaterial/code-frontend)启动

## 如何使用代码生成器

[代码生成器使用](./doc/how_to_use.md)

## 如何使用生成的后端代码

[使用代码生成器生成的后端代码](./doc/how_to_use_backend_code.md)

## 如何使用生成的前端代码

[使用代码生成器生成的前端代码](./doc/how_to_use_frontend_code.md)

