package com.work.sign.dao;

import com.work.sign.model.SignCredentials;

public interface SignDao {
    void saveUser(SignCredentials cred);
    boolean isExistUsername(String name);
}