package org.tydd.controller;

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
@RestController
@RequestMapping("webmagic")
public class WebMagicController {

    @Resource
    private LianjiaHousePageProcessor lianjiaHousePageProcessor;

    @GetMapping(value = "/execute/lianjia")
    public ResponseVo executeLianJia() {
        lianjiaHousePageProcessor.executeAsync();
        return ResponseVo.getSuccess();
    }
}
