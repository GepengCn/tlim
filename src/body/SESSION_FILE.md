### 简介

多人会话文件消息

### 消息类型

`MTS-002`

### 简略结构
```
[
    {
        fileId:#{fileId},
        session_id:#{session_id}
    }
]
```
### 限制

`fileId`
- 类型:`string(64)`

`session_id`
- 非空
- 类型:`string(64)`

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
    msg_type:'MTS-002',
    msg_time:#{msg_time},
    body:[{
             fileId:#{fileId},
             session_id:#{session_id}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTT_002.png?raw=true
