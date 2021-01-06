package org.tydd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tydd.common.ResponseVo;
import org.tydd.webmagic.lianjia.LianjiaHousePageProcessor;

import javax.annotation.Resource;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.controller
 * @Description
 * @Date 2020/12/4
 */
@Api(tags = "爬虫")
@RestController
@RequestMapping("webmagic")
public class WebMagicController {

    @Resource
    private LianjiaHousePageProcessor lianjiaHousePageProcessor;

    @ApiOperation(value = "启动链家爬虫", notes = "收集二手房信息")
    @GetMapping(value = "/execute/lianjia")
    public ResponseVo executeLianJia() {
        lianjiaHousePageProcessor.executeAsync();
        return ResponseVo.success();
    }
}
