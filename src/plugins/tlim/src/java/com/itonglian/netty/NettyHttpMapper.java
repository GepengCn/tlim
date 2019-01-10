package com.itonglian.netty;

import com.itonglian.netty.impl.MessageActor;
import com.itonglian.netty.impl.SystemMessageActor;
import com.itonglian.utils.StringConstants;
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
        logger.info("请求已达NettyHttpMapper...,请求方法["+this.path+"]");
        switch (this.path){
            case "systemMessage":
                nettyHttpActor = new SystemMessageActor();
                break;
            case "approval":
                nettyHttpActor = new SystemMessageActor();
                break;
            case "message":
                nettyHttpActor = new MessageActor();
                break;
            default:
                break;
        }
    }
    public boolean execute(){
        return nettyHttpActor.execute(jsonValue);
    }

    private String parsePath(String path){
        int start = path.indexOf(StringConstants.NETTY)+StringConstants.NETTY.length();
        int end = path.indexOf("?");
        if(end!=-1){
            return path.substring(start,end);
        }
        return path.substring(start);
    }

}
