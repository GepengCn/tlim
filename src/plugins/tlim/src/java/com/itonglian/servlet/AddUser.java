package com.itonglian.servlet;

import com.alibaba.fastjson.JSONArray;
import com.itonglian.entity.User;
import com.itonglian.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddUser extends BaseServlet {

    @Override
    protected String mapper() {
        return "tlim/addUser";
    }


    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String userList = req.getParameter("userList");

        List<User> jsonUser = JSONArray.parseArray(userList,User.class);

        UserUtils.addUser(jsonUser);

    }
}
