package org.tydd.image.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tydd.common.ResponseVo;
import org.tydd.image.service.IImageHandleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.image
 * @Description 图片工具控制层
 * @Date 2020/12/24
 */
@Slf4j
@Controller
@RequestMapping(value = "image")
public class ImageHandleController {

    @Resource
    @Qualifier("imageHandleServiceImpl")
    private IImageHandleService imageHandleService;

    /**
     * 压缩图片
     * @param imageFile 图片文件
     * @param ratio 压缩比例
     * @return
     */
    @RequestMapping(value = "/handle/compression")
    @ResponseBody
    public ResponseVo compressionImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("ratio") Float ratio) {
        return ResponseVo.success(imageHandleService.compressionImage(imageFile, ratio));
    }

}
