package com.my.schoolshop.web.shopadmin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.model.PersonInfo;
import com.my.schoolshop.model.Shop;
import com.my.schoolshop.service.impl.ShopServiceImpl;
import com.my.schoolshop.util.FileUtil;
import com.my.schoolshop.util.HttpServletRequestUtil;
import com.thoughtworks.xstream.io.path.Path;
import org.junit.internal.runners.model.EachTestNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManageController {

    @Autowired
    private ShopServiceImpl shopService;


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
            File shopImgFile = new File(PathUtil.getImgBasePath() + FileUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            }catch (IOException e){
                e.
            }
            inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            ShopExecution se = shopService.addShop(shop,shopImg);
        }else {
            modelMap.put("sucess",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }




        return null;
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
