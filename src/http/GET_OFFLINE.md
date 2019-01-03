### 简介

离线消息

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/getOffline
```

### 参数

- `user_id{string(64)}`
    - 用户`id`

- `msg_id{string(64)}`
    - 消息id
    - 系统会查询此消息时间点之后的离线消息

- `getThenClear{int}`
    - 0:获取并清空离线消息
    - 1:获取但是不清空离线消息

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    message_list:[
        {
            body:#{body},
            msg_from:#{msg_from},
            msg_id:#{msg_id},
            msg_time:#{msg_time},
            msg_to:#{msg_to},
            msg_type:#{msg_type},
            msg_status:#{msg_status},
        }
    ]
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `msg_status`
    - 0:移动端未收,web未收
    - 1:移动端未收,web已收



### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/GET_OFFLINE.png?raw=true
