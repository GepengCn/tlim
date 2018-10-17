package com.itonglian.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.itonglian.entity.User;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPushHandler implements Runnable{

    private static final Logger Log = LoggerFactory.getLogger(JPushHandler.class);

    private String user_id;

    private String content;

    public JPushHandler(String user_id,String content) {
        this.user_id = user_id;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            String presence = UserPresenceManager.getPresence(user_id);

            Log.error("Got Presence - " + presence);
            System.out.println("Got Presence - " + presence);
            if(!"offline".equals(presence)){
                return;
            }
            JPushClient jpushClient = new JPushClient("3554ac2d8b507c13dae5e626", "42e2be00b39b8f9f177b119d", null, ClientConfig.getInstance());

            // For push, all you need do is to build PushPayload object.
            PushPayload payload = buildPushObject_all_all_alert(user_id,content);

            try {
                PushResult result = jpushClient.sendPush(payload);
                Log.error("Got result - " + result);
                System.out.println("Got result - " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
                Log.error("Connection error, should retry later", e);
                System.out.println("Connection error, should retry later");

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                Log.error("Should review the error, and fix the request", e);
                System.out.println("Should review the error, and fix the request");
                Log.error("HTTP Status: " + e.getStatus());
                System.out.println("HTTP Status: " + e.getStatus());
                Log.error("Error Code: " + e.getErrorCode());
                System.out.println("Error Code: " + e.getErrorCode());
                Log.error("Error Message: " + e.getErrorMessage());
                System.out.println("Error Message: " + e.getErrorMessage());
            }


        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static PushPayload buildPushObject_all_all_alert(String msgTo,String content) throws Exception {
        User user = UserCacheManager.findUserByKey(msgTo);
        String appPushCode = user.getApp_push_code();
        if(StringUtils.isNullOrEmpty(appPushCode)){
            throw new Exception("appPushCode为空，不推送");
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(user.getApp_push_code()))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .autoBadge()
                                .build())
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }
}
