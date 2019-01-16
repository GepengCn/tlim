package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.AddSession;

public class AddSessionActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        AddSession.Param param = JSONObject.parseObject(jsonValue,AddSession.Param.class);
        return new AddSession().submit(param.getSessionType(),param.getRequestUser(),param.getSubscribers());
    }
}
