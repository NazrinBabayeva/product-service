package com.example.ecommerce_v2.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class CacheCleanupJob {
    @CacheEvict(value = "products", allEntries = true)
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearCacheAtMidnight() {
        log.info("Product cache cleared at midnight");
    }
}
