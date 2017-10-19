package com.daiba.utils;

import com.daiba.weixin.WX;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/10.
 */
public class GetMaterialList {


    public static final String WX_MATERIAL_URI = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    public static String getMaterialList(String type, int offset, int count){

        String token = WX.getInstance().getAccess_token();
        String url = WX_MATERIAL_URI.replace("ACCESS_TOKEN", token);
        JSONObject json = new JSONObject();

        try {
            json.put("type", type);
            json.put("offset", offset);
            json.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String result = HTTPRequest.ajax(url, json.toString(), HTTPRequest.POST);
        return result;
    }

    public static String getFirstMaterialItems(String type, int offset, int count){
        String result = getMaterialList(type, offset, count);
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items = null;
        try {
            items = json.getJSONArray("item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return items.getJSONObject(0).getString("media_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
