package org.tydd.tax.vo;

import lombok.Data;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.vo
 * @Description 个税计算结果-每期明细
 * @Date 2020/12/29
 */
@Data
public class CalculationTaxViewVo {

    /** 期数 */
    private Integer period;

    /** 税后收入 */
    private Double afterTaxIncome;

    /** 税金 */
    private Double taxes;

    /** 累计已缴纳税金 */
    private Double totalTaxes;

    /** 专项扣除（五险一金） */
    private SpecialDeductionVo specialDeduction;

    /** 应纳税所得额 */
    private Double taxableIncome;

    /** 累计应纳税所得额 */
    private Double totalTaxableIncome;

    /** 本期适用税率 */
    private int taxRate;
}
