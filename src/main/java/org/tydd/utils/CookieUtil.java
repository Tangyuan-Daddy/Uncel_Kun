package org.tydd.utils;

import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Site;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.utils
 * @Description
 * @Date 2020/12/4
 */
public class CookieUtil {

    private static String SPLIT_STR = ",";

    public static Site addCookieForSite(Site site, String cookies) {
        if (!StringUtils.isEmpty(cookies)) {
            String[] cookieArray = cookies.split(SPLIT_STR);
            if (cookieArray != null && cookieArray.length > 0) {
                String key;
                String value;
                for (String cookie : cookieArray) {
                    key = cookie.split("=")[0].trim();
                    value = cookie.split("=")[1].trim();
                    site.addCookie(key, value);
                }
            }
        }
        return site;
    }
}
