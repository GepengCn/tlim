package com.itonglian.view;

import java.util.ArrayList;
import java.util.List;

public class MessageStatistics {

    private static List<String> list = new ArrayList<String>();

    public static List<String> queryList(){
        return list;
    }

    public static void add(String msg){
        list.add(msg);
    }

    public static void clearAll(){
        list.clear();
    }

}
