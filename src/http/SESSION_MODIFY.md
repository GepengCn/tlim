### 简介

更新会话

- 更新会话名称
- 更新会话订阅者列表

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/modifySession
```

### 参数

- `session_id{string(64)}`
    - 会话`id`
- `session_name{string(256)}`
    - 会话名称
- `subscribers{Array}`
    - 成员列表
    - `subscriber{object}`
      1. 用户`id`
      - `user_id{string(64)}`
      2. 修改类型
      - `type{string(64)}`

```
subscribers:[
      {
          user_id:#{user_id},
          type:#{type}
      }
]
```
> `session_name`或`subscribers`皆可为空,但不能同时为空<br>
> `type`修改类型,取值范围:`add`|`del`
### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    session_id:#{session_id},
    session_modify_time:#{session_modify_time}
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `session_id`
    - 会话`id`

- `session_modify_time`
    - 修改时间
    - 时间戳

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/SESSION_MODIFY.png?raw=true
