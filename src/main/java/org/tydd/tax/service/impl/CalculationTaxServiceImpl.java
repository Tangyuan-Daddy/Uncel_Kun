package org.tydd.tax.service.impl;

import com.alibaba.nacos.client.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tydd.tax.config.CalculationTaxConfig;
import org.tydd.tax.dto.CalculationTaxDto;
import org.tydd.tax.dto.TaxRateDto;
import org.tydd.tax.service.ICalculationTaxService;
import org.tydd.tax.vo.CalculationTaxViewVo;
import org.tydd.tax.vo.CalculationTaxVo;
import org.tydd.tax.vo.SpecialDeductionVo;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.service.impl
 * @Description
 * @Date 2020/12/28
 */
@Slf4j
@Service("taxCalculationService")
public class CalculationTaxServiceImpl implements ICalculationTaxService {

    /** 选中 */
    private final static String CHECK_FLAG = "1";

    /** 未选中 */
    private final static String NOT_CHECK_FLAG = "2";

    @Resource
    private CalculationTaxConfig calculationTaxConfig;

    /** 税率Json字符串 */
    @Value("${tax.install}")
    private String taxInstall;

    /**
     * 获取税率表
     * @return
     */
    @Override
    public LinkedList<TaxRateDto> getTaxRate() {
        LinkedList<TaxRateDto> taxRateList = new LinkedList<>();
        try {
            taxRateList = (LinkedList<TaxRateDto>) JSONUtils.deserializeObject(taxInstall, taxRateList.getClass());
            log.info("taxInstall = " + taxRateList);
        } catch (IOException e) {
            log.info("获取税率表转Json异常", e);
        }
        return taxRateList;
    }

    /**
     * 计算个税
     * @param calculationTax
     * @return
     */
    @Override
    public CalculationTaxVo calculateTax(CalculationTaxDto calculationTax) {
        Integer totalPeriod = calculationTax.getTotalPeriod();
        // 获取专项扣除额度（五险一金）
        SpecialDeductionVo specialDeduction = this.getSpecialDeduction(calculationTax);
        List<CalculationTaxViewVo> viewList = new LinkedList();
        Double totalTaxIncome = 0d;
        Double afterTaxIncome = 0d;
        for (int i = 1; i <= totalPeriod; i++) {
            CalculationTaxViewVo calculationTaxViewVo = computeTaxableIncome(calculationTax, specialDeduction, i, totalTaxIncome);
            viewList.add(calculationTaxViewVo);
            totalTaxIncome += calculationTaxViewVo.getTaxes();
            afterTaxIncome += calculationTaxViewVo.getAfterTaxIncome();
        }
        CalculationTaxVo calculationTaxVo = new CalculationTaxVo();
        calculationTaxVo.setViewList(viewList);
        calculationTaxVo.setPreTaxIncome(calculationTax.getPreTaxIncome() * 12);
        calculationTaxVo.setAfterTaxIncome(afterTaxIncome);
        return calculationTaxVo;
    }

    /**
     * 计算应纳税所得额
     * @param calculationTax   个税计算参数
     * @param specialDeduction 专项扣除额度（五险一金）
     * @param period           期数
     * @param totalTaxIncome   累计纳税总额
     * @return
     */
    @Override
    public CalculationTaxViewVo computeTaxableIncome(CalculationTaxDto calculationTax, SpecialDeductionVo specialDeduction, Integer period, Double totalTaxIncome) {
        CalculationTaxViewVo calculationTaxView = new CalculationTaxViewVo();
        calculationTaxView.setSpecialDeduction(specialDeduction);
        calculationTaxView.setPeriod(period);
        // 应纳税所得额
        Double taxableIncome = 0d;
        // 税前收入
        Double preTaxIncome = calculationTax.getPreTaxIncome();
        // 判断是否大于个税起征额
        if (preTaxIncome > calculationTaxConfig.getTaxThreshold()) {
            // 扣除免征额
            taxableIncome = preTaxIncome - calculationTaxConfig.getTaxThreshold();
            if (taxableIncome > specialDeduction.getTotalSpecialDeduction()) {
                // 扣除专项扣除额度（五险一金）
                taxableIncome = taxableIncome - specialDeduction.getTotalSpecialDeduction();
                // 获取附加扣除额
                Double additionalDeduction = getAdditionalDeduction(calculationTax);
                if (taxableIncome > additionalDeduction) {
                    taxableIncome = taxableIncome - additionalDeduction;
                } else {
                    taxableIncome = 0d;
                }
            }
        }
        log.info("taxableIncome = " + taxableIncome);
        calculationTaxView.setTaxes(0d);
        calculationTaxView.setAfterTaxIncome(0d);
        return calculationTaxView;
    }

    /**
     * 获取专项扣除额度
     * @param calculationTax
     * @return
     */
    private SpecialDeductionVo getSpecialDeduction(CalculationTaxDto calculationTax) {
        SpecialDeductionVo specialDeduction = new SpecialDeductionVo();
        Double preTaxIncome = calculationTax.getPreTaxIncome();
        specialDeduction.setHousingProvidentFund((preTaxIncome / 100) * calculationTax.getHousingProvidentFundRatio());
        specialDeduction.setEmploymentInjuryInsurance((preTaxIncome / 100) * calculationTax.getEmploymentInjuryInsuranceRatio());
        specialDeduction.setEndowmentInsurance((preTaxIncome / 100) * calculationTax.getEndowmentInsuranceRatio());
        specialDeduction.setMaternityInsurance((preTaxIncome / 100) * calculationTax.getMaternityInsuranceRatio());
        specialDeduction.setMedicalInsurance((preTaxIncome / 100) * calculationTax.getMedicalInsuranceRatio());
        specialDeduction.setUnemploymentInsurance((preTaxIncome / 100) *  calculationTax.getUnemploymentInsuranceRatio());
        specialDeduction.setTotalSpecialDeduction(specialDeduction.getHousingProvidentFund() +
                specialDeduction.getEmploymentInjuryInsurance() +
                specialDeduction.getEndowmentInsurance() +
                specialDeduction.getMaternityInsurance() +
                specialDeduction.getMedicalInsurance() +
                specialDeduction.getUnemploymentInsurance());
        return specialDeduction;
    }

    /**
     * 获取附加扣除额度
     * @param calculationTax
     * @return
     */
    private Double getAdditionalDeduction(CalculationTaxDto calculationTax) {
        Double additionalDeduction = 0d;
        if (CHECK_FLAG.equals(calculationTax.getChildEducation())) {
            additionalDeduction += calculationTaxConfig.getChildEducation();
        }
        if (CHECK_FLAG.equals(calculationTax.getContinuingEducationA())) {
            additionalDeduction += calculationTaxConfig.getContinuingEducationA();
        }
        if (CHECK_FLAG.equals(calculationTax.getContinuingEducationB())) {
            additionalDeduction += calculationTaxConfig.getContinuingEducationB();
        }
        if (CHECK_FLAG.equals(calculationTax.getHousingLoan())) {
            additionalDeduction += calculationTaxConfig.getHousingLoan();
        }
        if (CHECK_FLAG.equals(calculationTax.getHousingRent())) {
            additionalDeduction += calculationTaxConfig.getHousingRent();
        }
        if (CHECK_FLAG.equals(calculationTax.getSupportOldMan())) {
            additionalDeduction += calculationTaxConfig.getSupportOldMan();
        } else if ("3".equals(calculationTax.getSupportOldMan())) {
            additionalDeduction += calculationTaxConfig.getSupportOldMan() / 2;
        }
        return additionalDeduction;
    }
}
