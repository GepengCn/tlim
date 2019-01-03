![Alt text][symbol]
-------------------
<div align="center">

[![](https://img.shields.io/badge/doc-2.1.0-green.svg)](https://github.com/GepengCn/tlim/blob/dev/src/README.md)
[![](https://img.shields.io/badge/download-v1.1-blue.svg)](https://github.com/GepengCn/tlim/releases/download/V1.1/openfire_V_1_1.zip)
[![](https://img.shields.io/badge/msg-type-yellowgreen.svg)](https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_TYPE.md)
[![](https://img.shields.io/badge/doc-old-red.svg)](https://github.com/GepengCn/tlim/blob/master/README.md)
[![](https://img.shields.io/badge/demo-coolweb-lightgrey.svg)](http://coolweb.club)

</div>

## 简介


- 这是基于`Openfire`的即时通讯插件,使用`XMPP`协议作为消息的载体，内部消息封装成`JSON`格式。
- 重写了群聊、离线消息、消息回执等功能。
- 使用自定义`JSON`协议同时在`Web`、`ios`和`android`进行即时消息交互。
- 弃用了`Openfire`自带的数据库连接池与`JDBC`组件，使用`MyBatis`搭配`Druid`连接池并且使用二级缓存大幅度优化了性能。
- 同时支持`MySQL`、`Oracle`和`SQLServer`。

## 功能概览

![Alt text][openfire]


## 消息协议

使用`XMPP`协议作为消息传输的标准，辅以自定义`JSON`协议。

### `XMPP`协议

默认使用`type='chat`'作为消息载体,分别实现`Plugin`、`PacketInterceptor`接口以插件的形式拦截消息,然后自定义处理。

```xml
<message type="#{type}" from="zhangsan#{domain}" to="lisi#{domain}">
<body>
   #{json}
</body>
</message>

```

- `type`:chat
- `domain`:@im.itonglian.com
- `json`:自定义JSON协议

### `JSON`协议

- 协议体

    ```
    {
        compress:#{compress},
        encode:#{encode},
        encrypt:#{encrypt},
        version:#{version},
        msg_from:#{msg_from},
        msg_to:#{msg_to},
        msg_id:#{msg_id},
        msg_type:#{msg_type},
        msg_time:#{msg_time},
        body:#{body}
    }
    ```


    - `compress`:压缩方式;0:未压缩 1:压缩;默认:0
    - `encode`:编码格式;0:UTF-8 1:GBK 默认:0
    - `encrypt`:加密;0:未加密 1:加密 默认:0
    - `version`:版本号;默认:2.0.0 当前:2.1.0
    - `msg_from`:发送者
    - `msg_to`:接收者
    - `msg_id`:消息id，唯一标识;UUID
    - `msg_type`:消息类型
    - `msg_time`:发送时间戳
    - `body`:消息内容体;Array

- [消息类型`msg_type`][messageType]

    使用`字母`、`-`和`数字`的组合拼接成的字符串作为消息类型

    - `MTT`:1对1消息
    - `MTS`:会话内消息，也称为群消息
    - `MTB`:业务消息，有审批与系统两种类型
    - `MTC`:命令消息

 - 消息体`body`
    - [文本消息][MTT-000]
    - [图片消息][MTT-001]
    - [文件消息][MTT-002]
    - [语音消息][MTT-003]
    - [群文本消息][MTS-000]
    - [群图片消息][MTS-001]
    - [群文件消息][MTS-002]
    - [群语音消息][MTS-003]
    - <font color="#fff"><del>已收回执</del></font>
    - [已读回执][MTT-100]
    - [群已读回执][MTS-100]
    - [消息撤回][MTT-101]
    - [群消息撤回][MTS-101]
    - [系统消息之创建会话][MTS-105]
    - [系统消息之修改会话名称][MTS-102]
    - [系统消息之邀请订阅者][MTS-106]
    - [系统消息之退出会话][MTS-104]
    - [系统消息之解散群组][MTS-107]

## HTTP
- 请求路径
    `http://#{ip}:#{port}/plugins/tlim/#{method}`
    - `ip`:openfire服务ip地址
    - `port`:openfire服务端口号,默认`9595`
    - `method`:方法名

- `GET`请求

    - 基于`HTTP` `GET`请求
    - 暂不支持`POST`等请求

- 跨域访问

可以支持跨域访问


- 所有接口列表

    - [新增会话][SESSION_CREATE]
    - [更新会话][SESSION_MODIFY]
    - [删除会话][SESSION_DELETE]
    - [文件上传][FILE_UPLOAD]
    - [文件下载][FILE_DOWNLOAD]
    - [查询会话列表][FIND_SESSIONS]
    - [查询订阅者列表][FIND_SUBSCRIBERS_LIST]
    - [查询会话历史消息][FIND_SESSION_HISTORY]
    - [更新会话图标][UPDATE_SESSION_PIC]
    - [审批][APPROVAL]
    - [退回][SYSTEM_MESSAGE]
    - [通知公告][SYSTEM_MESSAGE]
    - [工资条][SYSTEM_MESSAGE]
    - [邮件消息][SYSTEM_MESSAGE]
    - [查询离线消息][GET_OFFLINE]
    - [更新AppPushCode][APP_PUSH_CODE]
    - [新增用户接口][ADD_USER]
    - [移除用户接口][REMOVE_USER]
   
## [部署][DEPLOY]

## 插件开发

- 环境搭建

- 插件式开发

- Ant项目管理

- 初始化



[旧版本文档][oldDoc]










[messageType]:https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_TYPE.md

[openfire]:https://github.com/GepengCn/tlim/blob/dev/images/openfire.png?raw=true

[symbol]:https://github.com/GepengCn/tlim/blob/dev/images/tlim.png?raw=true

[oldDoc]:https://github.com/GepengCn/tlim/blob/master/README.md

[MTT-000]:https://github.com/GepengCn/tlim/blob/dev/src/body/TEXT.md
[MTT-001]:https://github.com/GepengCn/tlim/blob/dev/src/body/PICTURE.md
[MTT-002]:https://github.com/GepengCn/tlim/blob/dev/src/body/FILE.md
[MTT-003]:https://github.com/GepengCn/tlim/blob/dev/src/body/VOICE.md

[MTS-000]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_TEXT.md
[MTS-001]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_PICTURE.md
[MTS-002]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_FILE.md
[MTS-003]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_VOICE.md

[MTT-100]:https://github.com/GepengCn/tlim/blob/dev/src/body/READ_BACK.md
[MTS-100]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_READ_BACK.md

[MTT-101]:https://github.com/GepengCn/tlim/blob/dev/src/body/REVOKE.md
[MTS-101]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_REVOKE.md

[MTS-105]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_CREATE.md
[MTS-102]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_NAME_UPDATE.md
[MTS-106]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_INVITE.md
[MTS-104]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_EXIT.md
[MTS-107]:https://github.com/GepengCn/tlim/blob/dev/src/body/SESSION_DISSOLVED.md
[SESSION_CREATE]:https://github.com/GepengCn/tlim/blob/dev/src/http/SESSION_CREATE.md
[SESSION_MODIFY]:https://github.com/GepengCn/tlim/blob/dev/src/http/SESSION_MODIFY.md
[SESSION_DELETE]:https://github.com/GepengCn/tlim/blob/dev/src/http/SESSION_DELETE.md
[FILE_UPLOAD]:https://github.com/GepengCn/tlim/blob/dev/src/http/FILE_UPLOAD.md
[FILE_DOWNLOAD]:https://github.com/GepengCn/tlim/blob/dev/src/http/FILE_DOWNLOAD.md
[FIND_SESSIONS]:https://github.com/GepengCn/tlim/blob/dev/src/http/FIND_SESSIONS.md
[FIND_SUBSCRIBERS_LIST]:https://github.com/GepengCn/tlim/blob/dev/src/http/FIND_SUBSCRIBERS_LIST.md
[FIND_SESSION_HISTORY]:https://github.com/GepengCn/tlim/blob/dev/src/http/FIND_SESSION_HISTORY.md
[UPDATE_SESSION_PIC]:https://github.com/GepengCn/tlim/blob/dev/src/http/UPDATE_SESSION_PIC.md
[APPROVAL]:https://github.com/GepengCn/tlim/blob/dev/src/http/APPROVAL.md
[SYSTEM_MESSAGE]:https://github.com/GepengCn/tlim/blob/dev/src/http/SYSTEM_MESSAGE.md
[GET_OFFLINE]:https://github.com/GepengCn/tlim/blob/dev/src/http/GET_OFFLINE.md
[APP_PUSH_CODE]:https://github.com/GepengCn/tlim/blob/dev/src/http/APP_PUSH_CODE.md
[DEPLOY]:https://github.com/GepengCn/tlim/blob/dev/src/CAP_DEPLOY.md
[ADD_USER]:https://github.com/GepengCn/tlim/blob/dev/src/http/ADD_USER.md
[REMOVE_USER]:https://github.com/GepengCn/tlim/blob/dev/src/http/REMOVE_USER.md

