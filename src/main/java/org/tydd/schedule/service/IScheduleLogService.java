package org.tydd.schedule.service;

import org.tydd.schedule.entity.ScheduleLog;
import org.tydd.schedule.enums.ScheduleStatusEnum;
import org.tydd.schedule.enums.ScheduleTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule.service
 * @Description
 * @Date 2020/12/1
 */
public interface IScheduleLogService {

    /**
     * 保存日志记录
     * @param scheduleType
     * @param scheduleStartTime
     */
    ScheduleLog saveScheduleLog(ScheduleTypeEnum scheduleType, LocalDateTime scheduleStartTime);

    /**
     * 更新日志记录
     * @param statusEnum
     * @param id
     */
    void updateScheduleLog(ScheduleStatusEnum statusEnum, Long id, LocalDateTime scheduleEndTime);

    List<ScheduleLog> getScheduleListPage(String scheduleType, Long start, Long limit);
}
