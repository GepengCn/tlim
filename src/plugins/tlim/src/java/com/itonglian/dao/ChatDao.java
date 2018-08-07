package com.itonglian.dao;

import com.itonglian.entity.OfMessage;

import java.util.List;
import java.util.Map;

public interface ChatDao {

    public void add(OfMessage ofMessage);

    public void delete(String msgId);

    public void update(OfMessage ofMessage);

    public OfMessage findEntityById(String msgId);

    public List<OfMessage> findList(Map<String,Object> conditions);
}
