package com.learning.smartHouse.config;

import com.google.common.cache.CacheBuilder;
import com.learning.smartHouse.model.ButtonType;
import com.learning.smartHouse.repository.ButtonTypeRepository;
import com.learning.smartHouse.service.DatabaseActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableScheduling
public class AppConfig {
    @Autowired
    private ButtonTypeRepository buttonTypeRepository;
    @Autowired
    private DatabaseActiveService databaseActiveService;

    @Value("#{systemProperties['cache.ttl'] ?: 30}")
    private int CACHE_TTL;

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {

            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name,
                        CacheBuilder.newBuilder().expireAfterWrite(CACHE_TTL, TimeUnit.MINUTES).build().asMap(), true);
            }
        };
        return cacheManager;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ButtonType buttonType = new ButtonType();
            buttonType.setName("set");
            buttonType.setTypeId(1);
            buttonTypeRepository.saveAndFlush(buttonType);
            buttonType = new ButtonType();
            buttonType.setName("plus");
            buttonType.setTypeId(2);
            buttonTypeRepository.saveAndFlush(buttonType);
            buttonType = new ButtonType();
            buttonType.setName("minus");
            buttonType.setTypeId(3);
            buttonTypeRepository.saveAndFlush(buttonType);
        };
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduleDatabaseStatus() {
        try {
            if (buttonTypeRepository.checkLiveStatus() == 1) {
                databaseActiveService.getActive().set(true);
            }
        } catch (Exception e){
            databaseActiveService.getActive().set(false);
        }

    }
}