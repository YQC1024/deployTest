# 门店扫码签字确认页（部署练手项目）

一个最小可上线的全链路示例，覆盖：

- 扫码打开页面
- 展示协议说明
- 输入姓名、手机号
- 手写签字
- 提交
- 查看提交结果

技术栈：

- 前端：Vue 3 + Vite
- 后端：Spring Boot 3
- 数据库：MySQL 8
- 部署：Docker + Docker Compose + Nginx

## 架构

```text
浏览器
  ↓
Nginx
  ├─ 前端静态资源
  └─ /api 转发到 Spring Boot
                ↓
             Spring Boot
                ↓
              MySQL
```

## 一键启动（推荐）

在项目根目录执行：

```bash
docker compose up --build -d
```

启动后访问：

- 页面地址：[http://localhost](http://localhost)
- 后端健康检查：[http://localhost/api/health](http://localhost/api/health)
- MySQL 本机端口：`3307`（容器内是 `3306`）

停止服务：

```bash
docker compose down
```

删除数据卷（清库）：

```bash
docker compose down -v
```

## 扫码演示

示例链接（可生成二维码后扫码）：

- [http://localhost/?storeCode=STORE-001](http://localhost/?storeCode=STORE-001)

你可以把 `storeCode` 换成任意门店编码。

## 目录结构

```text
.
├─ frontend/              # Vue3 + Vite 页面
├─ backend/               # Spring Boot API
├─ nginx/                 # Nginx 配置与镜像构建
├─ docker-compose.yml
└─ README.md
```

## API 说明

1. 提交签字

- `POST /api/signatures`
- 请求体：

```json
{
  "storeCode": "STORE-001",
  "customerName": "张三",
  "phone": "13800138000",
  "agreementVersion": "v1.0.0",
  "signatureDataUrl": "data:image/png;base64,..."
}
```

2. 查看结果

- `GET /api/signatures/{id}`

## 本地开发（非 Docker）

1. 启 MySQL，创建 `sign_db`
2. 启后端：

```bash
cd backend
mvn spring-boot:run
```

3. 启前端：

```bash
cd frontend
npm install
npm run dev
```
