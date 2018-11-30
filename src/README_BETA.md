## ![Alt text][symbol] tlim

---
#### 介绍


这是基于Openfire的即时通讯插件,使用XMPP协议作为消息的载体，内部消息封装成JSON格式。
重写了群聊、离线消息、消息回执等功能，并且使用自封装的JSON协议同时在Web、IOS和安卓端进行即时消息交互。
弃用了Openfire自带的数据库连接池与JDBC组件，使用MyBatis搭配Druid连接池并且使用二级缓存大幅度优化了性能，除此之外同时支持MySQL、Oracle和SQLServer等数据库。

---



#### 消息协议

使用XMPP协议作为消息传输的标准，辅以自定义JSON协议。

- XMPP协议

- JSON协议

- 消息类型

- 错误码

- 会话类型

- 系统消息

- 标准消息




#### HTTP接口

- GET请求

- 跨域访问

- 端口


#### 插件开发

- 环境搭建

- 插件式开发

- Ant项目管理

- 初始化



[旧版本文档][oldDoc]












[symbol]:https://github.com/GepengCn/tonglian-openfire/blob/master/images/symbol.png?raw=true


[oldDoc]:https://github.com/GepengCn/tlim/blob/master/README.md

