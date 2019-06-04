package com.my.schoolshop.web.frontend;

import com.my.schoolshop.dto.ShopExecution;
import com.my.schoolshop.model.Area;
import com.my.schoolshop.model.Shop;
import com.my.schoolshop.model.ShopCategory;
import com.my.schoolshop.service.AreaService;
import com.my.schoolshop.service.ShopCategoryService;
import com.my.schoolshop.service.ShopService;
import com.my.schoolshop.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;


    @GetMapping("/listshopspageinfo")
    @ResponseBody
    private Map<String,Object> listShopsPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        long parentId = HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList = null;
        if(parentId != -1){
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                shopCategoryCondition.setParentId(parentId);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }

        modelMap.put("shopCategoryList",shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
            return  modelMap;
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


    @GetMapping("/listshops")
    @ResponseBody
    public Map<String,Object> listShops(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if((pageIndex > -1)&&(pageSize > -1)){
            long parentId = HttpServletRequestUtil.getLong(request,"parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            long areaId = HttpServletRequestUtil.getLong(request,"areaId");
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            ShopExecution se = shopService.getShopList(shopCondition,pageIndex,pageSize);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageIndex or pageSize");

        }
        return modelMap;
    }

    public Shop compactShopCondition4Search(long parentId,long shopCategoryId,long areaId,String shopName){
        Shop shopCondition = new Shop();
        if(parentId != -1){
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }

        if(shopCategoryId != -1){
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }

        if(areaId != -1){
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }

        if(shopName != null){
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }

}
