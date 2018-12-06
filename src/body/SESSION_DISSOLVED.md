### 简介

系统消息之解散群组

### 消息类型

`MTS-107`

### 简略结构
```
[
    session_id:#{session_id}
]
```
- `session_id`:会话`id`

### 限制

`session_id`
- 非空
- 类型:`string`
- 长度限制:`64`

### 完整结构
```
{
    compress:#{compress},
    encode:#{encode},
    encrypt:#{encrypt},
    version:#{version},
    msg_from:#{msg_from},
    msg_to:#{msg_to},
    msg_id:#{msg_id},
    msg_type:'MTS-107',
    msg_time:#{msg_time},
    body:[
         {
             session_id:#{session_id}
         }
    ]
}
```
