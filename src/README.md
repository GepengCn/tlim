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

    ```
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

    使用字母、-和数字的组合拼接成的字符串作为消息类型

    - `MTT`:1对1消息
    - `MTS`:会话内消息，也称为群消息
    - `MTB`:业务消息，有审批与系统两种类型
    - `MTC`:命令消息

 - [消息体`body`][messageBody]

    - 使用`XMPP`协议向Openfire发送消息
    - `<body></body>`里嵌入自定义JSON协议
    - `Openfire`通过`msg_type`区分消息类型分别予以不同的处理
    - 'msg_from'、'msg_to'告知`Openfire`消息的推送对象与回执对象
    - 'msg_time'

## HTTP

- GET请求

- 跨域访问

- 端口


## 插件开发

- 环境搭建

- 插件式开发

- Ant项目管理

- 初始化



[旧版本文档][oldDoc]









[messageBody]:https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_BODY.md


[messageType]:https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_TYPE.md

[openfire]:https://github.com/GepengCn/tlim/blob/dev/images/openfire.png?raw=true

[symbol]:https://github.com/GepengCn/tlim/blob/dev/images/tlim.png?raw=true

[crossPlatform]:https://github.com/GepengCn/tlim/blob/dev/images/cross-platform.png?raw=true

[oldDoc]:https://github.com/GepengCn/tlim/blob/master/README.md

