package com.itonglian.dao;

import com.itonglian.entity.OfStatus;

import java.util.List;

public interface StatusDao {

    void add(OfStatus ofStatus);

    void update(String msg_id,String reader,int status);

    List<OfStatus> findByReader(String reader);

    void delete(String msg_id);

    boolean isExist(String msg_id,String reader);

    void save(OfStatus ofStatus);


}
