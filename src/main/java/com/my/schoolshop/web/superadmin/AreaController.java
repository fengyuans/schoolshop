package com.my.schoolshop.web.superadmin;

import com.my.schoolshop.model.Area;
import com.my.schoolshop.service.AreaService;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    @Autowired
    private AreaService service;

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea(){
        Map<String,Object> map = new HashMap<>();
        List<Area> list = new ArrayList<>();
        try {
            list = service.getAreaList();
            map.put("rows",list);
            map.put("total",list.size());

        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.toString());
        }
        return map;
    }
}
