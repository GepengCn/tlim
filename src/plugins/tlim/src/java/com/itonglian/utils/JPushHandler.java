package com.itonglian.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPushHandler implements Runnable{

    private static final Logger Log = LoggerFactory.getLogger(JPushHandler.class);

    private static UserDao userDao = UserDaoImpl.getInstance();

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
            if(!"offline".equals(presence)){
                return;
            }
            JPushClient jpushClient = new JPushClient("3554ac2d8b507c13dae5e626", "42e2be00b39b8f9f177b119d", null, ClientConfig.getInstance());

            // For push, all you need do is to build PushPayload object.
            PushPayload payload = buildPushObject_all_all_alert(user_id,content);

            if(payload == null){
                return;
            }

            try {
                PushResult result = jpushClient.sendPush(payload);
                Log.error("Got result - " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
                Log.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                Log.error("Should review the error, and fix the request", e);
                Log.error("HTTP Status: " + e.getStatus());
                Log.error("Error Code: " + e.getErrorCode());
                Log.error("Error Message: " + e.getErrorMessage());
            }


        } catch (UserNotFoundException e) {
            Log.error("user_id="+user_id);
            Log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }

    }
    public static PushPayload buildPushObject_all_all_alert(String msgTo,String content) throws Exception {
        String appPushCode = userDao.findAppPushCodeByUserId(msgTo);
        if(StringUtils.isNullOrEmpty(appPushCode)){
            Log.error("appPushCode为空，不推送");
            return null;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(appPushCode))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(content)
                                .autoBadge()
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).build())
                        .build())
                // .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }
}
