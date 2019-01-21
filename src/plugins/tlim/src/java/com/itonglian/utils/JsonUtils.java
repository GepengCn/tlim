package com.itonglian.utils;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils {

    private static final Logger Log = LoggerFactory.getLogger(JsonUtils.class);

    public static String getSessionId(String jsonStr){

        List<SessionEntity> sessionEntities = JSONArray.parseArray(jsonStr,SessionEntity.class);

        if(sessionEntities==null||sessionEntities.size()==0){
            return null;
        }

        SessionEntity sessionEntity = sessionEntities.get(0);

        if(sessionEntity==null){
            return null;
        }

        return sessionEntity.getSession_id();
    }

    public static List<Revoke> getRevoke(String jsonStr){
        List<Revoke> revokeList = JSONArray.parseArray(jsonStr,Revoke.class);
        return revokeList;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SessionEntity{
        private String session_id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Revoke{

        private String msg_id;

        private String msg_to;

    }
}
