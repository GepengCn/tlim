package com.itonglian.netty;

import com.alibaba.fastjson.JSON;
import com.itonglian.utils.StringConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(! (msg instanceof FullHttpRequest)){
            send(ctx,parseResult(new Result("error","未知请求")), HttpResponseStatus.BAD_REQUEST);
            return;
        }
        FullHttpRequest httpRequest = (FullHttpRequest)msg;
        try{
            String path=httpRequest.uri();          //获取路径
            String params = getParams(httpRequest);     //获取参数
            HttpMethod method=httpRequest.method();//获取请求方法
            //如果不是这个路径，就直接返回错误
            if(path==null||!path.contains(StringConstants.NETTY)){
                send(ctx,parseResult(new Result("error","非法请求")),HttpResponseStatus.BAD_REQUEST);
                return;
            }
            logger.info("已接受HTTP请求:"+path);
            logger.info("HTTP请求类型:"+method);
            logger.info("请求参数params:"+params);
            //如果是GET请求
            if(HttpMethod.GET.equals(method)){
                //接受到的消息，做业务逻辑处理...
                send(ctx,parseResult(new Result("ok","GET请求")),HttpResponseStatus.OK);
                return;
            }
            //如果是POST请求
            if(HttpMethod.POST.equals(method)){
                //接受到的消息，做业务逻辑处理...
                send(ctx,parseResult(new Result("ok","POST请求")),HttpResponseStatus.OK);
                boolean success = new NettyHttpMapper(path,params).execute();
                logger.info("处理结果["+success+"]...");
                return;
            }

            //如果是PUT请求
            if(HttpMethod.PUT.equals(method)){
                //接受到的消息，做业务逻辑处理...
                send(ctx,parseResult(new Result("ok","PUT请求")),HttpResponseStatus.OK);
                return;
            }
            //如果是DELETE请求
            if(HttpMethod.DELETE.equals(method)){
                //接受到的消息，做业务逻辑处理...
                send(ctx,parseResult(new Result("ok","DELETE请求")),HttpResponseStatus.OK);
                return;
            }
        }catch(Exception e){
            logger.error("处理请求失败!");
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }finally{
            //释放请求
            httpRequest.release();
        }
    }
    /**
     * 获取body参数
     * @param request
     * @return
     */
    private String getParams(FullHttpRequest request){
        ByteBuf buf = request.content();
        return buf.toString(CharsetUtil.UTF_8);
    }

    /**
     * 发送的返回值
     * @param ctx     返回
     * @param context 消息
     * @param status 状态
     */
    private void send(ChannelHandlerContext ctx, String context,HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /*
     * 建立连接时，返回消息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接的客户端地址:" + ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    public static String parseResult(Result result){
        return JSON.toJSONString(result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Result{

        private String result;

        private String result_info;

    }

}
