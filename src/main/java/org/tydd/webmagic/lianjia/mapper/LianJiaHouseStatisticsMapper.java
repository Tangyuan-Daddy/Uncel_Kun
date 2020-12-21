package org.tydd.webmagic.lianjia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.tydd.webmagic.lianjia.entity.LianJiaHouseStatisticsEntity;

import java.util.List;

/**
 * @author minkun
 * @Project Uncel Kun
 * @Package org.tydd.webmagic.lianjia.mapper
 * @Description
 * @Date 2020/12/16
 */
public interface LianJiaHouseStatisticsMapper extends BaseMapper<LianJiaHouseStatisticsEntity> {

    @Delete("delete from t_lianjia_house_statistics where batch_date = #{batchDate}")
    Integer deleteByBatchDate(@Param("batchDate") String batchDate);

    @Select("select * from t_lianjia_house_statistics where batch_date = #{batchDate} order by district")
    List<LianJiaHouseStatisticsEntity> queryListByBatchDate(@Param("batchDate") String batchDate);
}
