package org.tydd.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tydd.common.ResponseVo;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseQueryDto;
import org.tydd.webmagic.lianjia.service.ILianJiaHouseService;

import javax.annotation.Resource;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.controller
 * @Description
 * @Date 2020/12/15
 */
@RestController
@RequestMapping(value = "lianjia")
public class LianJiaHouseController {

    @Resource
    @Qualifier("lianJiaHouseServiceImpl")
    private ILianJiaHouseService lianJiaHouseService;

    /**
     * 获取行政区统计数据
     * @param batchDate 批次时间（YYYY-MM）
     * @return
     */
    @GetMapping(value = "/statistics/district")
    public ResponseVo getStatisticsByDistrict(@RequestParam("batchDate") String batchDate) {
        LianJiaHouseQueryDto queryDto = new LianJiaHouseQueryDto();
        queryDto.setBatchDate(batchDate);
        return ResponseVo.getSuccess(lianJiaHouseService.getHouseStatisticsList(queryDto));
    }

    /**
     * 获取小区统计数据
     * @param batchDate 批次时间（YYYY-MM）
     * @param district  行政区
     * @param community 社区
     * @return
     */
    @GetMapping(value = "/statistics/community")
    public ResponseVo getStatisticsByCommunity(@RequestParam("batchDate") String batchDate, String district, String community) {
        return ResponseVo.getSuccess();
    }

    /**
     * 生成统计数据
     * @param batchDate 批次时间（YYYY-MM）
     * @return
     */
    @GetMapping(value = "/statistics/district/build")
    public ResponseVo buildStatisticsByDistrict(@RequestParam("batchDate") String batchDate) {
        LianJiaHouseQueryDto queryDto = new LianJiaHouseQueryDto();
        queryDto.setBatchDate(batchDate);
        lianJiaHouseService.buildStatistics(queryDto);
        return ResponseVo.getSuccess();
    }
}
