package org.tydd.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tydd.schedule.entity.ScheduleLog;
import org.tydd.schedule.enums.ScheduleStatusEnum;
import org.tydd.schedule.enums.ScheduleTypeEnum;
import org.tydd.schedule.mapper.ScheduleLogMapper;
import org.tydd.schedule.service.IScheduleLogService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule.service.impl
 * @Description
 * @Date 2020/12/1
 */
@Service("scheduleLogServiceImpl")
public class ScheduleLogServiceImpl implements IScheduleLogService {

    @Resource
    private ScheduleLogMapper scheduleLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleLog saveScheduleLog(ScheduleTypeEnum scheduleType, LocalDateTime scheduleStartTime) {
        ScheduleLog scheduleLog = new ScheduleLog();
        scheduleLog.setScheduleType(scheduleType.getCode());
        scheduleLog.setScheduleStatus(ScheduleStatusEnum.EXECUTE.getCode());
        scheduleLog.setScheduleStartTime(scheduleStartTime);
        scheduleLogMapper.insert(scheduleLog);
        return scheduleLog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateScheduleLog(ScheduleStatusEnum statusEnum, Long id, LocalDateTime scheduleEndTime) {
        ScheduleLog scheduleLog = scheduleLogMapper.selectById(id);
        scheduleLog.setScheduleStatus(statusEnum.getCode());
        scheduleLog.setScheduleEndTime(scheduleEndTime);
        scheduleLogMapper.updateById(scheduleLog);
    }

    @Override
    public List<ScheduleLog> getScheduleListPage(String scheduleType, Long startPage, Long limit) {
        QueryWrapper<ScheduleLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(scheduleType)) {
            queryWrapper.eq("schedule_type", scheduleType);
        }
        IPage<ScheduleLog> page = new Page<>();
        page.setPages(startPage == null || startPage < 0 ? 0L : startPage);
        page.setCurrent(limit == null || limit <= 0 ? 0L : limit);
        IPage<ScheduleLog> pageResult = scheduleLogMapper.selectPage(page, queryWrapper);
        return pageResult.getRecords();
    }
}
