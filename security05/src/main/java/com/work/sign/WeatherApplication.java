package com.work.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.SimpleAccountRealm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
    }
    
    @Autowired
	DataSource dataSource;

	@Bean
    public Realm realm() {
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        realm.setPermissionsLookupEnabled(true);
		return realm;
    }

}
