package com.kaiser.aaa.myactionbarmenu.Utils;

/**
 * Created by aaa on 15-3-25.
 */
public class PathHelper {
    //内容详情页面
    public static  String contentpath(String id){
     String content_path ="http://webapi.yilule.com:5580/api/SingleRequest?sid="+id+"&count=6";
        return content_path;
    }
    public static String path_second="http://webapi.yilule.com:5580/api/Listen?dataId=12&pageIndex=1&pageSize=20";

}
