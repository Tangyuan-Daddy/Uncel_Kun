package org.tydd.webmagic.lianjia.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.dto
 * @Description 查询DTO
 * @Date 2020/12/13
 */
@Data
public class LianJiaHouseQueryDto {

    /** 行政区 */
    private String district;

    /** 小区 */
    private String communityName;

    /** 统计时间 */
    private String batchDate;
}
