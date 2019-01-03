### 简介

查询订阅者列表

- 会话类型

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/findSubscribers
```

### 参数

- `session_id{string(64)}`
    - 会话`id`

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    session_id:#{session_id},
    session_name:#{session_name},
    session_type:#{session_type},
    session_user:#{session_user},
    subscribers:[
        {
            user_id:#{user_id},
            user_name:#{user_name},
            acct_login:#{acct_login},
            pic:#{pic},
            session_id:#{session_id},
            ts:#{ts},
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

- `user_id`
    - 订阅者id

- `user_name`
    - 订阅者姓名

- `acct_login`
    - 订阅者登录名

- `pic`
    - 订阅者图标

- `ts`
    - 订阅者创建时间戳

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/dev/images/FIND_SUBSCRIBERS_LIST.png?raw=true
