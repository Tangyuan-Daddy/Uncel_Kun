package org.tydd.webmagic.lianjia;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tydd.common.PageInfoDto;
import org.tydd.config.WebMagicConfig;
import org.tydd.utils.CookieUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic
 * @Description 链家二手房信息爬虫入口
 * @Date 2020/12/2
 */
@Data
@Slf4j
@Component
public class LianjiaHousePageProcessor implements PageProcessor {

    @Resource
    private LianJiaPipeline lianJiaPipeline;

    @Resource
    private WebMagicConfig webMagicConfig;

    private static Site site = Site.me().setRetryTimes(800).setSleepTime(800).setCharset("UTF-8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
            .addHeader("Host", "nj.lianjia.com")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9");

    @Override
    public void process(Page page) {
        if (page.getUrl().toString().endsWith("pg")) {
            String pageInfoStr = page.getHtml().xpath("//div[@class='page-box fr']/div[@class='page-box house-lst-page-box']/@page-data").toString();
            PageInfoDto pageInfo = JSONObject.parseObject(pageInfoStr, PageInfoDto.class);
            log.info("pageInfo = " + pageInfo);
            int totalPage = pageInfo.getTotalPage();
            if (totalPage > 1) {
                for (int i = 2; i <= totalPage; i++) {
                    page.addTargetRequest(page.getUrl().toString() + i);
                }
            }
        }
        List<String> viewLinks = page.getHtml().xpath("//div[@class='title']/a/@href").all();
        page.addTargetRequests(viewLinks);
        String communityName = page.getHtml().xpath("//div[@class='communityName']/a/text()").toString();
        if (communityName != null && !"".equals(communityName)) {
            page.putField("communityName", communityName);
            page.putField("totalPrice", page.getHtml().xpath("//div[@class='price ']/span[@class='total']/text()").toString());
            page.putField("unitPrice", page.getHtml().xpath("//div[@class='unitPrice']/span[@class='unitPriceValue']/text()").toString());
            String area = page.getHtml().xpath("//div[@class='area']/div[@class='mainInfo']/text()").toString();
            if (area != null && area.length() > 0) {
                area = area.replace("平米", "");
            }
            page.putField("area", Double.valueOf(area));
            page.putField("district", page.getHtml().xpath("//div[@class='areaName']/span[@class='info']/a/text()").toString());
            page.putField("roomInfo", page.getHtml().xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='mainInfo']/text()").toString());
            page.putField("title", page.getHtml().xpath("//div[@class='title-wrapper']/div[@class='content']/div[@class='title']/h1/text()").toString());
            page.putField("imageUrls", page.getHtml().xpath("//div[@class='content-wrapper housePic']//img/@src").all());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    private void createSpider(String url) {
        Spider spider = Spider.create(new LianjiaHousePageProcessor())
                .addPipeline(lianJiaPipeline)
                .addUrl(url)
                .thread(3);
        spider.run();
    }

    public void execute() {
        log.info("LianjiaHousePageProcessor execute start.");
        LianjiaHousePageProcessor.site = CookieUtil.addCookieForSite(site, webMagicConfig.getLianjiaCookies());
        String baseUrl = "https://nj.lianjia.com/ershoufang/";
        if (!StringUtils.isEmpty(webMagicConfig.getLianjiaSecondPages())) {
            String[] secondPageArray = webMagicConfig.getLianjiaSecondPages().split(",");
            HashSet<String> secondPageSet = new HashSet<>();
            Arrays.stream(secondPageArray).forEach(l -> {secondPageSet.add(l);});
            secondPageSet.stream().forEach(s -> {
                createSpider(baseUrl + s + "/pg");
            });
        }
        log.info("LianjiaHousePageProcessor execute end.");
    }

    @Async
    public void executeAsync() {
        this.execute();
    }

}
