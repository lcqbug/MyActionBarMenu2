package com.kaiser.aaa.myactionbarmenu.entity;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "Chapter")
public class ChapterBean {
    @Id(column = "Id")
    @NoAutoIncrement
    private String id;

    @Column(column = "ScaleType")
    private String type;

    @Column(column = "Name")
    private String name;

    @Column(column = "Pic")
    private String image;

    @Column(column = "NewPic")
    private String newPic;

    @Column(column = "Foreword")
    private String foreword;

    @Override
    public String toString() {
        return "ChapterBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", newPic='" + newPic + '\'' +
                ", foreword='" + foreword + '\'' +
                '}';
    }

    public void getparseJson(JSONObject jsonObject){
        try {
            this.id=jsonObject.getString("Id");
            this.name=jsonObject.getString("Name");
            this.foreword=jsonObject.getString("Foreword");
            this.newPic=jsonObject.getString("NewPic");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewPic() {
        return newPic;
    }

    public void setNewPic(String newPic) {
        this.newPic = newPic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getForeword() {
        return foreword;
    }

    public void setForeword(String foreword) {
        this.foreword = foreword;
    }

}
