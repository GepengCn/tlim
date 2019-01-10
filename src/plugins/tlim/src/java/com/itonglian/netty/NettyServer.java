package com.itonglian.netty;

import com.itonglian.utils.XMLProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NettyServer implements Runnable{

    private ChannelFuture channelFuture;

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);


    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast("ServerEncoder", new HttpResponseEncoder());
                            channelPipeline.addLast("ServerDecoder", new HttpRequestDecoder());
                            channelPipeline.addLast("HttpObjectAggregator", new HttpObjectAggregator(XMLProperties.getHttpObjectAggregatorValue()));
                            channelPipeline.addLast("ServerHandler",new NettyServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, XMLProperties.getOptionSoBacklog())
            .childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture = serverBootstrap.bind(XMLProperties.getNettyServerPort()).sync();
            if(channelFuture.isSuccess()){
                logger.info("NettyServer 已启动");
                logger.info("Netty 监听端口:"+XMLProperties.getNettyServerPort());
            }
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
