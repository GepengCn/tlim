## Openfire双向同步

#### Netty

使用高性能网络框架Netty转发同步请求

#### HTTP

使用HTTP协议

| 序号 | 请求URL | 备注 |
| ------ | ------ | ------ |
| 1 | /netty/message | websocket消息 |
| 2 | /netty/systemMessage | 系统消息 |
| 3 | /netty/approval | 审批消息 |
| 4 | /netty/addSession | 新增会话 |
| 5 | /netty/deleteSession | 删除会话 |
| 6 | /netty/dissolved | 删除会话、成员及历史消息 |
| 7 | /netty/getOffline | 获取历史消息 |
| 8 | /netty/modifySession | 修改会话 |
| 9 | /netty/registerAppPushCode | 用户绑定极光推送 |

