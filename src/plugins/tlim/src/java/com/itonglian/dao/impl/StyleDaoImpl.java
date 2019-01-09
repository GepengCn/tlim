package com.itonglian.dao.impl;

import com.itonglian.dao.StyleDao;
import com.itonglian.entity.OfStyle;
import com.itonglian.mapper.mysql.StyleMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StyleDaoImpl implements StyleDao {

    private static final Logger Log = LoggerFactory.getLogger(StyleDaoImpl.class);

    private static final StyleDao styleDao = new StyleDaoImpl();

    public static StyleDao getInstance(){
        return styleDao;
    }

    @Override
    public boolean add(OfStyle ofStyle) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StyleMapper styleMapper = session.getMapper(StyleMapper.class);
        try {
            ofStyle.setStyle_id(UUID.randomUUID().toString());
            styleMapper.insertStyle(ofStyle);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public  List<OfStyle> query(String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StyleMapper styleMapper = session.getMapper(StyleMapper.class);
        List<OfStyle> ofStyleList = new ArrayList<>();
        try {
            ofStyleList = styleMapper.findByUser(user_id);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofStyleList;
    }

    @Override
    public boolean isExist(String style_name, String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StyleMapper styleMapper = session.getMapper(StyleMapper.class);
        boolean isExist = false;
        try {
            int count = styleMapper.isExist(style_name,user_id);
            if(count>0){
                isExist = true;
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return isExist;
    }

    @Override
    public boolean update(int style_value, String style_name, String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StyleMapper styleMapper = session.getMapper(StyleMapper.class);
        try {
            styleMapper.update(style_value,style_name,user_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}
