### 简介

文本消息

### 消息类型
`MTT-000`

### 简略结构
```
[
    {
        text:#{text}
    }
]
```
### 限制

- 类型:`string(3000)`
- 不支持`emoji`

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
             text:#{text}
          }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/master/images/MTT_000.png?raw=true
