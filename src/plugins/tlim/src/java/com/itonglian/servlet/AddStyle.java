package com.itonglian.servlet;

import com.itonglian.dao.StyleDao;
import com.itonglian.dao.impl.StyleDaoImpl;
import com.itonglian.entity.OfStyle;
import com.itonglian.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddStyle extends BaseServlet {

    StyleDao styleDao = StyleDaoImpl.getInstance();

    @Override
    protected String mapper() {
        return "tlim/addStyle";
    }


    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String style_name = req.getParameter("style_name");

        int style_value = StringUtils.stringToInt(req.getParameter("style_value"));

        String user_id = req.getParameter("user_id");

        submit(style_name,style_value,user_id);
    }

    public boolean submit(String style_name,int style_value,String user_id) {
        boolean success;
        if(styleDao.isExist(style_name,user_id)){
            success = styleDao.update(style_value,style_name,user_id);
        }else{
            success = styleDao.add(new OfStyle(style_name,style_value,user_id));
        }
        return success;
    }
}
