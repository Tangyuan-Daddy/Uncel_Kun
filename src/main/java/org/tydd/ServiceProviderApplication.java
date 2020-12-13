package org.tydd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author minkun
 * @Project spring-boot-ms
 * @Package org.tydd
 * @Description
 * @Date 2020/11/27
 */
@EnableAsync(proxyTargetClass=true)
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.tydd")
public class ServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }

}
