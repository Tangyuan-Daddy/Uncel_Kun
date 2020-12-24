package org.tydd.webmagic.lianjia.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tydd.common.LianJiaCommon;
import org.tydd.webmagic.lianjia.dto.AverageDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseStatisticsVo;
import org.tydd.webmagic.lianjia.entity.LianJiaHouseStatisticsEntity;
import org.tydd.webmagic.lianjia.mapper.LianJiaHouseMapper;
import org.tydd.webmagic.lianjia.mapper.LianJiaHouseStatisticsMapper;
import org.tydd.webmagic.lianjia.service.ILianJiaHouseService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.service.impl
 * @Description 链家房产Service接口
 * @Date 2020/12/13
 */
@Service("lianJiaHouseServiceImpl")
public class LianJiaHouseServiceImpl implements ILianJiaHouseService {

    @Resource
    private LianJiaHouseMapper lianJiaHouseMapper;

    @Resource
    private LianJiaHouseStatisticsMapper lianJiaHouseStatisticsMapper;

    /**
     * 生成统计数据
     * @param queryDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buildStatistics(LianJiaHouseQueryDto queryDto) {
        List<LianJiaHouseStatisticsVo> houseStatisticsVoList = getHouseStatisticsFromFact(queryDto);
        lianJiaHouseStatisticsMapper.deleteByBatchDate(queryDto.getBatchDate());
        if (!CollectionUtils.isEmpty(houseStatisticsVoList)) {
            houseStatisticsVoList.stream().forEach( houseStatistics -> {
                LianJiaHouseStatisticsEntity lianJiaHouseStatistics = new LianJiaHouseStatisticsEntity();
                BeanUtils.copyProperties(houseStatistics, lianJiaHouseStatistics);
                lianJiaHouseStatistics.setBatchDate(queryDto.getBatchDate());
                lianJiaHouseStatistics.setStatisticsType(LianJiaCommon.STATISTICS_TYPE_DISTRICT);
                lianJiaHouseStatisticsMapper.insert(lianJiaHouseStatistics);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buildStatisticsByCommunity(LianJiaHouseQueryDto queryDto) {
        List<String> districtList = lianJiaHouseMapper.queryDistrictList();
        if (!CollectionUtils.isEmpty(districtList)) {
            List<LianJiaHouseStatisticsEntity> statisticsList = new ArrayList<>();
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("statisticsCount", 10);
            districtList.forEach(district -> {
                queryMap.put("district", district);
                List<AverageDto> averageList = lianJiaHouseMapper.queryStatisticsDataByCommunity(queryMap);
                if (!CollectionUtils.isEmpty(averageList)) {
                    averageList.forEach(average -> {
                        LianJiaHouseStatisticsEntity statistics = new LianJiaHouseStatisticsEntity();
                        BeanUtils.copyProperties(average, statistics);
                        statistics.setBatchDate(queryDto.getBatchDate());
                        statistics.setDistrict(district);
                        statistics.setStatisticsType(LianJiaCommon.STATISTICS_TYPE_COMMUNITY);
                        statisticsList.add(statistics);
                    });
                }
            });

            if (!CollectionUtils.isEmpty(statisticsList)) {
                statisticsList.forEach(statistics -> {
                    lianJiaHouseStatisticsMapper.insert(statistics);
                });
            }
        }

    }

    /**
     * 获取统计数据
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<LianJiaHouseStatisticsVo> getHouseStatisticsList(LianJiaHouseQueryDto queryDto) {
        List<LianJiaHouseStatisticsVo> voList = new ArrayList<>();
        List<LianJiaHouseStatisticsEntity> houseStatisticsList = lianJiaHouseStatisticsMapper.queryListByBatchDate(queryDto.getBatchDate());
        if (!CollectionUtils.isEmpty(houseStatisticsList)) {
            houseStatisticsList.stream().forEach(houseStatistics -> {
                LianJiaHouseStatisticsVo vo = new LianJiaHouseStatisticsVo();
                BeanUtils.copyProperties(houseStatistics, vo);
                voList.add(vo);
            });
        }
        return voList;
    }

    /**
     * 获取统计数据
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LianJiaHouseStatisticsVo> getHouseStatisticsFromFact(LianJiaHouseQueryDto queryDto) {
        List<LianJiaHouseStatisticsVo> voList = new ArrayList<>();
        List<String> districtList = lianJiaHouseMapper.queryDistrictList();
        if (!CollectionUtils.isEmpty(districtList)) {
            Map<String, Object> queryMap = new HashMap<>();
            districtList.stream().forEach(district -> {
                LianJiaHouseStatisticsVo vo = new LianJiaHouseStatisticsVo();
                queryMap.clear();
                queryMap.put("district", district);
                queryMap.put("batchDate", queryDto.getBatchDate());
                vo.setAverageUnitPrice(lianJiaHouseMapper.queryAverageUnitPrice(queryMap).getAverage());
                vo.setAverageTotalPrice(lianJiaHouseMapper.queryAverageTotalPrice(queryMap).getAverage());
                vo.setTotalCount(lianJiaHouseMapper.queryCountByParam(queryMap));
                queryDto.setDistrict(district);
                vo.setMedianTotalPrice(this.getMedianByTotalPrice(queryDto));
                vo.setMedianUnitPrice(this.getMedianByUnitPrice(queryDto));
                vo.setDistrict(district);
                voList.add(vo);
            });
        }
        return voList;
    }

    /**
     * 获取平均单价
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AverageDto getAverageUnitPrice(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("communityName", queryDto.getCommunity());
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("batchDate", queryDto.getBatchDate());
        return lianJiaHouseMapper.queryAverageUnitPrice(queryMap);
    }

    /**
     * 获取平均总价
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AverageDto getAverageTotalPrice(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("communityName", queryDto.getCommunity());
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("batchDate", queryDto.getBatchDate());
        return lianJiaHouseMapper.queryAverageUnitPrice(queryMap);
    }

    /**
     * 获取单价中位数
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Double getMedianByUnitPrice(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("batchDate", queryDto.getBatchDate());
        return getMedian(lianJiaHouseMapper.queryUnitPriceList(queryMap));
    }

    /**
     * 获取总价中位数
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Double getMedianByTotalPrice(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("batchDate", queryDto.getBatchDate());
        return getMedian(lianJiaHouseMapper.queryTotalPriceList(queryMap));
    }

    private Double getMedian(ArrayList<Double> numList) {
        Double median = 0d;
        if (!CollectionUtils.isEmpty(numList)) {
            int totalSize = numList.size();
            if (totalSize % 2 == 0) {
                median = (numList.get(totalSize / 2) + (numList.get(totalSize / 2 + 1))) / 2;
            } else {
                int middleIndex = (int) Math.ceil(totalSize / 2d);
                median = numList.get(middleIndex - 1);
            }
        }
        return median;
    }
}
