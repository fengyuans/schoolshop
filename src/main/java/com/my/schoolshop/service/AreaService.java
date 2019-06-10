package com.my.schoolshop.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.schoolshop.cache.JedisUtil;
import com.my.schoolshop.dao.AreaDao;
import com.my.schoolshop.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    private static String AREALISTKEY = "arealist";

    @Transactional
    public List<Area> getAreaList() throws JsonParseException,JsonMappingException,IOException{
        String  key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper objectMapper = new ObjectMapper();

        if(!jedisKeys.exists(key)){
            areaList = areaDao.queryArea();
            String jsonString = objectMapper.writeValueAsString(areaList);
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
            areaList = objectMapper.readValue(jsonString,javaType);
        }
        return areaList;
    }

}
