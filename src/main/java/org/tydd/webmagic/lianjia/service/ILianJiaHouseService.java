package org.tydd.webmagic.lianjia.service;

import org.tydd.webmagic.lianjia.dto.AverageDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseStatisticsVo;

import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.service
 * @Description 链家房产Service实现
 * @Date 2020/12/13
 */
public interface ILianJiaHouseService {

    /**
     * 生成统计数据
     * @param queryDto
     */
    void buildStatistics(LianJiaHouseQueryDto queryDto);

    /**
     * 获取统计数据
     * @param queryDto
     * @return
     */
    List<LianJiaHouseStatisticsVo> getHouseStatisticsList(LianJiaHouseQueryDto queryDto);

    /**
     * 实时计算后获取统计数据
     * @param queryDto
     * @return
     */
    List<LianJiaHouseStatisticsVo> getHouseStatisticsFromFact(LianJiaHouseQueryDto queryDto);

    /**
     * 获取平均单价
     * @param queryDto
     * @return
     */
    AverageDto getAverageUnitPrice(LianJiaHouseQueryDto queryDto);

    /**
     * 获取平均总价
     * @param queryDto
     * @return
     */
    AverageDto getAverageTotalPrice(LianJiaHouseQueryDto queryDto);

    /**
     * 获取单价中位数
     * @param queryDto
     * @return
     */
    Double getMedianByUnitPrice(LianJiaHouseQueryDto queryDto);

    /**
     * 获取总价中位数
     * @param queryDto
     * @return
     */
    Double getMedianByTotalPrice(LianJiaHouseQueryDto queryDto);

}
