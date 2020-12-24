package org.tydd.webmagic.lianjia.dto;

import lombok.Data;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.dto
 * @Description
 * @Date 2020/12/14
 */
@Data
public class AverageDto {

    private Double average;

    private Integer totalCount;

    private Double averageUnitPrice;

    private Double averageTotalPrice;

    private String community;
}
