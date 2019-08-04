package com.work.sign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(WeatherApplicationTests.class);

    @Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testHash() {
		String username = "admin";
		String hashAlgorithmName = "md5";

        String credentials = "123456";
		String password_salt = "abc";
        int hashIterations = 2;

		SimpleHash sh = new SimpleHash(hashAlgorithmName, credentials, password_salt, hashIterations);
        
		logger.info("hello world");
		logger.info(sh.toString());

		jdbcTemplate.update("insert into users (username, password, password_salt) values (?, ?, ?)" ,
			new Object[] {username, sh.toString(), password_salt},
			new int [] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
	}

}
