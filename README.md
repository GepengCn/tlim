# tlim
<div align="left">

[![](https://img.shields.io/badge/%E6%9C%80%E6%96%B0%E6%96%87%E6%A1%A3-v2.2.0-brightgreen.svg)](https://github.com/GepengCn/tlim/blob/master/README.md)
[![](https://img.shields.io/badge/%E4%B8%8B%E8%BD%BD-Openfire-yellowgreen.svg)](https://github.com/GepengCn/tlim/releases/download/v1.3/openfire.zip)
[![](https://img.shields.io/badge/%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B-msg__type-orange.svg)](https://github.com/GepengCn/tlim/blob/master/src/MESSAGE_TYPE.md)
[![](https://img.shields.io/badge/%E6%97%A7%E7%89%88%E6%96%87%E6%A1%A3-v1.3.0-lightgrey.svg)](https://github.com/GepengCn/tlim/blob/master/src/old/README.md)
[![](https://img.shields.io/badge/demo-coolweb.club-blue.svg)](http://coolweb.club/cap-aco/views/aco/webim/demo/webclient.html)

</div>

## 介绍


- 这是基于`Openfire`的即时通讯插件,使用`XMPP`协议作为消息的载体。
- 重写了群聊、离线消息、消息已读回执等功能。
- 使用自定义`JSON`协议同时在`Web`、`ios`和`android`进行即时消息交互。
- 弃用了`Openfire`自带的数据库连接池与`JDBC`组件，使用`MyBatis`搭配`Druid`数据库连接池并且启用二级缓存提高查询效率与稳定性。
- 同时支持`MySQL`、`Oracle`和`SQLServer`。

## 入门

### 需要
- `JDK7`及以上
- `MySQL5.5`及以上
- `Oracle`10g及以上
- 需支持`websocket`

### 安装与使用

- 下载[Openfire][DOWNLOAD_OPENFIRE]并解压

- [部署][DEPLOY]与[配置][XMLPROPERTY]

- [API文档][DETAIL]

- [插件][DOWNLOAD_TLIM]及[脚本][DOWNLOAD_SQL]下载

- [linux下部署Openfire][UBUNTU_DEPLOY]


### 插件开发

- 环境搭建

- 插件式开发

- Ant项目管理


[openfire]:https://github.com/GepengCn/tlim/blob/master/images/openfire.png?raw=true

[symbol]:https://github.com/GepengCn/tlim/blob/master/images/tlim.png?raw=true

[oldDoc]:https://github.com/GepengCn/tlim/blob/master/src/old/README.md

[DEPLOY]:https://github.com/GepengCn/tlim/blob/master/src/CAP_DEPLOY.md

[DOWNLOAD_OPENFIRE]:https://github.com/GepengCn/tlim/releases/download/v1.3/openfire.zip
[DOWNLOAD_TLIM]:https://github.com/GepengCn/tlim/releases/download/v1.3_plugin/tlim.zip
[DOWNLOAD_SQL]:https://github.com/GepengCn/tlim/releases/download/v1.2_sql/database.zip
[SIMPLE_DEMO]:http://coolweb.club/cap-aco/views/aco/webim/demo/webclient.html
[XMLPROPERTY]:https://github.com/GepengCn/tlim/blob/master/XMLPROPERTY.md
[DETAIL]:https://github.com/GepengCn/tlim/blob/master/DETAIL.md
[UBUNTU_DEPLOY]:https://github.com/GepengCn/tlim/blob/master/DEPLOY.md
