package com.work.sign.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.work.sign.dao.*;
import com.work.sign.model.*;

@Service
public class SignServiceImpl implements SignService {
    @Autowired
    private SignDao signDao;

    @Override 
    public void saveUser(SignCredentials cred) {
        signDao.saveUser(cred);
    }
}