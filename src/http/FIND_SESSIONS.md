### 简介

查询会话列表

- 会话类型

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/findSessions
```

### 参数

- `user_id{string(64)}`
    - 订阅者、成员`id`

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    sessions:[
        {
            "session_id": #{session_id},
            "session_name": #{session_name},
            "session_type": #{session_type},
            "session_user": #{session_user},
        }
    ]
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `session_id`
    - 会话`id`

- `session_name`
    - 会话名称

- `session_type`
    - 会话类型

- `session_user`
    - 会话创建者`id`

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/FIND_SESSIONS.png?raw=true
