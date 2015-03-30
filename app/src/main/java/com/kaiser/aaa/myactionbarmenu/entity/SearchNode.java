package com.kaiser.aaa.myactionbarmenu.entity;

/**
 * Created by songsong-PC on 2015/3/30.
 */

public class SearchNode {
    private int Id;
    private String Name;
    private String NamePath;
    private int ScaleType;
    private String ParentName;
    private int Hot;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNamePath() {
        return NamePath;
    }

    public void setNamePath(String namePath) {
        NamePath = namePath;
    }

    public int getScaleType() {
        return ScaleType;
    }

    public void setScaleType(int scaleType) {
        ScaleType = scaleType;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public int getHot() {
        return Hot;
    }

    public void setHot(int hot) {
        Hot = hot;
    }
}
