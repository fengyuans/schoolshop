package com.my.schoolshop.service;

import com.my.schoolshop.dao.AreaDao;
import com.my.schoolshop.model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;


    public List<Area> getAreaList(){
        return areaDao.queryArea();
    }

}
