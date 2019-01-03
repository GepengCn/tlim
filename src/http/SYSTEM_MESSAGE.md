### 简介

系统消息

![Alt text][enum]
### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/systemMessage
```

### 参数

- `params{json}`
```
    params:[
        {
            title:#{title},
            content:#{content},
            ts:#{ts},
            url:#{url},
            mark:#{mark},
            random:#{random},
            type:#{type},
            typeName:#{typeName}
        }
    ]
```
- `msgTo{string(64)}`
    - 下一环节审批用户`id`

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

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/SYSTEM_MESSAGE.png?raw=true
[enum]:https://github.com/GepengCn/tlim/blob/master/images/SYSTEM_MESSAGE_ENUM.png?raw=true