![Alt text][symbol]
-------------------
<div align="center">

[![](https://img.shields.io/badge/doc-2.1.0-green.svg)](https://github.com/GepengCn/tlim/blob/dev/src/README.md)
[![](https://img.shields.io/badge/download-v1.1-blue.svg)](https://github.com/GepengCn/tlim/releases/download/V1.1/openfire_V_1_1.zip)
[![](https://img.shields.io/badge/msg-type-yellowgreen.svg)](https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_TYPE.md)
[![](https://img.shields.io/badge/doc-old-red.svg)](https://github.com/GepengCn/tlim/blob/master/README.md)
[![](https://img.shields.io/badge/demo-coolweb-lightgrey.svg)](http://coolweb.club)

</div>

## :ballot_box_with_check:介绍


- 这是基于Openfire的即时通讯插件,使用XMPP协议作为消息的载体，内部消息封装成JSON格式。
- 重写了群聊、离线消息、消息回执等功能，并且使用自封装的JSON协议同时在Web、IOS和安卓端进行即时消息交互。
- 弃用了Openfire自带的数据库连接池与JDBC组件，使用MyBatis搭配Druid连接池并且使用二级缓存大幅度优化了性能，除此之外同时支持MySQL、Oracle和SQLServer等数据库。

## :arrows_clockwise:跨平台

![Alt text][crossPlatform]


## :abcd:消息协议

使用`XMPP`协议作为消息传输的标准，辅以自定义`JSON`协议。

- `XMPP`协议

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

- `JSON`协议

    自定义协议

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

- [消息类型][messageType]

    使用字母、-和数字的组合拼接成的字符串作为消息类型

    - `MTT`:1对1消息
    - `MTS`:会话内消息，也称为群消息
    - `MTB`:业务消息，有审批与系统两种类型
    - `MTC`:命令消息


## :mag:HTTP接口

- GET请求

- 跨域访问

- 端口


## :electric_plug:插件开发

- 环境搭建

- 插件式开发

- Ant项目管理

- 初始化



[旧版本文档][oldDoc]










[messageType]:https://github.com/GepengCn/tlim/blob/dev/src/MESSAGE_TYPE.md


[symbol]:https://github.com/GepengCn/tlim/blob/dev/images/tlim.png?raw=true

[crossPlatform]:https://github.com/GepengCn/tlim/blob/dev/images/cross-platform.png?raw=true


[oldDoc]:https://github.com/GepengCn/tlim/blob/master/README.md

