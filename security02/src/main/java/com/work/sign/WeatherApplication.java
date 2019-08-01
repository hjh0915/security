package com.work.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Bean
    public Realm realm() {
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("lonestarr = vespa, goodguy, schwartz");

        realm.setRoleDefinitions("schwartz = lightsaber:*");
        realm.setCachingEnabled(true);
        return realm;
    }

}
