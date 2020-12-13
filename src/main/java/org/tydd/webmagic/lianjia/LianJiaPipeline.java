package org.tydd.webmagic.lianjia;

import com.alibaba.nacos.common.utils.Md5Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.tydd.webmagic.lianjia.dto.LianJiaHouseDto;
import org.tydd.webmagic.lianjia.entity.LianJiaHouseEntity;
import org.tydd.webmagic.lianjia.mapper.LianJiaHouseMapper;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import javax.annotation.Resource;

/**
 * author: minkun
 * date: 2020/10/16
 * Description:
 */
@Slf4j
@Component
public class LianJiaPipeline extends FilePersistentBase implements Pipeline {

    @Resource
    private LianJiaHouseMapper lianJiaHouseMapper;

    @SneakyThrows
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("LianJiaPipeline process, resultItems = " + resultItems.getAll().toString());
        if (resultItems.get("communityName") != null && !"".equals(resultItems.get("communityName"))) {
            LianJiaHouseDto houseDto = new LianJiaHouseDto();
            houseDto.setCommunityName(resultItems.get("communityName"));
            houseDto.setTitle(resultItems.get("title"));
            houseDto.setArea(resultItems.get("area"));
            houseDto.setDistrict(resultItems.get("district"));
            houseDto.setImageUrls(resultItems.get("imageUrls"));
            houseDto.setTotalPrice(Double.valueOf(resultItems.get("totalPrice")));
            houseDto.setUnitPrice(Double.valueOf(resultItems.get("unitPrice")));
            houseDto.setRoomInfo(resultItems.get("roomInfo"));
            LianJiaHouseEntity entity = new LianJiaHouseEntity();
            BeanUtils.copyProperties(houseDto, entity);
            entity.setImageUrls(houseDto.getImageUrls().toString());
            entity.setCommunityNameMd5(Md5Utils.getMD5(entity.getCommunityName().getBytes()));
            lianJiaHouseMapper.insert(entity);
        }
    }
}
