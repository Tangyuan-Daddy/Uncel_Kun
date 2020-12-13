package org.tydd.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule
 * @Description
 * @Date 2020/12/1
 */
@Slf4j
@Component
public class WebMagicSchedule {

    //@Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")
    public void executeWebMagic() {
        log.info("execute web magic schedule.");
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
