### 简介

多人会议消息

### 消息类型

`MTS-004`

### 简略结构
```
[
    {
        session_id:#{session_id},
        confId:#{confId},
        confPwd:#{confPwd},
        confName:#{confName},
        confHost:#{confHost},
        confTime:#{confTime}
    }
]
```
### 限制

`confId`
- 会议室ID:`string(64)`
`confPwd`
- 会议室密码:`string(64)`
`confName`
- 会议室名称:`string(256)`
`confHost`
- 会议主持人:`string(64)`
`confTime`
- 会议发起时间:`string(64)`


### 完整结构
```
{
    compress:#{compress},
    encode:#{encode},
    encrypt:#{encrypt},
    version:#{version},
    msg_from:#{msg_from},
    msg_to:#{msg_to},
    msg_id:#{msg_id},
    msg_type:'MTS-004',
    msg_time:#{msg_time},
    body:[{
             confId:#{confId},
             confPwd:#{confPwd},
             confName:#{confName},
             confHost:#{confHost},
             confTime:#{confTime}
          }
    ]
}
```
