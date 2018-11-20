package com.itonglian.dao.impl;

import com.itonglian.dao.UserDao;
import com.itonglian.entity.User;
import com.itonglian.mapper.UserMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import com.itonglian.utils.UserCacheManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class UserDaoImpl implements UserDao {


    private static final UserDao userDao = new UserDaoImpl();

    private static final Logger Log = LoggerFactory.getLogger(UserDaoImpl.class);

    public static UserDao getInstance(){
        return userDao;
    }

    @Override
    public void syncUser() {
        UserManager userManager =XMPPServer.getInstance().getUserManager();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        try {
            List<User> userList = userMapper.findAll("N");

            Iterator<User> iterator = userList.iterator();
            while(iterator.hasNext()){
                User user = iterator.next();
                if(!userManager.isRegisteredUser(user.getUser_id())){
                    userManager.createUser(user.getUser_id(),"123",user.getUser_name(),user.getUser_email());
                }
                UserCacheManager.add(user);
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }

    }

    @Override
    public void clear() {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        try {
            userMapper.removeAll("admin");
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
    }

    @Override
    public String findAppPushCodeByUserId(String userId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        String appPushCode = "";
        try {
            appPushCode = userMapper.findAppPushCode(userId,"N");
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return appPushCode;
    }


}
