package com.my.schoolshop.web.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.enums.ShopStateEnum;
import com.my.schoolshop.model.Area;
import com.my.schoolshop.model.PersonInfo;
import com.my.schoolshop.model.Shop;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.AreaService;
import com.my.schoolshop.service.ShopCategoryService;
import com.my.schoolshop.service.ShopService;
import com.my.schoolshop.service.impl.ShopCategoryServiceImpl;
import com.my.schoolshop.service.impl.ShopServiceImpl;
import com.my.schoolshop.util.CodeUtil;
import com.my.schoolshop.util.FileUtil;
import com.my.schoolshop.util.HttpServletRequestUtil;
import com.thoughtworks.xstream.io.path.Path;
import org.junit.internal.runners.model.EachTestNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopManageController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @GetMapping("/getshopbyid")
    @ResponseBody
    public Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1){
            Shop shop = shopService.getByShopId(shopId);
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("shop",shop);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",true);
            modelMap.put("errMsg","empty shopId");
        }

        return modelMap;
    }




    @GetMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }

        return modelMap;
    }


    @PostMapping("/registershop")
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入正确的验证码");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;

        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//获取文件上传内容
        if(multipartResolver.isMultipart(request)){//是否有上传的文件流
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //注册店铺
        if(shop != null && shopImg != null){
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setPersonInfo(owner);

            //注册店铺
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if(se.getState() == ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    //用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }



    @PostMapping("/modifyshop")
    @ResponseBody
    public Map<String, Object> modifyShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入正确的验证码");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;

        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//获取文件上传内容
        if(multipartResolver.isMultipart(request)){//是否有上传的文件流
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //修改店铺
        if(shop != null && shop.getShopImg() != null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(8L);
            shop.setOwnerId(owner.getUserId());

            //修改店铺
            ShopExecution se = null;
            try {
                if(shopImg == null){
                    se = shopService.modifyShop(shop,null,null);
                }
                se = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if(se.getState() == ShopStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID");
            return modelMap;
        }
    }









}
