### 简介
新增会话

### 请求地址

`http://ip:port/plugins/tlim/addSession`

### 请求参数


`session_type`
- 会话类型
- `string(64)`

`request_user`
- 群主、会话创建人
- `string(64)`

`subscribers`
- 成员列表
- array
  - 用户`id`
  - `user_id`
  - `string(64)`
