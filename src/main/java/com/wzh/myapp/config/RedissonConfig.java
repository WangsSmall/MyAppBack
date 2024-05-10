package com.wzh.myapp.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 75654
 * @date 2023/3/26 16:16
 *
 * Redisson 配置类
 *      https://github.com/redisson/redisson#quick-start
 */
@Configuration
@ConfigurationProperties("spring.redis")
@Data
public class RedissonConfig {
    /**
     * 获取配置中的参数值
     */
    private String host;
    private String port;

    @Bean
    public RedissonClient redissonClient(){
        // 1. Create config object
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        // useClusterServers() 集群模式 useSingleServer() 单机模式
        // 链式编程，设置 redis 地址，库选择，密码（有设置，无不做设置）
        config.useSingleServer().setAddress(redisAddress)
                .setPassword("123456")
                .setDatabase(3);

        // 2. Create Redisson instance
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
