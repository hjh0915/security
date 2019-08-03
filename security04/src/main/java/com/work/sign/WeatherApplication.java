package com.work.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Bean
    public Realm realm() {
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("lonestart", "vespa");

        realm.addRole("schwartz");
        realm.addRole("goodguy");
        
        return realm;
    }

}
