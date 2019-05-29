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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;



@Slf4j
public class NettyServer implements Runnable{

    private ChannelFuture channelFuture;

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
                log.debug("NettyServer 已启动");
                log.debug("Netty 监听端口:"+XMLProperties.getNettyServerPort());
            }
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
