package org.tydd.webmagic.lianjia.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.dto
 * @Description
 * @Date 2020/12/4
 */
@Data
@ToString
public class LianJiaHouseDto {

    private String communityName;

    private Double totalPrice;

    private Double unitPrice;

    private Double area;

    private String district;

    private String roomInfo;

    private String title;

    private List<String> imageUrls;
}
