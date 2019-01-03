### 简介

新增用户接口

> 注意:如果不用Openfire默认开启的同步接口(会定时从用户表同步用户),
`Openfire/conf/openfire.xml`的`<jive>`标签下新增`<tlim><userAsync>false</userAsync></tlim>`

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/addUser
```

### 参数

- `userList{json}`
     ```
     [
        {
            "user_id":#{user_id},
            "user_name":#{user_name}
        }
     ]
     ```

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

[demo1]:https://github.com/GepengCn/tlim/blob/dev/images/ADD_USER.png?raw=true
