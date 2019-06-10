package com.my.schoolshop.service.impl;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.schoolshop.cache.JedisUtil;
import com.my.schoolshop.dao.HeadLineDao;
import com.my.schoolshop.model.HeadLine;
import com.my.schoolshop.service.HeadLineService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static String HLLISTKEY = "headlinelist";
    private static Logger logger = (Logger) LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        String key = HLLISTKEY;
        List<HeadLine> headLines = null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(headLineCondition != null && headLineCondition.getEnableStatus() != null){
            key = key + "-" + headLineCondition.getEnableStatus();
        }
        if(!jedisKeys.exists(key)){
            headLines = headLineDao.queryHeadLine(headLineCondition);
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(headLines);
            }catch (JsonProcessingException e){
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadlessException(e.getMessage());
            }
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,HeadLine.class);
            headLines = objectMapper.readValue(jsonString,javaType);
        }
        return headLines;
    }
}
