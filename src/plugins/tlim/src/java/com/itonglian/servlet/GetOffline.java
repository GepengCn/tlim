package com.itonglian.servlet;

import com.alibaba.fastjson.JSON;
import com.itonglian.dao.MessageDao;
import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfCustomOffline;
import com.itonglian.netty.NettyClient;
import com.itonglian.utils.CustomThreadPool;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import com.itonglian.utils.XMLProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetOffline extends BaseServlet {

    OfflineDao offlineDao = OfflineDaoImpl.getInstance();

    MessageDao messageDao = MessageDaoImpl.getInstance();

    private List<OfCustomOffline> customOfflineList;

    @Override
    protected String mapper() {
        return "tlim/getOffline";
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter printWriter = resp.getWriter();

        String user_id = req.getParameter("user_id");

        String msg_id = req.getParameter("msg_id");

        String getThenClear = req.getParameter("getThenClear");

        boolean success = submit(msg_id,getThenClear,user_id);

        if(XMLProperties.getNettyClient()&&success){
            CustomThreadPool.getInstance().getExecutorService().execute(new NettyClient(MessageUtils.getMapper(mapper()), JSON.toJSONString(new Param(msg_id,getThenClear,user_id))));
        }

        doBack(new BackJson("ok","",customOfflineList),printWriter);

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Param{
        private String msg_id;
        private String getThenClear;
        private String user_id;


    }
    public boolean submit(String msg_id,String getThenClear,String user_id){

        try {
            boolean clear = true;

            String msg_time = messageDao.findMessageTime(msg_id);

            if(!StringUtils.isNullOrEmpty(getThenClear)&&"1".equals(getThenClear)){
                clear = false;
            }

            if(StringUtils.isNullOrEmpty(msg_time)){
                customOfflineList = offlineDao.findByUser(user_id);
            }else{
                customOfflineList = offlineDao.findByUserAfterThatTime(user_id,msg_time);
            }
            if(clear){
                offlineDao.deleteByUser(user_id);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }
        return true;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    private class BackJson extends BaseServlet.BackJson {
        private String result;

        private String result_detail;

        List<OfCustomOffline> message_list;

    }
}
