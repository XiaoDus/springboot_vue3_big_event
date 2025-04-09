
---

## 🚀 项目简介：SpringBoot + Vue3 大事件管理系统

该项目旨在实现一个完整的「大事件（Big Event）」记录、管理与可视化平台，典型应用于待办事项、事件跟踪、工作记录等业务场景。

---

### 🧱 项目结构

```bash
springboot_vue3_big_event/
├── springboot_vue3/    # 后端 Spring Boot 项目
│   ├── controller/     # 事件、用户等控制器
│   ├── service/        # 服务层逻辑
│   ├── entity/         # 实体类（数据库模型）
│   ├── mapper/         # MyBatis 持久层接口
│   └── application.yml # 配置文件（数据库、端口等）

├── vue3/               # 前端 Vue 3 项目
│   ├── views/          # 页面组件（如登录页、事件页）
│   ├── api/            # 接口封装
│   ├── store/          # Pinia 状态管理
│   └── main.js         # 应用入口
```

---

### 🔧 技术栈一览

| 层级       | 技术栈                    |
|------------|---------------------------|
| 前端       | Vue 3 + Vite + Pinia + Axios + Element Plus |
| 后端       | Spring Boot + MyBatis Plus + Lombok |
| 数据库     | MySQL                     |
| 接口规范   | RESTful API               |
| 构建部署   | Maven + Vite              |

---

### ✨ 核心功能模块

- ✅ 用户注册 / 登录（含 Token 验证）
- ✅ 事件增删改查（CRUD 操作）
- ✅ 分类管理（为事件分类）
- ✅ 时间线展示、进度控制（可拓展）
- ✅ 响应式 UI，移动端兼容

---

### 🧪 启动方式

#### 后端（Spring Boot）：

```bash
cd springboot_vue3
mvn spring-boot:run
```

确保你已正确配置 MySQL 数据库连接与依赖。

#### 前端（Vue 3）：

```bash
cd vue3
npm install
npm run dev
```

---

### 📈 优化方向

- ✅ 接入 Swagger 或 Knife4j 接口文档
- ✅ 前端统一异常处理与权限拦截器（基于路由守卫）
- ✅ 后端加入日志追踪、接口限流
- ✅ Docker 一键部署（前后端整合）

---
