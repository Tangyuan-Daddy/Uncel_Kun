package org.tydd.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author minkun
 * @Project service-uncel
 * @Package org.tydd.schedule.entity
 * @Description 调度日志
 * @Date 2020/12/1
 */
@Data
@TableName("t_schedule_log")
public class ScheduleLog {

    private Long id;

    private Integer scheduleType;

    private LocalDateTime scheduleStartTime;

    private LocalDateTime scheduleEndTime;

    private Integer scheduleStatus;
}
