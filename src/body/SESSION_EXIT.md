### 简介

系统消息之退出会话

### 消息类型

`MTS-104`

### 简略结构
```
[
    session_id:#{session_id},
    user_id:#{user_id},
    user_name:#{user_name}
]
```
- `session_id`:会话`id`
- `user_id`:成员`id`
- `user_name`:成员姓名

### 限制

`session_id`
- 非空
- 类型:`string(64)`

`user_id`
- 非空
- 类型:`string(64)`

`user_name`
- 非空
- 类型:`string(256)`

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
    msg_type:'MTS-104',
    msg_time:#{msg_time},
    body:[
         {
             session_id:#{session_id},
             user_id:#{user_id},
             user_name:#{user_name}
         }
    ]
}
```

### 图例

![Alt text][demo]

[demo]:https://github.com/GepengCn/tlim/blob/master/images/MTS_104.png?raw=true
