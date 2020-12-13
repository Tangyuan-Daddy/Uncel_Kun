package org.tydd.schedule.enums;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.schedule.enums
 * @Description 调度状态
 * @Date 2020/12/1
 */
public enum ScheduleStatusEnum {

    EXECUTE(1),

    FINISH(2),

    ERROR(3);

    private Integer code;

    ScheduleStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
