package com.kaiser.aaa.myactionbarmenu.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserJSONUtils {

	/** 内容详情页面的json */
    public static List<JSONObject> parserContentJson(String jsonstr){
        try {
            JSONObject jsonObject=new JSONObject(jsonstr);
            JSONArray jsonArray_photoList = jsonObject.getJSONArray("PhotoList");
            List<JSONObject> list=new ArrayList<>();
            for (int i = 0; i <jsonArray_photoList.length() ; i++) {
                JSONObject jsonObject_PhotoList = jsonArray_photoList.getJSONObject(i);
                list.add(jsonObject_PhotoList);
            }
            return  list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
         return null;
    }
    //第一个fragment的json
    public static List<JSONObject> parseSecondFragmentJson(String jsonStr){
        try {
            JSONObject jsonObject=new JSONObject(jsonStr);
            List<JSONObject> list_Second=new ArrayList<>();
            JSONObject jsonObject_Data = jsonObject.getJSONObject("Data");
            JSONArray JsonArray_list = jsonObject_Data.getJSONArray("List");
            for (int i = 0; i <JsonArray_list.length() ; i++) {
                JSONObject jsonObject_list = JsonArray_list.getJSONObject(i);
                list_Second.add(jsonObject_list);
            }
            return list_Second;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

	
}
