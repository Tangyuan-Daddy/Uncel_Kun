package org.tydd.schedule.enums;

import lombok.Data;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule.enums
 * @Description 调度类型枚举
 * @Date 2020/12/1
 */
public enum ScheduleTypeEnum {

    WEB_MAGIC(1);

    private Integer code;

    ScheduleTypeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
