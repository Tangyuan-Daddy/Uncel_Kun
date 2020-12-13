package webmagic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tydd.ServiceProviderApplication;
import org.tydd.webmagic.lianjia.LianjiaHousePageProcessor;

import javax.annotation.Resource;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package webmagic
 * @Description
 * @Date 2020/12/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProviderApplication.class)
public class LianJiaTest {

    @Resource
    private LianjiaHousePageProcessor lianjiaHousePageProcessor;

    @Test
    public void testLianJiaExecute() {
        lianjiaHousePageProcessor.execute();
    }
}
