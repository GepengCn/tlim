package com.itonglian.netty;

import com.itonglian.utils.StringConstants;
import com.itonglian.utils.XMLProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class NettyClient implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String jsonValue;

    private String method;

    public NettyClient(String method,String jsonValue) {
        this.method = method;
        this.jsonValue = jsonValue;
    }

    @Override
    public void run() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpResponseDecoder());
                            socketChannel.pipeline().addLast(new HttpRequestEncoder());
                            socketChannel.pipeline().addLast(new HttpClientHandler());
                        }
                    });
            String host = XMLProperties.getNettyClientIp();
            int port = XMLProperties.getNettyClientPort();
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if(channelFuture.isSuccess()){
                logger.info("NettyClient 已连接至NettyServer");
                logger.info("NettyServer ip:"+host+",port:"+port);
            }else {
                return;
            }
            String url = "http://"+host+":"+port+XMLProperties.getChannelCode()+StringConstants.NETTY+method;
            URI uri = new URI(url);
            logger.info("NettyClient 正在连接HTTP请求:"+url);
            logger.info("NettyClient HTTP请求参数:"+jsonValue);
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                                         uri.toASCIIString(), Unpooled.wrappedBuffer(jsonValue.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            channelFuture.channel().write(request);
            channelFuture.channel().flush();
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            workerGroup.shutdownGracefully();
        }

    }

}
