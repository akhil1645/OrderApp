package com.adobe.proj.cfg;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;


@Configuration
//@EnableCaching// not required here since we already declared at main application file
public class RedisCustomConfig {
	
	// compulsary
	@Bean 
	public RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(60))
				.disableCachingNullValues()
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}
	// redis to be used as cache manager. Cofiguration is default Cache Configuration
	//entryTtl->time to leave. 60 minutes Cache data to be present.
	// disableCachingNullValues()-> don't cache null values
	//serialisation-> It is process of writing data to a string
	// In java serialistation is a mechanism in which from the jvm you want to write to other streams outside the jvm any data needed to be send
	
	//.serializeValuesWith-> this looks for what you are writing into the cache and if it implements serialisable. if it doesn;t implement serialiszble it won't allow/
	//GenericJackson2JsonRedisSerializer()->  now what we are teeling is the serialised data in the redis we will have to get it into json. Jackson library used to get it into json. so json serialiser.
	// because in the binary format it will be stored in the redis. That we will have to conver it into json.
	
	
	
	

	
	
	// optional we can do custom for each and every cache if we want to do different configuration
	
	
	@Bean 
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
		return (builder) -> builder
				.withCacheConfiguration("productCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
				.withCacheConfiguration("customerCache",
						RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
	}

}
