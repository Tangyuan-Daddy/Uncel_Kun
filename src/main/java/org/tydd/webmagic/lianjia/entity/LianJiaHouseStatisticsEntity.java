package org.tydd.webmagic.lianjia.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.entity
 * @Description 链家数据区域统计
 * @Date 2020/12/9
 */
@Data
@TableName(value = "t_lianjia_house_statistics")
public class LianJiaHouseStatisticsEntity extends LianJiaStatisticsEntity {

    private Long id;

    /** 行政区 */
    private String district;

    /** 小区 */
    private String community;

    /** 统计批次时间 */
    private String batchDate;

    /** 统计类型 */
    private String statisticsType;

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
