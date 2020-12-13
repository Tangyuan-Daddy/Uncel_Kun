package org.tydd.webmagic.lianjia.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.entity
 * @Description
 * @Date 2020/12/4
 */
@Data
@TableName("t_lianjia_house")
public class LianJiaHouseEntity {

    private  Long id;

    private String communityName;

    private String communityNameMd5;

    private Double totalPrice;

    private Double unitPrice;

    private Double area;

    private String district;

    private String roomInfo;

    private String title;

    private String imageUrls;

}
