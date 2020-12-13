package org.tydd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author minkun
 * @Project spring-boot-ms
 * @Package org.tydd.config
 * @Description
 * @Date 2020/11/27
 */
@Data
@RefreshScope
@Component
public class ScheduleConfig {

    @Value("${crawler.switch}")
    private String crawlerSwitch;

}
