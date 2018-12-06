### 简介

新增会话

### 请求地址
```http
http://#{ip}:#{port}/plugins/tlim/addSession
```

### 参数

- `session_type{string(64)}`
    - 会话类型

- `request_user{string(64)}`
    - 群主、会话创建人

- `subscribers{Array}`
    - 成员列表
    - `subscriber{object}`
      - 用户`id`
      - `user_id{string(64)}`

```json

subscribers:[
      {
          user_id:'adwdasc-as9das9d8w-8asd8a'
      },
      {
          user_id:'adwdasc-as9das9d8w-8asd8b'
      },
      {
          user_id:'adwdasc-as9das9d8w-8asd8c'
      }
]
```