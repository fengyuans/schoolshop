package com.my.schoolshop.web.frontend;

import com.my.schoolshop.model.HeadLine;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.HeadLineService;
import com.my.schoolshop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    @GetMapping("/listmainpageinfo")
    @ResponseBody
    public Map<String,Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategories = new ArrayList<>();
        try {
            shopCategories = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategories);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<>();
        try {
            //获取状态为1:可用头条
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLine);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }




}
