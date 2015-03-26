package com.kaiser.aaa.myactionbarmenu.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaa on 15-3-25.
 */
public class ContentBean {
    private String Title;
    private String NewPath;

    public  void getPareseJson(JSONObject jsonObject){
        try {
            this.Title=jsonObject.getString("Title");
            this.NewPath=jsonObject.getString("NewPath");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ContentBean() {
    }

    public ContentBean(String title, String newPath) {
        Title = title;
        NewPath = newPath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNewPath() {
        return NewPath;
    }

    public void setNewPath(String newPath) {
        NewPath = newPath;
    }
}
