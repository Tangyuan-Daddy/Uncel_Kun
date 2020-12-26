package org.tydd.image.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tydd.common.ResponseVo;
import org.tydd.image.service.IImageHandleService;

import javax.annotation.Resource;

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
@Api(value="图片工具", description="提供图片相关的工具" )
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
    @ApiOperation(value = "压缩图片", notes = "对图片进行压缩处理")
    @RequestMapping(value = "/handle/compression", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo compressionImage(@RequestParam("imageFile") MultipartFile imageFile, @RequestParam("ratio") Float ratio) {
        log.info("image name = " + imageFile.getOriginalFilename() + ", image size = " + imageFile.getSize());
        return ResponseVo.success(imageHandleService.compressionImage(imageFile, ratio));
    }

}
