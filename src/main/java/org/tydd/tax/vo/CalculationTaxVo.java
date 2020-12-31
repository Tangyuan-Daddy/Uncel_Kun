package org.tydd.tax.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.vo
 * @Description 个税计算结果
 * @Date 2020/12/29
 */
@Data
@ToString
@ApiModel("个税计算结果")
public class CalculationTaxVo {

    /** 税前总收入 */
    @ApiModelProperty("税前总收入")
    private Double preTaxIncome;

    /** 税后总收入 */
    @ApiModelProperty("税后总收入")
    private Double afterTaxIncome;

    /** 每期明细 */
    @ApiModelProperty("每期明细")
    private List<CalculationTaxViewVo> viewList;
}
