### 简介

新增会话

### 请求地址

`http://#{ip}:#{port}/plugins/tlim/addSession`

### 请求参数


1. `session_type`
- 会话类型
- `string(64)`

2. `request_user`
- 群主、会话创建人
- `string(64)`

3. `subscribers`
- 成员列表
- 数组`Array`
  - 用户`id`
  - `user_id`
  - `string(64)`

```java

int a= 1;

public static void main(){
    String a = "3";
}

```