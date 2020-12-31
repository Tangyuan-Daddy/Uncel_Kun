package org.tydd.tax.service;

import org.tydd.tax.dto.CalculationTaxDto;
import org.tydd.tax.dto.TaxRateDto;
import org.tydd.tax.vo.CalculationTaxViewVo;
import org.tydd.tax.vo.CalculationTaxVo;
import org.tydd.tax.vo.SpecialDeductionVo;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.service
 * @Description 个税计算Service接口
 * @Date 2020/12/28
 */
public interface ICalculationTaxService {

    /**
     * 获取税率表
     * @return
     */
    ArrayList<TaxRateDto> getTaxRate();

    /**
     * 计算个税
     * @param taxCalculation
     * @return
     */
    CalculationTaxVo calculateTax(CalculationTaxDto taxCalculation);

    /**
     * 计算应纳税所得额
     * @param calculationTax   个税计算参数
     * @param specialDeduction 专项扣除额度（五险一金）
     * @param period           期数
     * @param totalTaxIncome   累计纳税总额
     * @return
     */
    CalculationTaxViewVo computeTaxableIncome(CalculationTaxDto calculationTax, SpecialDeductionVo specialDeduction, Integer period, Double totalTaxIncome, Double totalTaxes);
}
