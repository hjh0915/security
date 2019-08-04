package com.work.sign.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.work.sign.model.*;

@Repository
public class SignDaoImpl implements SignDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
      
    @Override 
    public void saveUser(SignCredentials cred) {
        String hashAlgorithmName = "md5";
        String password_salt = "abc";
        int hashIterations = 2;

        SimpleHash sh = new SimpleHash(hashAlgorithmName, cred.getPassword(), password_salt, hashIterations);
        
        jdbcTemplate.update("insert into users (username, password, password_salt) values (?, ?, ?)" ,
			new Object[] {cred.getUsername(), sh.toString(), password_salt},
            new int [] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
    }

    @Override
    public boolean CheckName(String name) {
        
    }
}