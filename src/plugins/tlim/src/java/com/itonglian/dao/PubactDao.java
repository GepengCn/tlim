package com.itonglian.dao;

import com.itonglian.entity.OfPubact;

import java.util.List;

public interface PubactDao {

    boolean add(String title,String content,String user_id,String session_id);

    List<OfPubact> findBySession(String session_id);

    void update(String id_,String title,String content);

    void delete(String id_);
}
