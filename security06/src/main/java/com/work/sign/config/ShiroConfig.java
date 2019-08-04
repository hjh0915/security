package com.work.sign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import javax.sql.DataSource;

@Configuration
public class ShiroConfig {
    // 由spring jdbc提供DataSource Bean
	@Autowired
	DataSource dataSource;
	
	@Bean
    public Realm realm() {
        final String MY_AUTHENTICATION_QUERY = "select password from users where username = ?";
		final String MY_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";
        final String MY_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";

        JdbcRealm realm = new JdbcRealm();
		realm.setDataSource(dataSource);

        realm.setAuthenticationQuery(MY_AUTHENTICATION_QUERY);
        realm.setUserRolesQuery(MY_USER_ROLES_QUERY);
        realm.setPermissionsQuery(MY_PERMISSIONS_QUERY);

		// 若不调用，则只有user roles，没有permission
		realm.setPermissionsLookupEnabled(true);
		return realm;
	}

}
