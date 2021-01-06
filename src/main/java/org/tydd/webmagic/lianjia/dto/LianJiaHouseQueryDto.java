package org.tydd.webmagic.lianjia.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.dto
 * @Description 查询DTO
 * @Date 2020/12/13
 */
@ApiModel("查询DTO")
@Data
public class LianJiaHouseQueryDto {

    /** 行政区 */
    @ApiModelProperty(value = "行政区")
    private String district;

    /** 小区 */
    @ApiModelProperty(value = "小区")
    private String community;

    /** 统计时间 */
    @ApiModelProperty(value = "统计时间")
    private String batchDate;

    /** 统计类型 */
    @ApiModelProperty(value = "统计类型")
    private String statisticsType;
}
