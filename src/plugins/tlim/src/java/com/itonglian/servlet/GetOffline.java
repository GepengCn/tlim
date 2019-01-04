package com.itonglian.servlet;

import com.itonglian.dao.MessageDao;
import com.itonglian.dao.OfflineDao;
import com.itonglian.dao.impl.MessageDaoImpl;
import com.itonglian.dao.impl.OfflineDaoImpl;
import com.itonglian.entity.OfCustomOffline;
import com.itonglian.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetOffline extends BaseServlet {

    OfflineDao offlineDao = OfflineDaoImpl.getInstance();

    MessageDao messageDao = MessageDaoImpl.getInstance();

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

        boolean clear = true;

        String msg_time = messageDao.findMessageTime(msg_id);

        if(!StringUtils.isNullOrEmpty(getThenClear)&&"1".equals(getThenClear)){
            clear = false;
        }

        List<OfCustomOffline> customOfflineList;

        if(StringUtils.isNullOrEmpty(msg_time)){
            customOfflineList = offlineDao.findByUser(user_id);
        }else{
            customOfflineList = offlineDao.findByUserAfterThatTime(user_id,msg_time);
        }
        if(clear){
            offlineDao.deleteByUser(user_id);
        }

        doBack(new BackJson("ok","",customOfflineList),printWriter);

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
