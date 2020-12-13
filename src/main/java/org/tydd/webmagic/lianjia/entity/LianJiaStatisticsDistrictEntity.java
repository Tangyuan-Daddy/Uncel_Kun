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
@TableName(value = "t_lianjia_statistics_district")
public class LianJiaStatisticsDistrictEntity extends LianJiaStatisticsEntity {

    private Long id;

    private String district;

    private String community;

}
