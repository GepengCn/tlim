### 简介

语音消息

### 消息类型

`MTT-003`

### 简略结构
```
[
    {
        fileId:#{fileId}
    }
]
```
### 限制

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
    msg_type:'MTT-003',
    msg_time:#{msg_time},
    body:[{
             fileId:#{fileId}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTT_003.png?raw=true
