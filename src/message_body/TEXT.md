### 消息类型
`MTT-000`

### `body`
```
[
    {
        text:#{text}
    }
]
```
- 类型:string
- 长度限制:3000
- 不支持emoji

### 完整`JSON`结构
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

### demo

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/dev/images/MTT_000.png?raw=true
