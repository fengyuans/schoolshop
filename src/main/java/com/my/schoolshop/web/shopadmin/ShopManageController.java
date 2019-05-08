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
            modelMap.put("sucess",true);
        }catch (Exception e){
            modelMap.put("sucess",false);
            modelMap.put("errMsg",e.getMessage());
        }

        return modelMap;
    }


    @PostMapping("/registershop")
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
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
            modelMap.put("sucess",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //注册店铺
        if(shop != null && shopImg != null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwnerId(owner.getUserId());
            File shopImgFile = new File(FileUtil.getImgBasePath() + FileUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            //注册店铺
            ShopExecution se = shopService.addShop(shop,shopImg);
            if(se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("sucess",true);
            }else {
                modelMap.put("sucess",false);
                modelMap.put("errMsg",se.getStateInfo());
            }
            return modelMap;
        }else {
            modelMap.put("sucess",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }


    private static void inputStreamToFile(InputStream ins,File file){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer))!= -1){
                outputStream.write(buffer,0,bytesRead);
            }

        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile异常"+e.getMessage());
        }finally {
            try {
                if(outputStream != null){
                    outputStream.close();
                }

                if(ins != null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("调用inputStreamToFile异常"+e.getMessage());
            }

        }

    }
}
