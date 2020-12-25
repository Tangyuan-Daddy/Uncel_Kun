package org.tydd.image.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tydd.image.service.IImageHandleService;
import org.tydd.utils.encryption.Md5Util;

import java.io.File;
import java.io.IOException;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.image.service.impl
 * @Description 图片处理Service实现
 * @Date 2020/12/24
 */
@Slf4j
@Service("imageHandleServiceImpl")
public class ImageHandleServiceImpl implements IImageHandleService {

    /** 保存图片的根目录 */
    @Value("${image.filePath}")
    private String fileRootPath;

    @Value("${image.baseUrl}")
    private String baseUrl;

    @Override
    public File saveImageLocal(MultipartFile imageFile) {
        String path = fileRootPath;
        String fileName = imageFile.getOriginalFilename();
        String saveFileName = Md5Util.encode(fileName.substring(0, fileName.indexOf(".")) + System.currentTimeMillis()) + fileName.substring(fileName.indexOf("."));
        File filePath = new File(path, saveFileName);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            log.info("create parent file path, filePath = " + filePath);
        }
        // 写入文件
        try {
            imageFile.transferTo(filePath);
        } catch (IOException ex) {
            log.error("save file error.", ex);
        }
        return filePath;
    }

    /**
     * 压缩文件
     * @param imageFile  图片文件
     * @param compressionRatio  压缩比例
     * @return
     */
    @Override
    public String compressionImage(MultipartFile imageFile, Float compressionRatio) {
        String compressionImageUrl = null;
        File filePath = saveImageLocal(imageFile);
        if (filePath != null) {
            try {
                Thumbnails.of(filePath.getAbsolutePath())
                        .scale(1f)
                        .outputQuality(compressionRatio)
                        .toFile(fileRootPath + "/compression/" + filePath.getAbsoluteFile().getName());
                compressionImageUrl = baseUrl + filePath.getAbsoluteFile().getName();
            } catch (IOException e) {
                log.error("图片压缩异常。", e);
            }
        }
        return compressionImageUrl;
    }
}