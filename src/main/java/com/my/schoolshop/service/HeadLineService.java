package com.my.schoolshop.service;

import com.my.schoolshop.model.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {

    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
