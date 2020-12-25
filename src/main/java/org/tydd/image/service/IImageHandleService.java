package org.tydd.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.image.service
 * @Description 图片处理Service接口
 * @Date 2020/12/24
 */
public interface IImageHandleService {

    /**
     * 保存图片到本地
     * @param imageFile
     * @return 图片路径
     */
    File saveImageLocal(MultipartFile imageFile);

    /**
     * 压缩文件
     * @param imageFile  图片文件
     * @param compressionRatio  压缩比例
     * @return
     */
    String compressionImage(MultipartFile imageFile, Float compressionRatio);
}
