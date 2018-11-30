## 消息类型

### 类型字典

- `MTT-000`:文本消息
- `MTT-001`:图片消息
- `MTT-002`:文件消息
- `MTT-003`:语音消息
- `MTT-100`:单已读回执
- `MTT-101`:单消息撤回
- `MTT-102`:创建单人会话
- `MTT-200`:单聊清空历史消息
- `MTT-201`:清空历史消息回执
- `MTB-000`:审批消息
- `MTB-001`:朋友圈转发
- `MTB-100`:系统消息
- `MTS-000`:群聊文本
- `MTS-001`:群聊图片
- `MTS-002`:群聊文件
- `MTS-003`:群聊语音
- `MTS-100`:群已读回执
- `MTS-101`:群消息撤回
- `MTS-102`:修改会话名称
- `MTS-103`:移除会话订阅者
- `MTS-104`:退出会话
- `MTS-105`:创建会话
- `MTS-106`:邀请加入会话
- `MTS-107`:解散群组
- `MTC-000`:已收回执


### 系统消息模版

```
{
    title:#{title},
    content:#{content},
    ts:#{ts},
    url:#{url},
    mark:#{mark},
    random:#{random},
    type:#{type},
    typeName:#{typeName}
}
```

### 系统消息字段表

字段名 | 含义 | 类型 |长度|备注
|:---|---|---|---|---
title|标题|string|100|
content|内容|string|2000|
ts|时间|string|20|[yyyy-MM-dd:hh:mm:ss]
url|自定义跳转地址|string|300|/testController/detail?id=1
mark|备注|string|200|
random|预留字段|string|100|
type|类型|int|∞|
typeName|类型名|string|200|

### 系统消息类型

type:typeName(类型:类型名)
- 审批类型:0
- 退回:1
- 通知公告:2
- 工资条:3
- 邮件:4