package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.GetOffline;

public class GetOfflineActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        GetOffline.Param param = JSONObject.parseObject(jsonValue, GetOffline.Param.class);
        return new GetOffline().submit(param.getMsg_id(),param.getGetThenClear(),param.getUser_id());
    }
}
