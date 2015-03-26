package com.kaiser.aaa.myactionbarmenu.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaa on 15-3-26.
 */
public class SecondFragmentBean {
    private String Title;
    private String Summary;
    private String Cover;
    private String AudioPath;
    private String AudioName;
    private String EditorName;
    private String EditorPic;
    private String Id;
    private String IsTop;



    public  void parserJson(JSONObject jsonObject){
        try {
            this.Title=jsonObject.getString("Title");
            this.Summary=jsonObject.getString("Summary");
            this.Cover=jsonObject.getString("Cover");
            this.AudioPath=jsonObject.getString("AudioPath");
            this.AudioName=jsonObject.getString("AudioName");
            this.EditorName=jsonObject.getString("EditorName");
            this.EditorPic=jsonObject.getString("EditorPic");
            this.Id=jsonObject.getString("Id");
            this.IsTop=jsonObject.getString("IsTop");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public SecondFragmentBean() {
    }

    public SecondFragmentBean(String title, String summary, String cover, String audioPath, String audioName, String editorName, String editorPic, String id, String isTop) {
        Title = title;
        Summary = summary;
        Cover = cover;
        AudioPath = audioPath;
        AudioName = audioName;
        EditorName = editorName;
        EditorPic = editorPic;
        Id = id;
        IsTop = isTop;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getAudioPath() {
        return AudioPath;
    }

    public void setAudioPath(String audioPath) {
        AudioPath = audioPath;
    }

    public String getAudioName() {
        return AudioName;
    }

    public void setAudioName(String audioName) {
        AudioName = audioName;
    }

    public String getEditorName() {
        return EditorName;
    }

    public void setEditorName(String editorName) {
        EditorName = editorName;
    }

    public String getEditorPic() {
        return EditorPic;
    }

    public void setEditorPic(String editorPic) {
        EditorPic = editorPic;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsTop() {
        return IsTop;
    }

    public void setIsTop(String isTop) {
        IsTop = isTop;
    }
}
