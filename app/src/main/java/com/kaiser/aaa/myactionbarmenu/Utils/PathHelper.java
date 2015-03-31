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

   // int index,页数 String lat,String lng,int parentId（每页显示数据量）
    public static String firstpage(int index, String lat,String lng,int parentId){
        String path_firstpage="http://webapi.yilule.com:5580/api/TourData?pageSize=20&pageIndex="+index+"&lat=116.342894&lng=40.046066&parentId="+parentId+"&order=6";
        return path_firstpage;
    }
   // String path_firstpage="http://webapi.yilule.com:5580/api/TourData?pageSize=20&pageIndex=1&lat=116.342894&lng=40.046066&parentId=59&order=6";
    public static String contentInfo(String id){
        String path_content_info="http://webapi.yilule.com:5580/api/TourMap?dataid="+id;
        return path_content_info;
    }
    //查看评论的地址
    public static String Comment_path(int id){
        String comment_path="http://webapi.yilule.com:5580/api/Comments?pid="+id+"&pageSize=20&pageIndex=1";
        return  comment_path;
    }

}
