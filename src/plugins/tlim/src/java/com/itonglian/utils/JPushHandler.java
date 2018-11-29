package com.itonglian.utils;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPushHandler implements Runnable{

    private String content;

    private String appPushCode;

    private String sessionName;

    private PushWhere pushWhere;

    private static final Logger Log = LoggerFactory.getLogger(JPushHandler.class);


    public JPushHandler(String appPushCode,String content,String sessionName,String combineKv,PushWhere pushWhere) {
        this.content = content;
        this.appPushCode = appPushCode;
        this.sessionName = sessionName;
        this.pushWhere = pushWhere;
    }

    @Override
    public void run() {
        JPushClient jpushClient = new JPushClient("83a8c468321366eb977c61f2", "90fd74bf44097c9bb69c3fd1", null, ClientConfig.getInstance());
        try {
            PushPayload payload;

            content = StringUtils.contentfilter(content);
            switch (pushWhere){
                case all:
                    payload = buildPushObject_all_alert(appPushCode,content,sessionName);
                    break;
                case ios:
                    payload = buildPushObject_ios_alert(appPushCode,content,sessionName);
                    break;
                case android:
                    payload = buildPushObject_android_alert(appPushCode,content,sessionName);
                    break;
                default:
                    return;
            }

            if(payload==null){
                return;
            }
            PushResult result = jpushClient.sendPush(payload);
            jpushClient.close();
        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
    public PushPayload buildPushObject_all_alert(String appPushCode,String content,String sessionName) throws Exception {
        if(StringUtils.isNullOrEmpty(appPushCode)){
            return null;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(appPushCode))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .autoBadge()
                                .setSound("default")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(content)
                                .setTitle(sessionName)
                                .build())
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }


    public PushPayload buildPushObject_ios_alert(String appPushCode,String content,String sessionName) throws Exception {
        if(StringUtils.isNullOrEmpty(appPushCode)){
            return null;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(appPushCode))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .autoBadge()
                                .setSound("default")
                                .build())
                        /*.addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(content)
                                .setTitle(sessionName)
                                .build())*/
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }

    public PushPayload buildPushObject_android_alert(String appPushCode,String content,String sessionName) throws Exception {
        if(StringUtils.isNullOrEmpty(appPushCode)){
            return null;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(appPushCode))
                .setNotification(Notification.newBuilder()
                        /*.addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .autoBadge()
                                .setSound("default")
                                .build())*/
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(content)
                                .setTitle(sessionName)
                                .build())
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }


    public enum PushWhere{

        all,
        ios,
        android

    }


}
