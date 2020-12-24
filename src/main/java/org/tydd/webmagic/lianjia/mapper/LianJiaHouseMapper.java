package org.tydd.webmagic.lianjia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tydd.webmagic.lianjia.dto.AverageDto;
import org.tydd.webmagic.lianjia.entity.LianJiaHouseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.mapper
 * @Description
 * @Date 2020/12/4
 */
public interface LianJiaHouseMapper extends BaseMapper<LianJiaHouseEntity> {

    AverageDto queryAverageUnitPrice(Map<String, Object> queryMap);

    AverageDto queryAverageTotalPrice(Map<String, Object> queryMap);

    @Select("select community_name from t_lianjia_house where district = #{district} group by community_name")
    List<String> queryCommunityList(@Param("district") String district);

    @Select("select district from t_lianjia_house group by district")
    List<String> queryDistrictList();

    Integer queryCountByParam(Map<String, Object> queryMap);

    ArrayList<Double> queryUnitPriceList(Map<String, Object> queryMap);

    ArrayList<Double> queryTotalPriceList(Map<String, Object> queryMap);

    List<AverageDto> queryStatisticsDataByCommunity(Map<String, Object> queryMap);
}
