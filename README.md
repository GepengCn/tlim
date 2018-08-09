# 即时通讯设计文档
> 版本:1.2.3<br>
> 更新于:2017年8月9日<br>
> openfire版本:4.2.3<br>
> 应用服务器版本:2.0.1<br>

## <span id="catalog">文档目录</span>
> [一、消息结构](#一消息结构)<br>
> [二、消息字段表](#二消息字段表)<br>
> [三、消息类型](#三消息类型)<br>
> [四、错误码](#四错误码)<br>
> [五、<span id="interface">接口列表](#五接口列表)</span>
>>    1. [发送文本消息](#发送文本消息)<br>
>>    2. [发送图片消息](#发送图片消息)<br>
>>    3. [发送文件消息](#发送文件消息)<br>
>>    4. [发送语音消息](#发送语音消息)<br>
>>    5. [群文本消息](#群文本消息)<br>
>>    6. [群图片消息](#群图片消息)<br>
>>    7. [群文件消息](#群文件消息)<br>
>>    8. [群语音消息](#群语音消息)<br>
>>    9. [已收回执](#已收回执)<br>
>>    10. [已读回执](#已读回执)<br>
>>    10. [群已读回执](#群已读回执)<br>
>>    11. [消息撤回](#消息撤回)<br>
>>    12. [群消息撤回](#群消息撤回)<br>
>>    13. [新增会话](#新增会话)<br>
>>    14. [更新会话](#更新会话)<br>
>>    15. [删除会话](#删除会话)<br>
>>    16. [文件下载](#文件下载)<br>
>>    17. [文件上传](#文件上传)<br>
>>
> 六、[流程](#流程)<br>
> 七、[Smack API相关](#八smack-api相关)<br>
> 八、[本次改动](#本次改动)



## 一、消息结构

    {
        compress:"0",
        encode:"1",
        encrypt:"0",
        version:"2.0.0",
        from:'31e4302a-22f1-430e-a7e2-eebcf960dad6',
        to:'b4842821-0f8a-48f9-9492-89c20c3889a5',
        msg_id:'fd6c568e-19ca-4e4e-9709-24dec0bb2b8d',
        msg_type:'MTT-001',
        msg_time:'1599218283837',
        body:body
    }

> compress:"0"代表不压缩,"1"代表压缩<br>
> encode:"1"代表utf-8<br>
> encrypt:"0"代表明文<br>


## 二、消息字段表
消息字段名称 | 含义 | JSON层 |版本|类型|大小|备注
---|---|:---:|:---:|---|---|---
compress | 压缩方式 | 1 |   1.0.0   |   String  |   20   |
encode | 编码格式 | 1 |   1.0.0   |   String  |   20   |
encrypt | 加密方式 | 1 |   1.0.0   |   String  |   50   |
version | 版本号 | 1 |   1.0.0   |   String  |   10   |
msg_id | 消息id，唯一，uuid | 1 |   1.0.0   |   String  |   64   |
msg_time | 时间戳 | 1 |   1.0.0   |   String  |   20   |
from | userId | 1 |   1.0.0   |   String  |   64   |
to | userId | 1 |   1.0.0   |   String  |   64   |
[msg_type](#三消息类型) | MTT:message type talk <br>HTS:http type session<br>MTC:message type command<br>FO:file operate| 1 |   1.0.0   |    String  |   20   |
body | 消息内容体,自己实现;数组| 1 |   1.0.0   |   JSONArray  |   infinite   |
session_id  |   会话id  |   2 |   1.0.0   |   String  |   64   |
[session_type](#四会话类型)    |   会话类型    |   2 |   1.0.0   |     Integer  |   20   |
session_name    |   会话名称    |   2 |   1.0.0   |   String  |   128   |
session_create_time    |   会话创建时间戳    |   2 |   1.0.0   |String  |   20   |
session_modify_time    |   会话修改时间戳    |   2 |   1.0.0   |String  |   20   |
session_delete_time    |   会话删除时间戳    |   2 |   1.0.0   |String  |   20   |
session_valid   |   会话是否有效    |   2 |   1.0.0   |   Integer  |   10   |
session_user    |   创建会话用户userid  |   2 |   1.0.0   |  String  |   64   |


## 三、消息类型

    MTT-000:文本消息
    MTT-001:图片消息
    MTT-002:文件消息
    MTT-003:语音消息
    MTS-000:群聊文本
    MTS-001:群聊图片
    MTS-002:群聊文件
    MTS-003:群聊语音
    MTS-004:待办
    HTS-000:新增会话
    HTS-001:更新会话
    HTS-002:删除会话
    MTC-000:收到消息回执
    MTC-001:已读消息回执
    MTC-002:消息撤回
    HFO-000:下载文件
    HFO-001:上传文件


## 四、错误码

    error-000:body为空或无效
    error-001:msg_type为空或无效
    error-002:session_type为空或无效
    error-003:session_name为空
    error-004:session_user为空或不存在
    error-005:解析json失败
    error-006:body不是有效数组
    error-007:session_id为空
## 五、接口列表

###### 发送文本消息

    1. 接口定义:

    发送文本类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            text:'hello,world'
        }
    ]


---

###### 发送图片消息

    1. 接口定义:

    发送图片类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传图片到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---
###### 发送文件消息

    1. 接口定义:

    发送文件类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传文件到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---

###### 发送语音消息

    1. 接口定义:

    发送语音类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传语音到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]



---
###### 群文本消息

    1. 接口定义:

    发送文本类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'2asd221ssa-ssd222aascxz-ssaa',
            text:'hello,world'
        }
    ]


---

###### 群图片消息

    1. 接口定义:

    发送图片类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传图片到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            session_id:'2asd221ssa-ssd222aascxz-ssaa',
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---
###### 群文件消息

    1. 接口定义:

    发送文件类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传文件到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            session_id:'2asd221ssa-ssd222aascxz-ssaa',
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---

###### 群语音消息

    1. 接口定义:

    发送语音类型消息

    2. 接口流程:

    clientA->openfire->clientB

    a. 上传语音到应用服务器并获取到fileId
    b. clientA发送fileId到openfire服务器
    c. openfire服务器解析、校验、存储
    d. 消息推送给clientB

    3. body
    [
        {
            session_id:'2asd221ssa-ssd222aascxz-ssaa',
            file_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]



---
###### 已收回执

    1. 接口定义:

    client收到消息后返回回执

    2. 接口流程:

    clientB->openfire->clientA

    a. client发送回执给openfire服务器
    b. openfire解析、校验、转发

    3. body
    [
        {
            msg_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]



---
###### 已读回执

    1. 接口定义:

    client读取消息后发送回执

    2. 接口流程:

    clientB->openfire->clientA

    a. client发送回执给openfire服务器
    b. openfire解析、校验、转发或广播

    3. body
    [
        {
            msg_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---
###### 群已读回执

    1. 接口定义:

    client读取消息后发送回执

    2. 接口流程:

    clientB->openfire->clientA,clientC

    a. client发送回执给openfire服务器
    b. openfire解析、校验、转发或广播

    3. body
    [
        {
            session_id:'as7da7sd7as-wdasd-w2wda22d-adsd1',
            msg_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]


---
###### 消息撤回

    1. 接口定义:

    client撤回已发送消息(默认2min内)

    2. 接口流程:

    clientB->openfire->clientA


    a. client发送撤回指令给openfire服务器
    b. openfire解析、校验、发送撤回指令给其它订阅client

    3. body
    [
        {
            msg_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]



---
###### 群消息撤回

    1. 接口定义:

    client撤回已发送消息(默认2min内)

    2. 接口流程:

    clientB->openfire->clientA,clientC,clientD


    a. client发送撤回指令给openfire服务器
    b. openfire解析、校验、发送撤回指令给其它订阅client

    3. body
    [
        {
            session_id:'2d77s7sd-as88d8d8-d88da8sd',
            msg_id:'2asd221ssa-ssd222aascxz-ssaa'
        }
    ]



---
###### 新增会话

    1. 接口定义:

    新增会话,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送新增会话http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址

    http://coolweb.club:9090/plugin/tlim/addSession

    4. 参数

    4.1. session_type:'1'
    4.2. subscribers:[
      {
          user_id:'clientA'
      },
      {
          user_id:'clientB'
      },
      {
          user_id:'clientC'
      }
    ]

    5. 返回值:json对象
    {
        result:'ok/fail',
        result_detail:'ok/error-001',
        session_id:'asa8ww8w-as99zxc9qe-s0adasd8',
        session_name:'nameA,nameB,nameC',
        session_type:'1',
        session_user:'adwdasc-as9das9d8w-8asd8a',
        session_create_time:'159020291828',
        subscribers:[
          {
              user_id:'clientA'
          },
          {
              user_id:'clientB'
          },
          {
              user_id:'clientC'
          }
        ]

    }



---

###### 更新会话

    1. 接口定义:

    更新会话名称、修改订阅者等,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9090/plugin/tlim/modifySession

    4. 参数

    4.1. session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'
    4.2 session_name:'hello,world',
    4.2. subscribers:[
      {
          user_id:'68731eb70ff04ab1b4bfdff065dc0ac9',
          type:'del'
      },
      {
          user_id:'68128ea81e6346c78b7ac8c149404c3a',
          type:'del'
      },
      {
          user_id:'5be16456aab44c7e8dcacc51c44de705',
          type:'add'
      }
    ]

    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "session_id": "efac3b0f-880c-4764-a0c4-beb1718a2cea",
        "session_modify_time": "1533804920060"
    }

---
###### 删除会话

    1. 接口定义:

    删除会话,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9090/plugin/tlim/deleteSession

    4. 参数

    4.1. session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'


    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "session_delete_time": "1533805005540",
        "session_id": "f5709fc2-4ffe-4aa6-b106-51f170d6c109"
    }

---

###### 文件下载

    1. 接口定义:

    从应用服务器/文件服务器下载文件

    2. 接口流程:

    clientA->app server->clientA

    a. client向服务器发起http请求
    b. 服务器响应后传输文件流给client

    3. 接口地址:http://ip:port/projectName/upload/mobileFileDownload
    4. 参数:fileId
    5. 返回值:file流


---
###### 文件上传

    1. 接口定义:

    上传文件(HTTP)到应用服务器/文件服务器,返回fileId

    2. 接口流程:

    clientA->app server->clientA

    a. client上传文件到文件服务器
    b. 服务器处理后返回fileId给client

    3. 接口地址:http://ip:port/projectName/upload/mobileFileUpload
    4. 参数:file流
    5. 返回值:fileId


---

## 六、流程
![Alt text][flowPic]

## 七、Smack API相关

1. 域名(domain):```im.itonglian.com```
2. 用户名(Jid):```userId@im.itonglian.com```
3. 密码使用真实密码
4. 扩展Message
```
a. 创建Extension类实现ExtensionElement接口


import org.jivesoftware.smack.packet.ExtensionElement;

public class Extension implements ExtensionElement {

    private static final String ELEMENT_NAME = "tlim";

    private static final String NAME_SPACE = "im.itonglian.com";

    private static final String LOC_PROPERTY = "location";

    private static final String LOC_WEB = "web";

    private static final String LOC_MOBILE = "mobile";


    @Override
    public String getNamespace() {
        return NAME_SPACE;

    }

    @Override
    public String getElementName() {
        return ELEMENT_NAME;
    }

    @Override
    public CharSequence toXML() {
        StringBuilder str = new StringBuilder();
        str
            .append("<")
            .append(ELEMENT_NAME)
            .append(" xmlns='")
            .append(NAME_SPACE)
            .append("' ")
            .append(LOC_PROPERTY+"='")
            .append(LOC_WEB).append("'")
            .append("></")
            .append(ELEMENT_NAME)
            .append(">");
        return str.toString();
    }
}

b. 扩展消息
message.addExtension(new Extension());

```



## 八、改动
> 2018年8月9日
1. 文本、图片、语音、文件类消息拆分成单与群聊两种
2. 新增修改会话与删除会话接口
3. 移除会话类型，调整文档结构
4. 若干bug修复
5. 修正http中文乱码


> 2018年8月6日
1. 重写新增、修改、删除会话，由消息请求改为http请求
2. 修改与删除若干消息类型
3. 新增若干错误吗
4. 合并单聊与群聊
5. 稍微调整文档目录与结构
6. 版本1.2.0





> 2018年8月3日

1. 修正文档消息结构
2. 删除订阅、取消订阅接口
3. 修改消息回执接口调用方式
4. 删除无用的消息类型
5. 新增流程图
6. 修正了若干接口错误字段，删除多余字段
7. 修正了文档页面内跳转问题
8. 版本1.1.0



> 2018年7月31日
1. 消息结构:删除``result``字段
2. 消息结构:删除``items``字段
3. 消息结构:修改``encrypt``值为:``plain``
4. 消息结构:修改``version``值为:``2.0.0``
5. 消息结构:修改``compress``值为:``none``
6. 消息结构:新增``body``字段
7. 消息通讯模式修改<br>
    >原消息通讯模式:``client->app server->openfire->client``<br>
    >现消息通讯模式:``client->openfire->client``<br>
8. 删除``除上传下载文件外``所有http式拉取消息接口，改为主动请求被动推送模式
9. 新增发送文件消息接口
10. 新增发送语音消息接口
11. 新增消息撤回接口
12. 重写收到消息回执与已读消息回执接口
13. 合并1v1聊天与群聊天，将1v1聊天作为特殊的群组(有且只有只有2人)处理
14. 新增会话、订阅者概念取代群组与群成员
15. 新增若干成功回执接口
16. 新增错误码方便双端调试接口
17. 重写消息类型
18. 新增会话类型
19. 支持发送表情
20. 新增离线消息处理与接口
21. 版本1.0.0




[flowPic]:https://github.com/GepengCn/tonglian-openfire/blob/master/images/im01.png?raw=true
