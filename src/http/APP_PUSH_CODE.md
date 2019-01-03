### 简介

更新appPushCode;根据此值做极光推送

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/registerAppPushCode
```

### 参数

- `user_id{string(64)}`
    - 用户`id`

- `registrationId{string(64)}`
    - 极光推送注册`id`

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/dev/images/APP_PUSH_CODE.png?raw=true
