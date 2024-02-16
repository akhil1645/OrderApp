package com.adobe.proj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableScheduling // Once you enable scheduling you can specify a particular method to be called for only a given interval
public class ReorderapppracticeApplication {
	@Autowired
	CacheManager cacheManager;
	
	public static void main(String[] args) {
		SpringApplication.run(ReorderapppracticeApplication.class, args);
	}
	
	// this function was earlier defined in RestTemplateExample class
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder builder)
	{
		return builder.build();
	}
	
//	@Scheduled(fixedRate=2000) // for every 2 sec execute this function
//	@Scheduled(cron= "0 0/30 * * * *") // can also give cron job ex every half hour
	public void clearCache() { // this is better way than writing an end point using cace evict.
		System.out.println("Cache cleared !!! ");
		cacheManager.getCacheNames().forEach(name->{
			cacheManager.getCache(name).clear();
		}); ;
		//.getCacheNames() get all the caches productCaches, CustomerCache etc.
	}

}
