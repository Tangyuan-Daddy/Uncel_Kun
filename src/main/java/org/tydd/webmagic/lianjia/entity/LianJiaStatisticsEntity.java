package org.tydd.webmagic.lianjia.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.entity
 * @Description
 * @Date 2020/12/9
 */
@Data
@ToString
public class LianJiaStatisticsEntity {

    private Double averageTotalPrice;

    private Double averageUnitPrice;

    private Double averageArea;

    private String month;
}
