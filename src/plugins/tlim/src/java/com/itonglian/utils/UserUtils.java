package com.itonglian.utils;

import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.User;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class UserUtils {

    private static final Logger Log = LoggerFactory.getLogger(UserUtils.class);

    public static void addUser(List<User> userList){
        org.jivesoftware.openfire.user.UserManager userManager = XMPPServer.getInstance().getUserManager();

        Iterator<User> iterator = userList.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(!userManager.isRegisteredUser(user.getUser_id())){
                try {
                    userManager.createUser(user.getUser_id(),"123",user.getUser_name(),user.getUser_email());
                } catch (UserAlreadyExistsException e) {
                    Log.error(ExceptionUtils.getFullStackTrace(e));
                }
            }
            UserCacheManager.add(user);
        }
    }

    public static void removeUser(List<User> userList){
        UserDao userDao = UserDaoImpl.getInstance();

        Iterator<User> iterator = userList.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();
            String user_id = user.getUser_id();
            userDao.remove(user_id);
            UserCacheManager.remove(user_id);
        }
    }
}
