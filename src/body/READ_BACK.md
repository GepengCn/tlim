### 简介

已读回执

### 消息类型

`MTT-100`

### 简略结构
```
[
    {
        msg_id:#{msg_id}
    }
]
```
### 限制

`msg_id`
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
    msg_type:'MTT-100',
    msg_time:#{msg_time},
    body:[{
             msg_id:#{msg_id}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTT_100.png?raw=true
