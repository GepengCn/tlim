package com.itonglian.netty.impl;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.netty.NettyHttpActor;
import com.itonglian.servlet.ClearChatHistory;

public class ClearChatHistoryActor implements NettyHttpActor {
    @Override
    public boolean execute(String jsonValue) {
        ClearChatHistory.Param param = JSONObject.parseObject(jsonValue, ClearChatHistory.Param.class);
        return new ClearChatHistory().submit(param.getUser_id(),param.getOther_id());
    }
}
