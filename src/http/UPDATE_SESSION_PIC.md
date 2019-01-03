### 简介

更新会话图标

### 请求地址
```
http://#{ip}:#{port}/plugins/tlim/updateSessionPic
```

### 参数

- `session_id{string(64)}`
    - 会话`id`

- `session_pic{string(64)}`
    - 会话图标路径

### 返回值
```
{
    result:#{result},
    result_detail:#{result_detail},
    session_id:#{session_id},
    session_pic:http://#{ip}:#{port}/#{project}/uploader/loadImg?pic=#{session_pic}
}
```

- `result`
    - 成功或失败
    - 取值范围:`ok`/`fail`

- `result_detail`
    - 错误信息

- `session_id`
    - 会话`id`



### 图例

![Alt text][demo1]

[demo1]:https://github.com/GepengCn/tlim/blob/master/images/UPDATE_SESSION_PIC.png?raw=true
