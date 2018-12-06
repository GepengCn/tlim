### 简介

新增会话

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/addSession
```

### 参数

- `session_type{string(64)}`
    - 会话类型

- `request_user{string(64)}`
    - 群主、会话创建人

- `subscribers{Array}`
    - 成员列表
    - `subscriber{object}`
      - 用户`id`
      - `user_id{string(64)}`

```
subscribers:[
      {
          user_id:#{user_id}
      }
]
```

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    session_id:#{session_id},
    session_name:#{session_name},
    session_type:#{session_type},
    session_user:#{session_user},
    session_create_time:#{session_create_time},
    subscribers:[
      {
          user_id:#{user_id}
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
    - 群主或创建用户
- `session_create_time`
    - 创建时间
    - 时间戳
- `subscribers`
    - `Array`
    - 群成员
- `user_id`
    - 成员`id`