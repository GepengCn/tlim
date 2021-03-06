package com.itonglian.dao.impl;

import com.itonglian.dao.PubactDao;
import com.itonglian.entity.OfPubact;
import com.itonglian.enums.DBType;
import com.itonglian.mapper.mysql.PubactMapper;
import com.itonglian.utils.DBUtils;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.MyBatisSessionFactory;
import com.itonglian.utils.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PubactDaoImpl implements PubactDao {


    private static final PubactDao pubactDao = new PubactDaoImpl();

    public static PubactDao getInstance(){
        return pubactDao;
    }

    private static final Logger Log = LoggerFactory.getLogger(PubactDaoImpl.class);

    @Override
    public boolean add(String title, String content, String user_id,String session_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();

        try {
            if(DBUtils.getDBType()== DBType.SQLServer){
                com.itonglian.mapper.sqlserver.PubactMapper pubactMapper = session.getMapper(com.itonglian.mapper.sqlserver.PubactMapper.class);
                pubactMapper.insertPubact(UUID.randomUUID().toString(),title,content,user_id, MessageUtils.getTs(),session_id);
            }else{
                PubactMapper pubactMapper = session.getMapper(PubactMapper.class);
                pubactMapper.insertPubact(UUID.randomUUID().toString(),title,content,user_id, MessageUtils.getTs(),session_id);
            }
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
    public List<OfPubact> findBySession(String session_id) {
        List<OfPubact> ofPubactList = new ArrayList<>();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        PubactMapper pubactMapper = session.getMapper(PubactMapper.class);
        try {
            ofPubactList = pubactMapper.findBySession(session_id);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofPubactList;
    }

    @Override
    public boolean update(String id_, String title, String content) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        PubactMapper pubactMapper = session.getMapper(PubactMapper.class);
        try {
            pubactMapper.update(StringUtils.stringToLong(id_),title,content);
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
    public boolean delete(String id_) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        PubactMapper pubactMapper = session.getMapper(PubactMapper.class);
        try {
            pubactMapper.delete(StringUtils.stringToLong(id_));
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
