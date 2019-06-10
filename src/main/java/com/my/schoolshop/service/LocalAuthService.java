package com.my.schoolshop.service;

import com.my.schoolshop.exceptions.LocalAuthExecution;
import com.my.schoolshop.model.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    LocalAuth getLocalAuthByUserId(long userId);

    LocalAuthExecution register(LocalAuth localAuth,CommonsMultipartFile profileImg) throws RuntimeException;

    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException;

    LocalAuthExecution modifyLocalAuth(Long userId, String userName,String password, String newPassword);
}
