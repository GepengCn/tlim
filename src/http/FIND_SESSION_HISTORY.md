### 简介

查询会话历史消息

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/findSessionHistory
```

### 参数

- `session_id{string(64)}`
    - 会话`id`

- `start{int}`
    - 起始序号

- `length{int}`
    - 长度

> 注:`length`如果为`max`则相当于`Integer.MAX_VALUE`
### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    session_id:#{session_id},
    total:#{total},
    message_list: [
    {
        body:#{body},
        msg_from:#{msg_from},
        msg_id:#{msg_id},
        msg_time:#{msg_time},
        msg_to:#{msg_to},
        msg_type:#{msg_type},
        session_id:#{session_id}
    }]
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `session_id`
    - 会话`id`

- `total`
    - 消息总数

- `message_list`
    - 消息数组



### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/dev/images/FIND_SESSION_HISTORY.png?raw=true
