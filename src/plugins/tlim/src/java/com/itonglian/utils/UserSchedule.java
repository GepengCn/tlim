package com.itonglian.utils;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.User;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class UserSchedule implements Job {

    UserDao userDao = UserDaoImpl.getInstance();

    UserManager userManager = XMPPServer.getInstance().getUserManager();

    private static final Logger Log = LoggerFactory.getLogger(UserSchedule.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Log.error("执行用户同步...");

        int dbCount = userDao.count();

        if(dbCount==UserCacheManager.count()){
            Log.error("用户一致，撤销本次同步...");
            return;
        }
        int nowCount = UserCacheManager.count();
        Log.error("用户不一致[当前用户数:"+nowCount+",数据库用户数:"+dbCount+"]开始查询所有用户...");
        List<User> userList = userDao.findAll();

        Iterator<User> iterator = userList.iterator();

        while(iterator.hasNext()){

            User user = iterator.next();

            if(UserCacheManager.contain(user.getUser_id())){
                continue;
            }

            if(!userManager.isRegisteredUser(user.getUser_id())){
                try {
                    userManager.createUser(user.getUser_id(),"123",user.getUser_name(),user.getUser_email());
                } catch (UserAlreadyExistsException e) {
                    Log.error(ExceptionUtils.getFullStackTrace(e));
                }
            }
            UserCacheManager.add(user);

        }

        Log.error("同步完成,总计同步"+(dbCount-nowCount)+"人...");


    }
}
