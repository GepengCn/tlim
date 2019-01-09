package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.SystemMessage;

public class SystemMessageActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        SystemMessage.systemMessageJson systemMessageJson = JSONObject.parseObject(jsonValue,SystemMessage.systemMessageJson.class);
        return new SystemMessage().submit(systemMessageJson.getParams(),systemMessageJson.getMsg_to(),systemMessageJson.getMsg_type());
    }
}
