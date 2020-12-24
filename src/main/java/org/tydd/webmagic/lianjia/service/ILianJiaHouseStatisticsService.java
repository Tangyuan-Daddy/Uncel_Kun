package org.tydd.webmagic.lianjia.service;

import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseStatisticsVo;

import java.util.List;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.webmagic.lianjia.service
 * @Description 链家二手房信息统计Service接口
 * @Date 2020/12/23
 */
public interface ILianJiaHouseStatisticsService {

    /**
     * 获取最后统计时间
     * @return
     */
    String getLastStatisticalTime();

    /**
     * 按照行政区维度查询统计数据
     * @param queryDto
     * @return
     */
    List<LianJiaHouseStatisticsVo> getHouseStatisticsListByDistrict(LianJiaHouseQueryDto queryDto);

    /**
     * 按照社区维度查询统计数据
     * @param queryDto
     * @return
     */
    List<LianJiaHouseStatisticsVo> getHouseStatisticsByCommunity(LianJiaHouseQueryDto queryDto);
}
