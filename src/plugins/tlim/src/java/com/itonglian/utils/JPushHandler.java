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

    private static final Logger Log = LoggerFactory.getLogger(JPushHandler.class);


    public JPushHandler(String appPushCode,String content) {
        this.content = content;
        this.appPushCode = appPushCode;
    }

    @Override
    public void run() {
        JPushClient jpushClient = new JPushClient("3554ac2d8b507c13dae5e626", "42e2be00b39b8f9f177b119d", null, ClientConfig.getInstance());
        try {
            content = StringUtils.contentfilter(content);
            PushPayload payload = buildPushObject_all_all_alert(appPushCode,content);

            if(payload == null){
                return;
            }
            PushResult result = jpushClient.sendPush(payload);

            jpushClient.close();
        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
    public static PushPayload buildPushObject_all_all_alert(String appPushCode,String content) throws Exception {

        if(StringUtils.isNullOrEmpty(appPushCode)){
           // Log.error("appPushCode为空，不推送");
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
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).build())
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }
}
