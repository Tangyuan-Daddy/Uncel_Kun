package org.tydd.tax.vo;

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
public class CalculationTaxVo {

    /** 年 - 税前总收入 */
    private Double preTaxIncome;

    /** 年 - 税后总收入 */
    private Double afterTaxIncome;

    /** 每期明细 */
    private List<CalculationTaxViewVo> viewList;
}
