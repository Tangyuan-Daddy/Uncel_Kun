package org.tydd.schedule.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tydd.schedule.entity.ScheduleLog;
import org.tydd.schedule.enums.ScheduleStatusEnum;
import org.tydd.schedule.enums.ScheduleTypeEnum;
import org.tydd.schedule.service.IScheduleLogService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule
 * @Description 调度切片类
 * @Date 2020/12/1
 */
@Slf4j
@Aspect
@Component
public class ScheduleAspect {

    @Autowired
    @Qualifier("scheduleLogServiceImpl")
    private IScheduleLogService scheduleLogService;

    @Around("execution(* org.tydd.schedule.WebMagicSchedule.*(..))")
    public Object handleScheduleLogMethod(ProceedingJoinPoint pjp) {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("ScheduleAspect start, start time = {}.", startTime);
        Object object = null;
        ScheduleLog scheduleLog = null;
        ScheduleStatusEnum statusEnum = null;
        try {
            scheduleLog = scheduleLogService.saveScheduleLog(ScheduleTypeEnum.WEB_MAGIC, startTime);
            // 调用被拦截的方法
            object = pjp.proceed();
            statusEnum = ScheduleStatusEnum.FINISH;
        } catch (Throwable throwable) {
            statusEnum = ScheduleStatusEnum.ERROR;
            throwable.printStackTrace();
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            scheduleLogService.updateScheduleLog(statusEnum, scheduleLog.getId(), endTime);
            log.info("ScheduleAspect end, end time = {}, execution time = {} MS",
                    endTime,
                    (endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli() - startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        }
        return object;
    }
}
