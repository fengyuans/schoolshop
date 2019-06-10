package com.my.schoolshop.service.impl;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.schoolshop.cache.JedisUtil;
import com.my.schoolshop.dao.ShopCategoryDao;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.ShopCategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao dao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    private static String SCLISTKEY = "shopcategorylist";
    private Logger logger = (Logger) LoggerFactory.getLogger(ShopCategoryServiceImpl.class);


    @Override
    @Transactional
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryConditon) throws IOException {
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if(shopCategoryConditon != null){
            key = key + "_allfirstlevel";
        }else if(shopCategoryConditon.getParent() != null && shopCategoryConditon != null && shopCategoryConditon.getParent().getShopCategoryId() != null){
            key = key + "_parent"+shopCategoryConditon.getParent().getShopCategoryId();
        }else {
            key = key + "_allsecondlevel";
        }


        if(!jedisKeys.exists(key)){
            shopCategoryList = dao.queryShopCategory(shopCategoryConditon);
            String jsonString = objectMapper.writeValueAsString(shopCategoryList);
            jedisStrings.set(key,jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class,ShopCategory.class);
            shopCategoryList = objectMapper.readValue(jsonString,javaType);
        }
        return shopCategoryList;
    }

}
