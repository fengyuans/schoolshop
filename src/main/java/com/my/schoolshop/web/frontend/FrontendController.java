package com.my.schoolshop.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping("/index")
    public String index(){return "frontend/index";}

    @RequestMapping("/shopdetail")
    public String shopDetail(){return "frontend/shopdetail";}

    @RequestMapping("/productdetail")
    public String productDetail(){return "frontend/productdetail";}

    @RequestMapping("/shoplist")
    private String showShopList(){return "frontend/shoplist";}
}
