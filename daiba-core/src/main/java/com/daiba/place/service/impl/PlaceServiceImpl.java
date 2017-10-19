package com.daiba.place.service.impl;

import com.daiba.place.dao.PlaceDao;
import com.daiba.place.model.Place;
import com.daiba.place.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dolphinzhou on 2016/10/23.
 */
@Service("placeService")
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceDao placeDao;

    @Override
    public String getPlace3NameById(String code) {
        String companyCode = code;
        String addCode = code.substring(0, 4) + "00";
        String campusCode = code.substring(0, 2) + "0000";
        return placeDao.select3NameByCode(companyCode, addCode, campusCode).toString();
    }

    @Override
    //遍历出一个三级索引
    public Map<String, Object> showLinkedList(int userId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();//反馈信息map
        List<Map<String, Object>> lsMap = new ArrayList<Map<String, Object>>();//三级关联列表
        List<Place> campusLs = placeDao.getPlacesByParentId("1001000000");//获得校区
        if (null != campusLs && campusLs.size() > 0) {
            for (Place place : campusLs) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("text", place.getName());
                map.put("value", place.getCode());
                List<Place> locations = placeDao.getPlacesByParentId(place.getCode());//获得地点
                List<Map<String, Object>> newLocations = new ArrayList<>();
                if (null != locations && locations.size() > 0) {
                    for (Place pp : locations) {
                        Map map1 = new HashMap();//校区地址map
                        map1.put("text", pp.getName());
                        map1.put("value", pp.getCode());
                        List<Place> kds = placeDao.getPlacesByParentId(pp.getCode() + "");//获得相关快递点
                        if (null != kds && kds.size() > 0) {
                            List<Map<String, Object>> newKds = new ArrayList<>();
                            for (Place p : kds) {
                                Map map2 = new HashMap();//快递点map
                                map2.put("text", p.getName());
                                map2.put("value", p.getCode());
                                newKds.add(map2);
                            }
                            map1.put("children", newKds);
                        }
                        newLocations.add(map1);
                    }
                    map.put("children", newLocations);
                }
                lsMap.add(map);
            }
            resultMap.put("success", true);
            resultMap.put("place", lsMap);
            return resultMap;
        }
        resultMap.put("success", false);
        resultMap.put("message", "服务器数据异常,请重试!");
        return resultMap;
    }

}
