### 简介

系统消息之修改会话名称

### 消息类型

`MTS-102`

### 简略结构
```
[
    {
        session_id:#{session_id},
        session_name:#{session_name}
    }
]
```
- `session_id`:会话`id`
- `session_name`:会话名称

### 限制

`session_id`
- 非空
- 类型:`string`
- 长度限制:`64`

`session_name`
- 非空
- 类型:`string`
- 长度限制:`256`

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
    msg_type:'MTS-102',
    msg_time:#{msg_time},
    body:[
         {
             session_id:#{session_id},
             session_name:#{session_name}
         }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTS_102.png?raw=true
