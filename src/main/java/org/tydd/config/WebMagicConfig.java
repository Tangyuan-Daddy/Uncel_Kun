package org.tydd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.config
 * @Description 爬虫相关设置
 * @Date 2020/12/9
 */
@Data
@RefreshScope
@Component
public class WebMagicConfig {

    /** 链家二级页面名称 */
    @Value("${webmagic.lianjia.secondPages}")
    private String lianjiaSecondPages;

    /** 链家Cookies */
    @Value("${webmagic.lianjia.cookies}")
    private String lianjiaCookies;
}
