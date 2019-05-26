package com.my.schoolshop.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @RequestMapping("/shopedit")
    public String shopOperation(){
        return "shop/shopedit";
    }


    @RequestMapping("/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanage";
    }

    @RequestMapping("/registershop")
    public String shopRegisterShop(){
        return "shop/register";
    }


    @RequestMapping("/getshoplist")
    public String shopList(){ return "shop/shoplist";}

    @RequestMapping("/productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanage";
    }




}
