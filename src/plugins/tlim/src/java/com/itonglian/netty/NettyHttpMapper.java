package com.itonglian.netty;

import com.itonglian.netty.impl.SystemMessageActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyHttpMapper {

    private NettyHttpActor nettyHttpActor;

    private String path;

    private String jsonValue;

    private static final Logger logger = LoggerFactory.getLogger(NettyHttpMapper.class);


    public NettyHttpMapper(String path,String jsonValue) {
        this.path = parsePath(path);
        this.jsonValue = jsonValue;
        switch (this.path){
            case "systemMessage":
                nettyHttpActor = new SystemMessageActor();
                break;
            default:
                break;
        }
    }
    public boolean execute(){
        return nettyHttpActor.execute(jsonValue);
    }

    private String parsePath(String path){
        String symbol = "/netty/";
        int start = path.indexOf(symbol)+symbol.length();
        int end = path.indexOf("?");
        if(end!=-1){
            return path.substring(start,end);
        }
        return path.substring(start);
    }

}
