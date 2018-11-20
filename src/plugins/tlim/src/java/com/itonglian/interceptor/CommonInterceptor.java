package com.itonglian.interceptor;

import com.itonglian.bean.Protocol;
import com.itonglian.dao.StatusDao;
import com.itonglian.dao.impl.StatusDaoImpl;
import com.itonglian.entity.OfStatus;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.JsonUtils;
import com.itonglian.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Message;

import java.util.Iterator;
import java.util.List;

public abstract class CommonInterceptor {

    private StatusDao statusDao = StatusDaoImpl.getInstance();

    protected static final Logger Log = LoggerFactory.getLogger(CommonInterceptor.class);

    protected CustomThreadPool customThreadPool = CustomThreadPool.getInstance();


    public void handlerRead(final Protocol protocol,final Message message){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<JsonUtils.Revoke> list = JsonUtils.getRevoke(protocol.getBody());
                Iterator<JsonUtils.Revoke> iterator = list.iterator();
                while(iterator.hasNext()){
                    JsonUtils.Revoke revoke = iterator.next();
                    OfStatus ofStatus = new OfStatus();
                    if(MessageUtils.where.web==MessageUtils.fromWeb(message)){
                        ofStatus.setMsg_id(revoke.getMsg_id());
                        ofStatus.setStatus(1);
                        ofStatus.setReader(protocol.getMsg_from());
                    }else if(MessageUtils.where.mobile==MessageUtils.fromWeb(message)){
                        ofStatus.setMsg_id(revoke.getMsg_id());
                        ofStatus.setStatus(2);
                        ofStatus.setReader(protocol.getMsg_from());
                    }
                    statusDao.save(ofStatus);
                }
            }
        });

        customThreadPool.getExecutorService().execute(thread);

    }
}
