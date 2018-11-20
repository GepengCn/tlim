# 即时通讯设计文档
> 版本:1.2.24<br>
> 更新于:2017年9月29日<br>
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
>>    11. [群已读回执](#群已读回执)<br>
>>    12. [消息撤回](#消息撤回)<br>
>>    13. [群消息撤回](#群消息撤回)<br>
>>    14. [新增会话](#新增会话)<br>
>>    15. [更新会话](#更新会话)<br>
>>    16. [删除会话](#删除会话)<br>
>>    17. [文件下载](#文件下载)<br>
>>    18. [文件上传](#文件上传)<br>
>>    19. [查询所有会话](#查询所有会话)<br>
>>    20. [查询会话](#查询会话)<br>
>>    21. [查询会话中所有订阅者](#查询会话中所有订阅者)<br>
>>    22. [查询会话历史消息](#查询会话历史消息)<br>
>>    23. [更新会话图标](#更新会话图标)<br>
>>    24. [WEB端查询所有会话](#web端查询所有会话)
>>    25. [WEB查询单聊历史消息](#web查询单聊历史消息)
>>    26. [WEB查询会话订阅者在线情况](#web查询会话订阅者在线情况)
>>    27. [WEB更新会话已读状态](#web更新会话已读状态)
>>    28. [WEB查询会话中某条消息已读人数](#web查询会话中某条消息已读人数)
>>    29. [WEB查询单聊中某条消息已读人数](#web查询单聊中某条消息已读人数)
>>    30. [WEB查询某条消息已读情况](#web查询某条消息已读情况)
>>    31. [WEB新增群公告](#web新增群公告)
>>    32. [WEB查询会话内所有群公告](#web查询会话内所有群公告)
>>    33. [WEB修改群公告](#web修改群公告)
>>    34. [WEB删除群公告](#web删除群公告)
>>    35. [系统消息:创建会话](#系统消息创建会话)
>>    36. [系统消息:修改会话名称](#系统消息修改会话名称)
>>    37. [系统消息:邀请订阅者](#系统消息邀请订阅者)
>>    38. [系统消息:移除订阅者](#系统消息移除订阅者)
>>    39. [系统消息:退出会话](#系统消息退出会话)
>>    40. [系统消息:消息撤回](#系统消息消息撤回)
>>    41. [解散群组](#解散群组)
>>    42. [系统消息:解散群组](#系统消息解散群组)
>>    43. [审批](#审批)
>>    44. [退回](#退回)
>>    45. [系统消息:单聊清空历史消息](#系统消息单聊清空历史消息)
>>    46. [单聊清空历史消息](#单聊清空历史消息)
>>    47. [系统消息:清空历史消息回执](#系统消息清空历史消息回执)
>>    48. [同步消息](#同步消息)
>>    49. [当前用户是否有未读消息](#当前用户是否有未读消息)
>>    50. [AppPushCode](#AppPushCode)
>>    51. [离线消息](#离线消息)
>>
> 六、[流程](#六流程)<br>
> 七、[Smack API相关](#七smack-api相关)<br>
> 八、[会话类型](#会话类型)<br>
> 九、[本次改动](#八改动)<br>
> 十、[openfire部署][depoyopenfire]


## 一、消息结构

    {
        compress:"0",
        encode:"1",
        encrypt:"0",
        version:"2.0.0",
        msg_from:'31e4302a-22f1-430e-a7e2-eebcf960dad6',
        msg_to:'b4842821-0f8a-48f9-9492-89c20c3889a5',
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
msg_from | userId | 1 |   1.2.9   |   String  |   64   |
msg_to | userId | 1 |   1.2.9   |   String  |   64   |
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
    MTT-100:单已读回执
    MTT-101:单消息撤回
    MTT-102:创建单人会话
    MTT-200:单聊清空历史消息
    MTT-201:清空历史消息回执
    MTB-000:审批
    MTB-001:朋友圈转发
    MTS-000:群聊文本
    MTS-001:群聊图片
    MTS-002:群聊文件
    MTS-003:群聊语音
    MTS-100:群已读回执
    MTS-101:群消息撤回
    MTS-102:修改会话名称
    MTS-103:移除会话订阅者
    MTS-104:退出会话
    MTS-105:创建会话
    MTS-106:邀请加入会话
    MTS-107:解散群组
    MTC-000:已收回执
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
    error-008:user_id为空
    error-009:不存在的会话
    error-010:addSession出错
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

    http://coolweb.club:9595/plugins/tlim/addSession

    4. 参数

    4.1 session_type:'1'
    4.2 request_user:'adwdasc-as9das9d8w-8asd8a',
    4.3 subscribers:[
      {
          user_id:'adwdasc-as9das9d8w-8asd8a'
      },
      {
          user_id:'adwdasc-as9das9d8w-8asd8b'
      },
      {
          user_id:'adwdasc-as9das9d8w-8asd8c'
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
              user_id:'adwdasc-as9das9d8w-8asd8a'
          },
          {
              user_id:'adwdasc-as9das9d8w-8asd8b'
          },
          {
              user_id:'adwdasc-as9das9d8w-8asd8c'
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
    http://coolweb.club:9595/plugins/tlim/modifySession

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
    http://coolweb.club:9595/plugins/tlim/deleteSession

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

###### 查询所有会话
   1. 接口定义:

    查询所有会话列表,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findSessions

    4. 参数

    4.1. user_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'


    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "sessions": [
            {
                "session_id": "3e2b7b5c-8948-438e-883e-377976afeb08",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            },
            {
                "session_id": "4e7cee72-72a0-48d1-a2c3-c071342c1467",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            },
            {
                "session_id": "861560a6-f726-446c-8337-3759525e0fb7",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            },
            {
                "session_id": "eab711cf-6f23-4247-83fe-51bdf8cf7295",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            }
        ]
    }

---
###### 查询会话
   1. 接口定义:

    查询会话详情及订阅者列表,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findSession

    4. 参数

    4.1. session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'


    5. 返回值:json对象
    {
       "result": "ok",
       "result_detail": "",
       "session_create_time": "1534750713170",
       "session_id": "5173d7d4-32de-40c1-8849-8b2bab48d3b2",
       "session_name": "陈晓旭,包婉婷,刘跃阳,安翰英",
       "session_type": 1,
       "session_user": "717a4913b0a9409191518967330419e5",
       "subscribers": [
           {
               "acct_login": "cxx",
               "session_id": "5173d7d4-32de-40c1-8849-8b2bab48d3b2",
               "ts": "1534750713144",
               "user_id": "1d0e5d20ef0c4eb38cf95f643eecfc27",
               "user_name": "陈晓旭"
           },
           {
               "acct_login": "lyy",
               "session_id": "5173d7d4-32de-40c1-8849-8b2bab48d3b2",
               "ts": "1534750713158",
               "user_id": "717a4913b0a9409191518967330419e5",
               "user_name": "刘跃阳"
           },
           {
               "acct_login": "bwt",
               "session_id": "5173d7d4-32de-40c1-8849-8b2bab48d3b2",
               "ts": "1534750713151",
               "user_id": "93be1343711940ea986be1486f1541af",
               "user_name": "包婉婷"
           },
           {
               "acct_login": "ahy",
               "session_id": "5173d7d4-32de-40c1-8849-8b2bab48d3b2",
               "ts": "1534750713164",
               "user_id": "f04de6dddf5a4f1db4b0ad57e2deaf82",
               "user_name": "安翰英"
           }
       ]
    }

---
###### 查询会话中所有订阅者
   1. 接口定义:

    查询会话中所有订阅者,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findSubscribers

    4. 参数

    4.1. session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'


    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "session_id": "3e2b7b5c-8948-438e-883e-377976afeb08",
        "session_name": "杜剑春,吴国正,葛鹏",
        "session_type": 0,
        "session_user": "673b15e889df4e4aaa33b46d1b433189",
        "subscribers":[
            {
                user_id:'',
                user_name:'',
                acct_login:'',
                pic:'',
                session_id:'',
                ts:''
            },
            {
                user_id:'',
                user_name:'',
                acct_login:'',
                pic:'',
                session_id:'',
                ts:''
            },
        ]
    }

---
###### 查询会话历史消息
   1. 接口定义:

    查询会话历史消息,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findSessionHistory

    4. 参数

    4.1 session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'
    4.2 user_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea',
    4.3 start:'0'
    4.4 length:'10'

    注:length如果为max则等于Integer.MAX_VALUE


    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "session_id": "824a02a1-9ee4-4487-b56f-de5b4a29317f",
        "total":25,
        "message_list": [
        {
            "body": "[{text:'hello,everyone',session_id:'824a02a1-9ee4-4487-b56f-de5b4a29317f'}]",
            "msg_from": "00f4aa18361c44d3b4c5dd444b76fe7e",
            "msg_id": "dcb349ac-e866-443d-8c5f-ba047267cc27",
            "msg_time": "1534315606151",
            "msg_to": "11ae67223bb5430d8c969fd525a2be96",
            "msg_type": "MTS-000",
            "session_id": "824a02a1-9ee4-4487-b56f-de5b4a29317f"
        },
        {
            "body": "[{text:'hello,everyone',session_id:'824a02a1-9ee4-4487-b56f-de5b4a29317f'}]",
            "msg_from": "00f4aa18361c44d3b4c5dd444b76fe7e",
            "msg_id": "84a07e61-b0b7-411c-af29-d17592d4331e",
            "msg_time": "1534315575769",
            "msg_to": "11ae67223bb5430d8c969fd525a2be96",
            "msg_type": "MTS-000",
            "session_id": "824a02a1-9ee4-4487-b56f-de5b4a29317f"
        }],
    }

---
###### 更新会话图标
   1. 接口定义:

    更新会话图标,http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/updateSessionPic

    4. 参数

    4.1 session_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'
    4.2 session_pic:'efac3b0f-880c-4764-a0c4-beb1718a2cea'


    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "session_id": "824a02a1-9ee4-4487-b56f-de5b4a29317f",
        "session_pic":"http://ip:port/cap-aco/uploader/loadImg?pic=jjhd21.png"
    }


---
###### WEB端查询所有会话
   1. 接口定义:

    由于WEB端不能缓存的限制,所以使用此接口查询会话,包含单聊模式;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findWebSessions

    4. 参数

    4.1 user_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'
    4.2 valid:0

    注:valid 为int型,取值范围(0,1);0:打开 1:关闭

    5. 返回值:json对象
    {
        "result": "ok",
        "result_detail": "",
        "sessions": [
            {
                "session_id": "3e2b7b5c-8948-438e-883e-377976afeb08",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_modify_time": "1534841308159",
                "session_pic": "D:/mediaFile/Img///imageTemp//1534815553662//FINAL1534815555239.png",
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            },
            {
                "session_id": "4e7cee72-72a0-48d1-a2c3-c071342c1467",
                "session_name": "杜剑春,吴国正,葛鹏",
                "session_type": 0,
                "session_modify_time": "1534841308159",
                "session_pic": "D:/mediaFile/Img///imageTemp//1534815553662//FINAL1534815555239.png",
                "session_user": "673b15e889df4e4aaa33b46d1b433189",
            }
        ]
    }


---

###### WEB查询单聊历史消息
   1. 接口定义:

    查询单聊历史消息;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findChatHistory

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'
    4.1 user_id:'efac3b0f-880c-4764-a0c4-beb1718a2cea'
    4.3 start:0
    4.4 length:10



    5. 返回值:json对象
    {
    "message_list": [
        {
            "body": "[{\"text\":\"hello,0\"}]",
            "id_": 854,
            "msg_from": "00f4aa18361c44d3b4c5dd444b76fe7e",
            "msg_id": "f970faad-9190-4b39-9f7b-b897db2a2595",
            "msg_time": "1534846194036",
            "msg_to": "673b15e889df4e4aaa33b46d1b433189",
            "msg_type": "MTT-000"
        },
        {
            "body": "[{\"text\":\"hello,8\"}]",
            "id_": 837,
            "msg_from": "00f4aa18361c44d3b4c5dd444b76fe7e",
            "msg_id": "f43a7188-c806-420c-b6ac-f562f1e6038a",
            "msg_time": "1534841308159",
            "msg_to": "673b15e889df4e4aaa33b46d1b433189",
            "msg_type": "MTT-000"
        },
        {
            "body": "[{\"text\":\"hello,7\"}]",
            "id_": 836,
            "msg_from": "00f4aa18361c44d3b4c5dd444b76fe7e",
            "msg_id": "6348bef5-545f-4cfd-9d30-75e73221171e",
            "msg_time": "1534841306654",
            "msg_to": "673b15e889df4e4aaa33b46d1b433189",
            "msg_type": "MTT-000"
        }

    ],
        "result": "ok",
        "result_detail": "",
        "session_id": "673b15e889df4e4aaa33b46d1b433189",
        "total": 3
    }

---
###### WEB查询会话订阅者在线情况
   1. 接口定义:

    查询会话订阅者在线情况;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findPresence

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'



    5. 返回值:json对象
     [
        {
            "status": "offline",
            "user_id": "00f4aa18361c44d3b4c5dd444b76fe7e"
        },
        {
            "status": "offline",
            "user_id": "03900bb681ee47dfaf100743d2fb38e6"
        },
        {
            "status": "offline",
            "user_id": "673b15e889df4e4aaa33b46d1b433189"
        },
        {
            "status": "offline",
            "user_id": "cbbe3baf49d44bb0bb794f2dd4cc5ec2"
        }
        ]
---
###### WEB更新会话已读状态
   1. 接口定义:

    更新当前会话内消息为已读;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/updateMsgStatus

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'
    4.2 msg_to:'asxa3b15easadd2211a33b46d1b433189'


    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": ""
     }
---
###### WEB查询会话中某条消息已读人数
   1. 接口定义:

    WEB查询会话中某条消息已读人数;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findReadMsg

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
          "sessionReads": [
            {
                "msg_id": "02286574-2875-4ab7-a5df-f92eb6a9b0d5",
                "readNum": 2
            }
          ]
     }
---
###### WEB查询单聊中某条消息已读人数
   1. 接口定义:

    WEB查询单聊中某条消息已读人数;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findChatReadMsg

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'
    4.2 msg_to:'673b15e889df4e4aaa33b46d1b433189'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
          "sessionReads": [
            {
                "msg_id": "02286574-2875-4ab7-a5df-f92eb6a9b0d5",
                "readNum": 2
            }
          ]
     }
---
###### WEB查询某条消息已读情况
   1. 接口定义:

    WEB查询某条消息已读情况;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findMsgStatusList

    4. 参数

    4.1 msg_id:'673b15e889df4e4aaa33b46d1b433189'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
          "ofStatusList": [
            {
                "id_": 0,
                "msg_id": "c032ef6d-95df-44f7-a3e0-bdc0535122f9",
                "msg_to": "00f4aa18361c44d3b4c5dd444b76fe7e",
                "msg_type": "MTS-000",
                "session_id": "54470ede-3e78-4a8a-9362-8edbf678e083",
                "status": 1
            }
           ]
     }
---
###### WEB新增群公告
   1. 接口定义:

    WEB新增群公告;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/addPubact

    4. 参数

    4.1 title:'标题'
    4.2 content:'内容'
    4.3 user_id:'673b15e889df4e4aaa33b46d1b433189'
    4.4 session_id:'673b15e889df4e4aaa33b46d1b433189'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
     }
---
###### WEB查询会话内所有群公告
   1. 接口定义:

    WEB查询会话内所有群公告;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/findPubactList

    4. 参数

    4.1 session_id:'673b15e889df4e4aaa33b46d1b433189'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
          "pubactList": [
              {
                  "content": "内容。。。。",
                  "id_": 9353,
                  "session_id": "673b15e889df4e4aaa33b46d1b433189",
                  "title": "主题",
                  "ts": "1536128472063",
                  "user_id": "d88wa-22m112j3-dasdawdd-w2123"
              }
          ],
     }
---
###### WEB修改群公告
   1. 接口定义:

    WEB修改群公告;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/modifyPubact

    4. 参数

    4.1 id_:'9421'
    4.2 title:'标题'
    4.3 content:'内容'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
     }
---
###### WEB删除群公告
   1. 接口定义:

    WEB删除群公告;http请求

    2. 接口流程:

    clientA->openfire->clientA

    a. client发送http请求给openfire服务器
    b. openfire处理后返回结果

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/deletePubact

    4. 参数

    4.1 id_:'标题'

    5. 返回值:json对象
     {
         "result": "ok",
         "result_detail": "",
     }
---



###### 系统消息:创建会话
    1. 接口定义:

    系统消息:创建会话;MTS-105

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            session_user:'673b15e889df4e4aaa33b46d1b433189',
            subscribers:[
                {
                    user_id:'f1939b73a9fa4b78aa904d0933b26d3f',
                    user_name:'A',
                },
                {
                    user_id:'03900bb681ee47dfaf100743d2fb38e6',
                    user_name:'B',
                }
            ]
        }
    ]

---
###### 系统消息:修改会话名称
    1. 接口定义:

    系统消息:修改会话名称;MTS-102

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            session_name:'hello,abc'
        }
    ]

---
###### 系统消息:邀请订阅者
    1. 接口定义:

    系统消息:邀请订阅者;MTS-106

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            subscribers:[
                {
                    user_id:'f1939b73a9fa4b78aa904d0933b26d3f',
                    user_name:'A',
                },
                {
                    user_id:'03900bb681ee47dfaf100743d2fb38e6',
                    user_name:'B',
                }
            ]
        }
    ]

---
###### 系统消息:移除订阅者
    1. 接口定义:

    系统消息:移除订阅者;MTS-103

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            subscribers:[
                {
                    user_id:'f1939b73a9fa4b78aa904d0933b26d3f',
                    user_name:'A',
                },
                {
                    user_id:'03900bb681ee47dfaf100743d2fb38e6',
                    user_name:'B',
                }
            ]
        }
    ]

---
###### 系统消息:退出会话
    1. 接口定义:

    系统消息:退出会话;MTS-104

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            user_id:'f1939b73a9fa4b78aa904d0933b26d3f',
            user_name:'A'
        }
    ]

---

###### 系统消息:消息撤回
    1. 接口定义:

    系统消息:消息撤回;MTS-101

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
            msg_id:'b8cf1de5-a12f-4480-82a7-0f084440f8a0'
        }
    ]

---
###### 解散群组
    1. 接口定义:

    解散群组;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/dissolved

    4. 参数

    4.1 session_id:'0804f124-7551-4148-ae32-c56ad409b290'
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
    }

---
###### 系统消息:解散群组
    1. 接口定义:

    系统消息:解散群组;MTS-107

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            session_id:'78b3a607-8797-4d95-bc15-7dfb62a01f75',
        }
    ]

---
##### 审批
    1. 接口定义
    审批;移动端忽略接口请求,此为服务器端调用
    2. 接口流程
    a. 服务端审批
    b. 消息推送引擎推送
    c. 调用openfire将消息推送到移动端
    d. 移动端处理审批消息
    
    注：移动端只参与消息处理，不处理接口调动
    
    3. 请求地址:
    http://coolweb.club:9595/plugins/tlim/approval
    4. 请求参数
    4.1 params:[
        {
            bizId:'',
            docTitle:'',
            solId:'',
            spType:'1052M001',
            wzType:'',
            taskId:''
        }
    ]
    4.2 msg_to:''
    5. body
    [
        {
            bizId:'',
            docTitle:'',
            solId:'',
            spType:'1052M001',
            wzType:'',
            taskId:''
        }
    ]

---

##### 退回
    1. 接口定义
    退回,http请求
    2. 接口流程
    a. 调用退回接口http://ip:port/cap-aco/approveController/processBack
    b. 服务端接口处理请求，并调用消息推送引擎
    c. 消息推送引擎调用openfire推送退回消息给移动端
    d. 移动端处理退回消息
    
    注: 移动端只参与退回接口调用与消息处理
    3. 请求地址:
    http://coolweb.club:9595/plugins/tlim/approvalBack
    4. 参数
    4.1:params:{
        taskId:'',
        actId:'',
        comment:'',
        fieldName:'',
        userId:'',
        bizId:''
    }
    5. body
    [
        {
            bizId:'',
            docTitle:'',
            solId:'',
            spType:'1052M001',
            wzType:'',
            taskId:''
        }
    ]

---
###### 系统消息:单聊清空历史消息
    1. 接口定义:

    系统消息:单聊清空历史消息;MTT-200

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            info:'A发起清除历史消息申请，是否同意?同意后您与A的历史消息将被彻底删除'
        }
    ]

---
###### 单聊清空历史消息
    1. 接口定义:

    单聊清空历史消息;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/clearChatHistory

    4. 参数

    4.1 user_id:'0804f124-7551-4148-ae32-c56ad409b290'
    4.1 other_id:'0804f124-7551-4148-ae32-c56ad409b290'
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
    }

---

###### 系统消息:清空历史消息回执

    1. 接口定义:

    系统消息:清空历史消息回执;MTT-201

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3. body
    [
        {
            status:0
        }
    ]
    注:0代表同意，1代表不同意
---

###### 同步消息
    1. 接口定义:

    同步消息;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/syncMessage

    4. 参数

    4.1 msg_to:'c66ddc90-5814-4afd-8598-8b37e3cb8f1a'
    4.1 msg_id:'cbf90f1bcc9f412eb2c8a0c0dc138ed8'
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
         "messageList":[
              {
                 "body": "[{\"session_id\":\"85028e68-be82-412e-b147-155840ebed88\",\"text\":\"123\"}]",
                 "msg_from": "e3e9dfddf4e84c02850c434e69942ada",
                 "msg_id": "d8cbcfbd-33fb-4614-b715-ac4f37c24282",
                 "msg_time": "1541488419370",
                 "msg_to": "cbf90f1bcc9f412eb2c8a0c0dc138ed8",
                 "msg_type": "MTS-000"
              }
         ]
    }

---

###### 当前用户是否有未读消息
    1. 接口定义:

    同步消息;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/hasUnread

    4. 参数

    4.1 user_id:'c66ddc90-5814-4afd-8598-8b37e3cb8f1a'
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
         "data":true/false
    }

---

###### AppPushCode
    1. 接口定义:

    修改AppPushCode;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/registerAppPushCode

    4. 参数

    4.1 user_id:'c66ddc90-5814-4afd-8598-8b37e3cb8f1a'
    4.2 registrationId:'2jh1h2-addawa-213fvvcxza21-saas'
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
    }

---
###### 离线消息
    1. 接口定义:

    离线消息;http请求

    2. 接口流程:

    clientA->openfire->clientB

    a. clientA发送消息体到openfire服务器
    b. openfire服务器解析、校验、存储
    c. 消息推送给clientB

    3.请求地址
    http://coolweb.club:9595/plugins/tlim/getOffline

    4. 参数

    4.1 user_id:'c66ddc90-5814-4afd-8598-8b37e3cb8f1a'
    4.2 msg_id:'d8cbcfbd-33fb-4614-b715-ac4f37c24282'
    4.2 getThenClear:0
    5. 返回值:json对象
    {
         "result": "ok",
         "result_detail": "",
         "message_list":[
            {
                "body": "[{\"session_id\":\"85028e68-be82-412e-b147-155840ebed88\",\"text\":\"123\"}]",
                "msg_from": "e3e9dfddf4e84c02850c434e69942ada",
                "msg_id": "d8cbcfbd-33fb-4614-b715-ac4f37c24282",
                "msg_time": "1541488419370",
                "msg_to": "cbf90f1bcc9f412eb2c8a0c0dc138ed8",
                "msg_type": "MTS-000",
                "recv":
            },
            {
                xxx
            },
            {
                xxx
            }

         ]
    }
    注:recv [0:移动端未收,web未收]/[1:移动端未收,web已收]
    getThenClear [0:获取并清空离线消息]/[1:获取但是不清空离线消息]

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
## 九、会话类型

```
0:群聊
1:审批
2:退回
99:单聊

```


## 十、改动

> 2018年9月7日
1. 修改会话时参数会话名"session_name"为空时，默认为不修改
2. WEB端查询所有会话新增字段valid，以便区分打开或关闭的会话
3. 版本1.2.23

> 2018年9月5日
1. 新增群公告接口
2. 版本1.2.22

> 2018年8月24日
1. 新增若干接口
2. 版本1.2.20


> 2018年8月15日
1. 新增从会话中查询所有订阅者接口
2. 新增获取会话历史消息记录接口
3. 新增获取单聊历史消息记录接口
4. 版本:1.2.14

> 2018年8月14日
1. 修改消息体字段msg为msg_from,to为msg_to
2. 修复修改会话时无法删除的bug
3. 修正查询所有会话接口返回值字段名
4. 新增查询会话接口
5. 实体字段名与数据库字段名统一
5. 版本:1.2.12

> 2018年8月13日
1. 新增已收回执、已读回执、消息撤回功能
2. 更新消息类型
3. 版本:1.2.6



> 2018年8月9日
1. 文本、图片、语音、文件类消息拆分成单与群聊两种
2. 新增修改会话与删除会话接口
3. 移除会话类型，调整文档结构
4. 若干bug修复
5. 修正http中文乱码
6. 版本:1.2.3

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

[depoyopenfire]:https://github.com/GepengCn/tonglian-openfire/blob/master/DEPLOY.md
