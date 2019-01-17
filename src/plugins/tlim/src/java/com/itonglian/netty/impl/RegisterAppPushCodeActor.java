package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.RegisterAppPushCode;

public class RegisterAppPushCodeActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        RegisterAppPushCode.Param param = JSONObject.parseObject(jsonValue,RegisterAppPushCode.Param.class);
        return new RegisterAppPushCode().submit(param.getUser_id(),param.getRegistrationId());
    }
}
