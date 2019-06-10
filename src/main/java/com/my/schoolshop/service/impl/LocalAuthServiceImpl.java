package com.my.schoolshop.service.impl;

import com.my.schoolshop.dao.LocalAuthDao;
import com.my.schoolshop.exceptions.LocalAuthExecution;
import com.my.schoolshop.model.LocalAuth;
import com.my.schoolshop.service.LocalAuthService;
import com.my.schoolshop.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;


    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) throws RuntimeException {
        return null;
    }

    @Override
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {
        return null;
    }

    @Override
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
        return null;
    }
}
