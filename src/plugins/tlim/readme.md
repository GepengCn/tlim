## openfire插件设计文档


> [一、开发环境搭建](#env)<br>
> [二、插件开发](#plugin)<br>
> [二、功能](#function)<br>
> [三、表结构](#table)<br>
> [四、接口设计](#structure)<br>
>>  [消息体封装、解析](#analyze)<br>
    [消息体Dao层](#messagedb)<br>
    [会话Dao层](#sessiondb)<br>
    [订阅者Dao层](#subscriberdb)<br>
    [消息监听](#interceptor)<br>
>>    
> [接口解析](#analysis)<br>
>>  [发送文本](#MTT-000)<br>
>>  [发送图片](#MTT-001)<br>
>>  [发送文件](#MTT-002)<br>
>>  [发送语音](#MTT-003)<br>
>>  [收到消息回执](#MTC-000)<br>
>>  [已读消息回执](#MTC-001)<br>
>>  [消息撤回](#MTC-002)<br>
>>  [新增会话](#MTC-003)<br>


### <span id="env">开发环境搭建</span>
    
    1.环境准备如下:
    
    a.openfire
    版本:4.2.3
    
    b.jdk
    版本:jdk7
    
    c.IDE
    IntelliJ IDEA
    
    d.数据库
    MySQL5.5
    
    2.openfire源码获取地址:
    
    http://www.igniterealtime.org/downloads/download-landing.jsp?file=openfire/openfire_src_4_2_3.zip
    
    3.参考https://blog.csdn.net/l139955/article/details/51981671导入openfire
    
    
    
### <span id="plugin">插件开发</span>
> 文件结构<br>
> 编译<br>
> 实现Plugin接口<br>

#### 文件结构

    src/plugins目录下新建tlim目录
    
    1. src/java 源码目录
    2. readme.html 插件简介
    3. changelog.html 改动介绍
    4. plugin.xml 插件信息配置
    
    配置参考如下:
    
    <?xml version="1.0" encoding="UTF-8"?>
    <plugin>
        <class>com.itonglian.IMPlugin</class>
        <name>tlim</name>
        <description>IM Plugin</description>
        <author>Gepeng</author>
        <version>1.0.0</version>
        <date>01/08/2018</date>
        <minServerVersion>4.1.6</minServerVersion>
        <adminconsole></adminconsole>
    </plugin>
    
    5. lib 第三方jar包引入

#### 编译

    build/build.properties新增属性:
    plugin=tlim
    
    使用ant执行plugin指令即可
    
#### 实现Plugin接口
    
    新建IMPlugin.java 实现Plugin接口
    
    public void initializePlugin：初始化接口时进入此方法
    
    public void destroyPlugin：插件销毁时进入此方法
    
    
### <span id="function">功能</span>
> 聊天消息存储<br>
> 离线消息存储<br>
> 会话存储<br>
> 广播<br>
> 消息体封装与解析<br>

### <span id="table">表结构<span>

- ofmessage
    
字段名 | 字段类型 | 版本|备注
---|---|---|---
msg_id| varchar2(64) | 1.0.0| uuid
msg_type    | varchar2(20)   | 1.0.0  | eg:MTC-000  
from|  varchar2(64)  |1.0.0  | user_id
to|   varchar2(64) |1.0.0  | user_id
ts|  varchar(20)  |1.0.0 | 15988287263
body    |   varchar2(4096) | 1.0.0 | JSONArray

    create database ofdb;

    drop table if exists ofmessage;

    /*==============================================================*/
    /* Table: ofmessage                                             */
    /*==============================================================*/
    create table ofmessage
    (
       msg_id               varchar(64) not null,
       msg_type             varchar(20),
       msg_from             varchar(64),
       msg_to               varchar(64),
       ts                   varchar(20),
       body                 varchar(4096),
       primary key (msg_id)
    )
    DEFAULT CHARACTER SET = utf8;

- ofsession

字段名 | 字段类型 | 版本|备注
---|---|---|---
session_id | varchar2(64) | 1.0.0 | uuid
session_type | int | 1.0.0 | 0/1/2/3
session_name | varchar2(256) | 1.0.0 |
session_create_time | varchar2(20) | 1.0.0 | 1598827376
session_modify_time | varchar2(20) | 1.0.0 | 1598827376
session_delete_time | varchar2(20) | 1.0.0 | 1598827376
session_valid | int | 1.0.0 | 0/1
session_user | varchar2(64) | 1.0.0 | user_id

    drop table if exists ofsession;

    /*==============================================================*/
    /* Table: ofsession                                             */
    /*==============================================================*/
    create table ofsession
    (
       session_id           varchar(64) not null,
       session_type         int,
       session_name         varchar(256),
       session_create_time  varchar(20),
       session_modify_time  varchar(20),
       session_delete_time  varchar(20),
       session_valid        int,
       session_user         varchar(64),
       primary key (session_id)
    )
    DEFAULT CHARACTER SET = utf8;

- ofsubscriber

字段名 | 字段类型 | 版本|备注
---|---|---|---
user_id | varchar2(64) | 1.0.0 | uuid
user_name | varchar2(256) | 1.0.0 | 用户名
acct_login | varchar2(64) | 1.0.0 | 登录名
pic | varchar2(128) | 1.0.0 | 头像url
session_id | varchar2(64) | 1.0.0 | 会话id
ts  | varchar2(20) | 1.0.0 | 15982872726

    drop table if exists ofsubscriber;

    /*==============================================================*/
    /* Table: ofsubscriber                                          */
    /*==============================================================*/
    create table ofsubscriber
    (
       user_id              varchar(64) not null,
       user_name            varchar(256),
       acct_login           varchar(64),
       pic                  varchar(128),
       session_id           varchar(64),
       ts                   varchar(20),
       primary key (user_id)
    )
    DEFAULT CHARACTER SET = utf8;

    
### <span id="structure">结构设计</span>


#### <span id="analyze">消息体封装、解析</span>

    class MessageParser
    
    public static void toOfMessage(Message message,T t)
    
    public static <T> T parseBodyToBean(OfMessage message)
    
    
#### <span id="messagedb">消息体Dao层</span>   

    
    class MessageDao
    
    public void add(OfMessage message)
    
    public void delete(String messageId)
    
    public void update(OfMessage message)
    
    public OfMessage findEntityById(String messageId)
    
    public List<OfMessage> findList(Map<String,Object> conditions)
    
    
#### <span id="sessiondb">会话Dao层</span>    

    class SessionDao
    
    public void add(OfSession session)
    
    public void delete(String sessionId)
    
    public void update(OfSession session)
    
    public OfSession findEntityById(String sessionId)
    
    public List<OfSession> findList(Map<String,Object> conditions)
    
    
#### <span id="subscriberdb">订阅者dao层</span>

    class SubscriberDao
    
    public void add(OfSubscriber subscriber)
    
    public void delete(String userId)
    
    public void update(OfSubscriber subscriber)
    
    public OfSubscriber findEntityById(String userId)
    
    public List<OfSubscriber> findList(Map<String,Object> conditions)
    
    public List<OfSubscriber> findBySessionId(String sessionId)
    
#### <span id="interceptor">消息监听</span>

    接口:interface Interceptor
    
    public void handler(Packet packet,Session session)
    
    实现类
    1.MessageInterceptor 聊天类消息监听
    2.SessionInterceptor 会话类消息监听
    
    拦截器处理中心
    InterceptorContext

    
    
### <span id="analysis">接口解析</span>

###### <span id="MTT-000">发送文本</span>

    clientA->openfire-clientB
    
    a.消息拦截
    b.解析与校验
    c.存储
    d.转发
    
---

###### <span id="MTT-001">发送图片</span>

    clientA->openfire-clientB
    
    a.消息拦截
    b.解析与校验
    c.存储
    d.转发
    
    

---

###### <span id="MTT-002">发送文件</span>

    clientA->openfire-clientB
    
    a.消息拦截
    b.解析与校验
    c.存储
    d.转发
    
    

---

###### <span id="MTT-003">发送语音</span>

    clientA->openfire-clientB
    
    a.消息拦截
    b.解析与校验
    c.存储
    d.转发
    

---
###### <span id="MTC-000">收到消息回执</span>

    clientB->openfire-clientA
    
    a.消息拦截
    b.解析与校验
    c.不存储
    d.转发
    

---
###### <span id="MTC-001">已读消息回执</span>

    clientB->openfire-clientA
    
    a.消息拦截
    b.解析与校验
    c.不存储
    d.转发
    
    

---
###### <span id="MTC-002">消息撤回</span>

    clientB->openfire-clientA,clientB,clientC,clientD
    
    a.消息拦截
    b.解析与校验
    c.不存储
    d.查询订阅者
    e.广播

---
###### <span id="MTC-003">新增会话</span>

    clientB->openfire-clientB
    
    a.消息拦截
    b.解析与校验
    c.存储会话
    d.存储订阅者
    e.推送结果
    

---
###### <span id="MTC-004">更新会话</span>

    clientB->openfire-clientA,clientB,clientC,clientD
    
    a.消息拦截
    b.解析与校验
    c.更新会话
    d.更新订阅者
    e.查询订阅者
    f.广播

---

###### <span id="MTC-005">删除会话</span>

    clientB->openfire-clientA,clientB,clientC,clientD
    
    a.消息拦截
    b.解析与校验
    c.删除会话
    d.查询订阅者
    e.广播
    f.删除订阅者
    