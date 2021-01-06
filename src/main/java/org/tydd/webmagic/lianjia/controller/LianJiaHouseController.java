package org.tydd.webmagic.lianjia.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.tydd.common.LianJiaCommon;
import org.tydd.common.ResponseVo;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.service.ILianJiaHouseService;
import org.tydd.webmagic.lianjia.service.ILianJiaHouseStatisticsService;

import javax.annotation.Resource;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.controller
 * @Description
 * @Date 2020/12/15
 */
@Api(tags = "链家房产信息")
@RestController
@RequestMapping(value = "lianjia")
public class LianJiaHouseController {

    @Resource
    @Qualifier("lianJiaHouseServiceImpl")
    private ILianJiaHouseService lianJiaHouseService;

    @Resource
    @Qualifier("lianJiaHouseStatisticsService")
    private ILianJiaHouseStatisticsService lianJiaHouseStatisticsService;

    /**
     * 获取行政区统计数据
     * @param batchDate 批次时间（YYYY-MM）
     * @return
     */
    @ApiOperation(value = "获取行政区统计数据", notes = "batchDate（yyyy-MM）")
    @GetMapping(value = "/statistics/district")
    public ResponseVo getStatisticsByDistrict(String batchDate) {
        if (StringUtils.isEmpty(batchDate)) {
            // 不指定批次时间，默认取最后一次统计时间
            batchDate = lianJiaHouseStatisticsService.getLastStatisticalTime();
        }
        LianJiaHouseQueryDto queryDto = new LianJiaHouseQueryDto();
        queryDto.setStatisticsType(LianJiaCommon.STATISTICS_TYPE_DISTRICT);
        queryDto.setBatchDate(batchDate);
        return ResponseVo.success(lianJiaHouseStatisticsService.getHouseStatisticsListByDistrict(queryDto));
    }

    /**
     * 获取小区统计数据
     * @param queryDto
     * @return
     */
    @ApiOperation(value = "获取小区统计数据", notes = "")
    @PostMapping(value = "/statistics/community")
    public ResponseVo getStatisticsByCommunity(@RequestBody LianJiaHouseQueryDto queryDto) {
        if (StringUtils.isEmpty(queryDto.getBatchDate())) {
            // 不指定批次时间，默认取最后一次统计时间
            queryDto.setBatchDate(lianJiaHouseStatisticsService.getLastStatisticalTime());
        }
        return ResponseVo.success(lianJiaHouseStatisticsService.getHouseStatisticsByCommunity(queryDto));
    }

    /**
     * 生成统计数据
     * @param batchDate 批次时间（YYYY-MM）
     * @return
     */
    @ApiOperation(value = "生成统计数据", notes = "batchDate（yyyy-MM）")
    @GetMapping(value = "/statistics/district/build")
    public ResponseVo buildStatisticsByDistrict(@RequestParam("batchDate") String batchDate) {
        LianJiaHouseQueryDto queryDto = new LianJiaHouseQueryDto();
        queryDto.setBatchDate(batchDate);
        lianJiaHouseService.buildStatistics(queryDto);
        lianJiaHouseService.buildStatisticsByCommunity(queryDto);
        return ResponseVo.success();
    }
}
