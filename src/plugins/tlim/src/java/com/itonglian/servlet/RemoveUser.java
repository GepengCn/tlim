package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itonglian.dao.UserDao;
import com.itonglian.dao.impl.UserDaoImpl;
import com.itonglian.entity.User;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.UserCacheManager;
import org.jivesoftware.admin.AuthCheckFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class RemoveUser extends HttpServlet {
    private static final Logger Log = LoggerFactory.getLogger(RemoveUser.class);

    UserDao userDao = UserDaoImpl.getInstance();


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AuthCheckFilter.addExclude("tlim/removeUser");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MessageUtils.setResponse(resp);

        PrintWriter printWriter = resp.getWriter();

        String userList = req.getParameter("userList");

        List<User> jsonUser = JSONArray.parseArray(userList,User.class);

        Iterator<User> iterator = jsonUser.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();
            String user_id = user.getUser_id();
            userDao.remove(user_id);
            UserCacheManager.remove(user_id);
        }

        doBack(new BackJson("ok",""),printWriter);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void doBack(BackJson backJson, PrintWriter printWriter){
        printWriter.append(JSONObject.toJSONString(backJson));
        printWriter.flush();
        printWriter.close();
    }

    private class BackJson{
        private String result;

        private String result_detail;


        public BackJson(String result, String result_detail) {
            this.result = result;
            this.result_detail = result_detail;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult_detail() {
            return result_detail;
        }

        public void setResult_detail(String result_detail) {
            this.result_detail = result_detail;
        }

    }
}
