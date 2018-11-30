<img src="https://github.com/GepengCn/tlim/blob/dev/images/tlim.jpg?raw=true" style="max-width:400px;" />

-------------------
## 介绍


- 这是基于Openfire的即时通讯插件,使用XMPP协议作为消息的载体，内部消息封装成JSON格式。
- 重写了群聊、离线消息、消息回执等功能，并且使用自封装的JSON协议同时在Web、IOS和安卓端进行即时消息交互。
- 弃用了Openfire自带的数据库连接池与JDBC组件，使用MyBatis搭配Druid连接池并且使用二级缓存大幅度优化了性能，除此之外同时支持MySQL、Oracle和SQLServer等数据库。

---



## 消息协议

使用XMPP协议作为消息传输的标准，辅以自定义JSON协议。

- XMPP协议

    默认使用type='chat'作为消息载体,分别实现Plugin、PacketInterceptor接口以插件的形式拦截消息,然后自定义处理。

    ```
    <message type="#{type}" from="zhangsan#{domain}" to="lisi#{domain}">
    <body>
       #{json}
    </body>
    </message>

    ```

    - type:chat
    - domain:@im.itonglian.com
    - json:自定义JSON协议

- JSON协议


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

    - compress:压缩方式;0:未压缩 1:压缩;默认:0
    - encode:编码格式;0:UTF-8 1:GBK 默认:0
    - encrypt:加密;0:未加密 1:加密 默认:0
    - version:版本号;默认:2.0.0 当前:2.1.0
    - msg_from:发送者
    - msg_to:接收者
    - msg_id:消息id，唯一标识;UUID
    - msg_type:消息类型
    - msg_time:发送时间戳
    - body:消息内容体;Array

- 消息类型

    - MTT-000:文本消息
    - MTT-001:图片消息
    - MTT-002:文件消息
    - MTT-003:语音消息
    - MTT-100:单已读回执
    - MTT-101:单消息撤回
    - MTT-102:创建单人会话
    - MTT-200:单聊清空历史消息
    - MTT-201:清空历史消息回执
    - MTB-000:审批消息
    - MTB-001:朋友圈转发
    - MTB-100:系统消息
    - MTS-000:群聊文本
    - MTS-001:群聊图片
    - MTS-002:群聊文件
    - MTS-003:群聊语音
    - MTS-100:群已读回执
    - MTS-101:群消息撤回
    - MTS-102:修改会话名称
    - MTS-103:移除会话订阅者
    - MTS-104:退出会话
    - MTS-105:创建会话
    - MTS-106:邀请加入会话
    - MTS-107:解散群组
    - MTC-000:已收回执


- 系统消息

    ```
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
    ```

    字段名 | 含义 | 类型 |长度|备注
    |:---|---|---|---|---
    title|标题|string|100|
    content|内容|string|2000|
    ts|时间|string|20|[yyyy-MM-dd:hh:mm:ss]
    url|自定义跳转地址|string|300|/testController/detail?id=1
    mark|备注|string|200|
    random|预留字段|string|100|
    type|类型|int|∞|
    typeName|类型名|string|200|


    type:typeName(类型:类型名)
    - 审批类型:0
    - 退回:1
    - 通知公告:2
    - 工资条:3
    - 邮件:4

- 标准消息




## HTTP接口

- GET请求

- 跨域访问

- 端口


## 插件开发

- 环境搭建

- 插件式开发

- Ant项目管理

- 初始化



[旧版本文档][oldDoc]












[symbol]:https://github.com/GepengCn/tlim/blob/dev/images/tlim.jpg?raw=true


[oldDoc]:https://github.com/GepengCn/tlim/blob/master/README.md

