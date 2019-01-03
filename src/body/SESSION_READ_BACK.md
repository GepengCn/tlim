### 简介

已读回执

### 消息类型

`MTS-100`

### 简略结构
```
[
    {
        session_id:#{session_id},
        msg_id:#{msg_id}
    }
]
```
### 限制

`msg_id`
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
    msg_type:'MTS-100',
    msg_time:#{msg_time},
    body:[{
             session_id:#{session_id},
             msg_id:#{msg_id}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTT_100.png?raw=true
