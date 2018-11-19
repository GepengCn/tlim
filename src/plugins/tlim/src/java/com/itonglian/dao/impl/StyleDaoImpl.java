package com.itonglian.dao.impl;

import com.itonglian.dao.StyleDao;
import com.itonglian.entity.OfStyle;
import com.itonglian.mapper.StyleMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StyleDaoImpl implements StyleDao {

    private static final Logger Log = LoggerFactory.getLogger(StyleDaoImpl.class);

    private static final StyleDao styleDao = new StyleDaoImpl();

    public static StyleDao getInstance(){
        return styleDao;
    }

    @Override
    public void add(OfStyle ofStyle) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StyleMapper styleMapper = session.getMapper(StyleMapper.class);
        try {
            styleMapper.insertStyle(ofStyle);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public  List<OfStyle> query(String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
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
}
