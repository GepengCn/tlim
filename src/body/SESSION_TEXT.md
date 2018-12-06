### 简介

多人会话文本消息

### 消息类型
`MTS-000`

### 简略结构
```
[
    {
        text:#{text},
        session_id:#{session_id}
    }
]
```
`session_id`会话id

### 限制
`text`
- 类型:`string(3000)`
- 不支持`emoji`

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
    msg_type:'MTT-000',
    msg_time:#{msg_time},
    body:[{
             text:#{text},
             session_id:#{session_id}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTS_000.png?raw=true
