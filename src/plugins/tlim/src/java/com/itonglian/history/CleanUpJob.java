package com.itonglian.history;

import com.itonglian.dao.MessageDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.entity.Message;
import com.itonglian.utils.XMLProperties;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CleanUpJob implements Job {

    MessageDao messageDao = MessageDaoImpl.getInstance();

    StatusDao statusDao = StatusDaoImpl.getInstance();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {




        logger.info("启动周期:"+XMLProperties.getCleanUpInterval()+"h");

        logger.info("待清理"+XMLProperties.getCleanUpHowmanydays()+"天之前的消息");

        DateTime dateTime = DateTime.now().minusDays(XMLProperties.getCleanUpHowmanydays());

        List<Message> messageList = messageDao.findByTime(dateTime.toDate().getTime());

        logger.info("待清理消息总数:"+messageList.size());

        Iterator<Message> iterator = messageList.iterator();

        while(iterator.hasNext()){

            Message message = iterator.next();

            logger.info("=====================================");

            logger.info("正在清理消息[msg_id="+message.getMsg_id()+"]");

            messageDao.deleteById(message.getMsg_id());

            statusDao.delete(message.getMsg_id());

            logger.info("清理成功[msg_id="+message.getMsg_id()+"]");

            logger.info("=====================================");

        }

    }
}
