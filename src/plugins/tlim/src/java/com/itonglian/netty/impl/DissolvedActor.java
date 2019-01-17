package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.Dissolved;

public class DissolvedActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        Dissolved.Param param = JSONObject.parseObject(jsonValue, Dissolved.Param.class);
        return new Dissolved().submit(param.getSession_id());
    }
}
