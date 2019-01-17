package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.ModifySession;

public class ModifySessionActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        ModifySession.Param param = JSONObject.parseObject(jsonValue,ModifySession.Param.class);
        return new ModifySession().submit(param.getSessionId(),param.getSessionName(),param.getSubscribers());
    }
}
