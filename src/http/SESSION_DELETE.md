### 简介

删除会话

- 删除会话
- 删除会话订阅者

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/deleteSession
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
    session_delete_time:#{session_modify_time}
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `session_id`
    - 会话`id`

- `session_delete_time`
    - 删除时间
    - 时间戳

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/SESSION_DELETE.png?raw=true
