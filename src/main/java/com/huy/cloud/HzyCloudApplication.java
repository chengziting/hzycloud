package com.huy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;

@SpringBootApplication
public class HzyCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(HzyCloudApplication.class, args);
	}

	private static ApplicationHome home = new ApplicationHome(HzyCloudApplication.class);

	public static File getHomePath(){
		return home.getDir();
	}

}
