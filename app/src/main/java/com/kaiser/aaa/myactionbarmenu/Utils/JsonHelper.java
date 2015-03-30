package com.kaiser.aaa.myactionbarmenu.Utils;

import com.kaiser.aaa.myactionbarmenu.entity.SearchNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	// 将json字符串反序列化成字符串数组
	public static String[] jsonStringToArray(String jsonString, String key) {
		JSONArray jsonArray = null;
		String[] arrString = null;
		try {
			if (key == null) {
				jsonArray = new JSONArray(jsonString);
			} else {

				JSONObject jsonObject = new JSONObject(jsonString);
				jsonArray = jsonObject.getJSONArray(key);
			}

			arrString = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				arrString[i] = jsonArray.getString(i);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return arrString;
	}

	// 将json字符串反序列化成Map对象
	public static Map<String, Object> jsonStringToMap(String jsonString,
			String[] keyNames, String key) {
		JSONObject jsonObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (key == null) {
				jsonObject = new JSONObject(jsonString);
				for (int i = 0; i < keyNames.length; i++) {
					map.put(keyNames[i], jsonObject.get(keyNames[i]));
				}
			} else {
				jsonObject = new JSONObject(jsonString);
				JSONObject jsonObject2 = jsonObject.getJSONObject(key);
				for (int i = 0; i < keyNames.length; i++) {
					map.put(keyNames[i], jsonObject2.get(keyNames[i]));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}

	// json字符串反序列化成List对象
	public static List<Map<String, Object>> jsonStringToList(String jsonString,
			String[] keyNames, String key) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		try {
			if (key == null) {
				jsonArray = new JSONArray(jsonString);

			} else {
				jsonObject = new JSONObject(jsonString);
				jsonArray = jsonObject.getJSONArray(key);
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();

				for (int j = 0; j < keyNames.length; j++) {
					map.put(keyNames[j], jsonObject2.get(keyNames[j]));
				}
				list.add(map);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	// json字符串转成JSONObject对象
	public static JSONObject jsonStringToJSONObject(String jsonString,
			String key) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
			jsonObject = jsonObject.getJSONObject(key);
			return jsonObject;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// JSONObject对象转成List对象
	public static List<Map<String, Object>> jsonObjectToList(
			JSONObject jsonObject, String[] keyNames, String key) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = null;
		try {
			if (key != null) {
				jsonArray = jsonObject.getJSONArray(key);
			} else {
				return null;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();

				for (int j = 0; j < keyNames.length; j++) {
					map.put(keyNames[j], jsonObject2.get(keyNames[j]));
				}
				list.add(map);
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

    //JSONArray对象转换成List对象
    public static List<SearchNode> jsonArrayToList(String jsonString){
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            List<SearchNode> list = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SearchNode node = new SearchNode();
                node.setHot(jsonObject.getInt("Hot"));
                node.setId(jsonObject.getInt("Id"));
                node.setName(jsonObject.getString("Name"));
                node.setNamePath(jsonObject.getString("NamePath"));
                node.setParentName(jsonObject.getString("ParentName"));
                node.setScaleType(jsonObject.getInt("ScaleType"));
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
