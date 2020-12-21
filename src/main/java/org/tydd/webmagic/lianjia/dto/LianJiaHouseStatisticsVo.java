package org.tydd.webmagic.lianjia.dto;

import lombok.Data;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.dto
 * @Description
 * @Date 2020/12/13
 */
@Data
public class LianJiaHouseStatisticsVo {

    /** 行政区 */
    private String district;

    /** 社区 */
    private String community;

    /** 挂牌总数 */
    private Integer totalCount;

    /** 平均单价 */
    private Double averageUnitPrice;

    /** 平均总价 */
    private Double averageTotalPrice;

    /** 中位数单价 */
    private Double medianUnitPrice;

    /** 中位数总价 */
    private Double medianTotalPrice;
}