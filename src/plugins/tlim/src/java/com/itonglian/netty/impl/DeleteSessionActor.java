package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.DeleteSession;

public class DeleteSessionActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        DeleteSession.Param param = JSONObject.parseObject(jsonValue, DeleteSession.Param.class);
        return new DeleteSession().submit(param.getSession_id());
    }
}
