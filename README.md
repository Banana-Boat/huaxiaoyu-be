### <div align=center><img src="./readme-image/logo.png" width="18%"></div>

# <center>华小遇</center>

一款主打华科校内交友的 APP！

- 同是 Huster，交友对象更靠谱；
- 通过算法匹配对象，保证志趣相投；
- 提供辅助聊天功能，助你轻松破冰，避免尴尬。

## 界面演示

#### 1. 登录 / 注册 / 我的

<div class="flexible">
<img src="./readme-image/login.png" width="25%">
<img src="./readme-image/register.png" width="25%">
<img src="./readme-image/mine.png" width="25%">
</div>

#### 2. 匹配 / 聊天 / 推荐话题

<div class="flexible">
<img src="./readme-image/match.gif" width="25%">
<img src="./readme-image/chat.gif" width="25%">
<img src="./readme-image/topic.gif" width="25%">
</div>

## 架构图

```mermaid
flowchart LR
  id_client(((Clients))) --HTTP--- id_main(Main Service\nAPI网关 / 鉴权\n用户相关 / IM相关)
  id_client --Websocket--- id_main

  subgraph Huaxiaoyu Services
  id_main --RPC--- id_chatbot(Matching Service\n聊天对象匹配)
  id_main --RPC--- id_scraping(Scraping Servuce\n辅助聊天话题抓取)
  end

  subgraph DataBase
  id_main -.- id_mysql[(Mysql DB)]
  id_chatbot -.- id_mysql
  id_main -.- id_redis[(Redis)]
  id_main -.- id_oos[(Cloud OSS)]
  id_scraping -.- id_mysql
  end

  subgraph Nacos 注册中心
  id_nacos(服务发现 / 配置管理) --HTTP--- id_chatbot
  end
```

## ER 图

```mermaid
erDiagram
    USER }|--o{ NOTICE : participate
    USER }|--o{ MESSAGE : participate
    USER }|--o{ RELATIONSHIP : participate

    USER {
        int id UK
        string username PK "用户名"
        string nickname "昵称"
        string password "密码"
        int age "年龄"
        string sex "性别"
        string department_code "学院代号"
        string head_photo "头像URL"
        string phone_num "电话"
        datetime created_at
        datetime updated_at
    }

    NOTICE {
        int id PK
        int send_id "发送者ID"
        int receive_id "接收者ID"
        int type "通知类型"
        int result "回复内容"
        int status "通知状态"
        datetime created_at
        datetime updated_at
    }

    MESSAGE {
        int id PK
        int send_id UK "发送者ID"
        int receive_id UK "接收者ID"
        string content "消息内容"
        int status "消息状态"
        datetime created_at
        datetime updated_at
    }

    RELATIONSHIP {
        int id PK
        int send_id UK "发送者ID"
        int receive_id UK "接收者ID"
        int status "关系状态"
        datetime created_at
        datetime updated_at
    }

    TOPIC {
        int id PK
        string title UK "话题标题"
        string type "话题类型"
        string content "话题内容"
        string options "选项"
    }
```

## 主要依赖

- [**Spring Cloud Alibaba**](https://github.com/alibaba/spring-cloud-alibaba)
- [**Nacos**](https://github.com/alibaba/Nacos)
- [**OpenFeign**](https://github.com/OpenFeign/feign)
- [**Spring Websocket**](https://spring.io/guides/gs/messaging-stomp-websocket)
- [**Spring Security**](https://github.com/spring-projects/spring-security)
- [**MyBatis-Plus**](https://github.com/baomidou/mybatis-plus)
- [**Alibaba Cloud OSS**](https://www.alibabacloud.com/zh/product/object-storage-service)

## 接口文档

[**API 文档**](https://apifox.com/apidoc/shared-73747cdd-880f-4b68-951f-7294685be27e)

## 服务端部署

- 修改根目录下 compose.yaml
- 应用容器化

  - 方法一：在服务器端拉取代码，执行`make build_images`，打包镜像
  - 方法二：本地执行`make build_push_multi`，打包多平台镜像并推至 hub

- 服务端执行`docker compose up -d`
