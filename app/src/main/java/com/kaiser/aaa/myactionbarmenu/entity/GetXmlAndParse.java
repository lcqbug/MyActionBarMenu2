package com.kaiser.aaa.myactionbarmenu.entity;

import android.os.Handler;
import android.os.Message;
import android.util.Xml;

import com.kaiser.aaa.myactionbarmenu.utils.PathHelper;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaa on 15-3-30.
 */
public class GetXmlAndParse {
    //地址，暂时写死
    private String url= PathHelper.contentInfo(9875+"");
    public static final int PARSESUCCWSS=0x2001;
    private Handler handler;
    public GetXmlAndParse(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler=handler;
    }
    /**
     * 获取网络上的XML
     */
    public void getXml(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    HttpURLConnection conn=(HttpURLConnection)new
                            URL(url).openConnection();
                    conn.setConnectTimeout(5000);//设置连接超时
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode()==200) {
                        InputStream inputStream=conn.getInputStream();
                        List<Content_Info> listNews=pullXml(inputStream);
                        if (listNews.size()>0) {//如果解析结果不为空则将解析出的数据发送给UI线程
                            Message msg=new Message();
                            msg.obj=listNews;
                            msg.what=PARSESUCCWSS;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }//和服务器建立连接
            }
        }).start();
    }
    /**
     * 解析Xml,并将其封装成
     * @param inputStream
     */
    protected List<Content_Info> pullXml(InputStream inputStream) {
        List<Content_Info> listNews=new ArrayList<Content_Info>();
        try {
            XmlPullParser pullParser= Xml.newPullParser();
            Content_Info news=null;
            pullParser.setInput(inputStream, "utf-8");
            int eventCode=pullParser.getEventType();
            while (eventCode!=XmlPullParser.END_DOCUMENT) {
                String targetName=pullParser.getName();
                switch (eventCode) {
                    case XmlPullParser.START_TAG:
                        if ("data".equals(targetName)) {//处理news的开始节点
                            news =new Content_Info();
                            news.setId(new Integer(pullParser.getAttributeValue(0)));
                            news.setName(new String(pullParser.getAttributeValue(1)));
                            news.setIntroduction(new String(pullParser.getAttributeValue(2)));
                            }
// else if ("title".equals(targetName)) {
//                  //          news.setTitle(pullParser.nextText());
//                        }else if ("publishDate".equals(targetName)) {
//                 //           news.setPublishDate(new Date(pullParser.nextText()));
//                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("data".equals(targetName)) {//处理news的结束节点
                            listNews.add(news);
                        }
                        break;

                }
                eventCode=pullParser.next();//解析下一个节点（开始节点，结束节点）
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listNews;
    }
}
