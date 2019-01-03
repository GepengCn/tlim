### 简介

审批

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/approval
```

### 参数

- `params{json}`
```
    params:[
        {
            bizId:#{bizId},
            docTitle:#{docTitle},
            solId:#{solId},
            spType:'1052M001',
            wzType:#{wzType},
            taskId:#{taskId}
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

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/APPROVAL.png?raw=true
