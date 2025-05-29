# 智能简历筛选系统

本系统是为腾云启航开发的智能简历筛选平台，旨在提高招聘效率和精准度。

## 系统架构

系统采用微服务架构，主要包含以下模块：

- resume-common：公共模块，包含通用工具类和配置
- resume-gateway：网关服务，负责请求路由和负载均衡
- resume-auth：认证授权服务，处理用户登录和权限管理
- resume-parser：简历解析服务，负责简历文本分析和信息提取
- resume-matching：岗位匹配服务，负责简历评分和岗位匹配
- resume-analysis：数据分析服务，负责招聘数据统计和可视化
- resume-interview：面试辅助服务，包含电子签约和面试管理

## 技术栈

- 后端：Spring Cloud、Spring Boot、MyBatis-Plus
- 前端：Vue.js、Element UI
- 数据库：MySQL、MongoDB
- AI/NLP：Stanford CoreNLP、HanLP
- 机器学习：TensorFlow、Weka
- 大数据：Hadoop、Spark
- 可视化：ECharts、Highcharts

## 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- MongoDB 4.4+
- Node.js 14+
- Redis 6.0+

## 快速开始

1. 克隆项目
```bash
git clone [项目地址]
```

2. 配置数据库
- 创建MySQL数据库
- 配置MongoDB连接
- 修改application.yml中的数据库配置

3. 启动服务
```bash
# 编译项目
mvn clean install

# 启动服务（按顺序）
cd resume-gateway
mvn spring-boot:run

# 启动其他服务...
```

4. 访问系统
- 后台管理：http://localhost:8080
- API文档：http://localhost:8080/swagger-ui.html

## 主要功能

1. 智能简历筛选
   - 自动提取简历信息
   - 简历评分排序
   - 关键词匹配

2. 岗位匹配
   - 岗位能力图谱
   - 匹配度分析
   - 推荐排序

3. 数据分析
   - 招聘数据统计
   - 渠道分析
   - 趋势预测

4. 面试管理
   - 电子签约
   - 面试安排
   - 候选人追踪

## 安全说明

- 所有API访问需要认证
- 敏感数据采用AES加密
- 支持角色权限管理
- 部署WAF防护

## 贡献指南

欢迎提交Issue和Pull Request

## 版权信息

Copyright © 2024 腾云启航 