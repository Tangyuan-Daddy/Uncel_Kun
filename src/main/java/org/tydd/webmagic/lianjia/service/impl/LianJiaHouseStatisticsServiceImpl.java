package org.tydd.webmagic.lianjia.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tydd.common.LianJiaCommon;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseStatisticsVo;
import org.tydd.webmagic.lianjia.entity.LianJiaHouseStatisticsEntity;
import org.tydd.webmagic.lianjia.mapper.LianJiaHouseStatisticsMapper;
import org.tydd.webmagic.lianjia.service.ILianJiaHouseStatisticsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.webmagic.lianjia.service.impl
 * @Description 链家二手房信息统计Service实现
 * @Date 2020/12/23
 */
@Slf4j
@Service("lianJiaHouseStatisticsService")
public class LianJiaHouseStatisticsServiceImpl implements ILianJiaHouseStatisticsService {

    @Resource
    private LianJiaHouseStatisticsMapper lianJiaHouseStatisticsMapper;

    /**
     * 获取最后统计时间
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public String getLastStatisticalTime() {
        return lianJiaHouseStatisticsMapper.queryLastBatchData(null);
    }

    /**
     * 按照行政区维度查询统计数据
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<LianJiaHouseStatisticsVo> getHouseStatisticsListByDistrict(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>(5);
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("batchDate", queryDto.getBatchDate());
        queryMap.put("statisticsType", queryDto.getStatisticsType());
        return this.buildVoList(lianJiaHouseStatisticsMapper.queryHouseStatisticsList(queryMap));
    }

    /**
     * 按照社区维度查询统计数据
     * @param queryDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<LianJiaHouseStatisticsVo> getHouseStatisticsByCommunity(LianJiaHouseQueryDto queryDto) {
        Map<String, Object> queryMap = new HashMap<>(6);
        queryMap.put("district", queryDto.getDistrict());
        queryMap.put("community", queryDto.getCommunity());
        queryMap.put("batchDate", queryDto.getBatchDate());
        queryMap.put("statisticsType", queryDto.getStatisticsType());
        int current = 1;
        int size = 10;
        IPage<LianJiaHouseStatisticsEntity> queryPage = new Page<>(current, size);
        queryPage = lianJiaHouseStatisticsMapper.queryHouseStatisticsPage(queryPage, queryMap);
        return buildVoList(queryPage.getRecords());
    }

    /**
     * 构建VO返回对象
     * @param entityList
     * @return
     */
    private List<LianJiaHouseStatisticsVo> buildVoList(List<LianJiaHouseStatisticsEntity> entityList) {
        List<LianJiaHouseStatisticsVo> voList = new ArrayList<>(entityList.size());
        if (!CollectionUtils.isEmpty(entityList)) {
            entityList.stream().forEach(entity -> {
                LianJiaHouseStatisticsVo vo = new LianJiaHouseStatisticsVo();
                BeanUtils.copyProperties(entity, vo);
                voList.add(vo);
            });
        }
        return voList;
    }
}
