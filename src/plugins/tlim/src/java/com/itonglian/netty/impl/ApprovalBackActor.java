package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.ApprovalBack;

public class ApprovalBackActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        ApprovalBack.Param param = JSONObject.parseObject(jsonValue,ApprovalBack.Param.class);
        return new ApprovalBack().submit(param.getParams(),param.getMsg_to(),param.getMsg_type());
    }
}
