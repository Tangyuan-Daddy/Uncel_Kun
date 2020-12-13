package org.tydd.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tydd.common.ResponseVo;
import org.tydd.schedule.service.IScheduleLogService;

import javax.annotation.Resource;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule.controller
 * @Description
 * @Date 2020/12/1
 */
@Slf4j
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleLogController {

    @Resource
    @Qualifier("scheduleLogServiceImpl")
    private IScheduleLogService scheduleLogService;

    @GetMapping(value = "/list")
    public ResponseVo getScheduleLogList(String scheduleType, Long start, Long limit) {
        return ResponseVo.getSuccess(scheduleLogService.getScheduleListPage(scheduleType, start, limit));
    }
}
