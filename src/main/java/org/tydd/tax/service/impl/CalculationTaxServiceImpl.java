package org.tydd.tax.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.nacos.client.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.tydd.common.CalculationTaxCommon.CHECK_FLAG;
import static org.tydd.common.CalculationTaxCommon.SPORT_OLD_MAN_AVERAGE_FLAG;

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
    public ArrayList<TaxRateDto> getTaxRate() {
        ArrayList<TaxRateDto> taxRateList = new ArrayList<>();
        taxRateList.addAll(JSONArray.parseArray(taxInstall, TaxRateDto.class));
        log.info("taxInstall = " + taxRateList);
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
        // 累计应纳税所得额
        Double totalTaxIncome = 0d;
        // 累进税后收入
        Double afterTaxIncome = 0d;
        // 累计缴纳税金
        Double totalTaxes = 0d;
        for (int i = 1; i <= totalPeriod; i++) {
            CalculationTaxViewVo calculationTaxViewVo = computeTaxableIncome(calculationTax, specialDeduction, i, totalTaxIncome, totalTaxes);
            viewList.add(calculationTaxViewVo);
            totalTaxIncome += calculationTaxViewVo.getTaxableIncome();
            afterTaxIncome += calculationTaxViewVo.getAfterTaxIncome();
            totalTaxes += calculationTaxViewVo.getTaxes();
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
    public CalculationTaxViewVo computeTaxableIncome(CalculationTaxDto calculationTax, SpecialDeductionVo specialDeduction, Integer period, Double totalTaxIncome, Double totalTaxes) {
        CalculationTaxViewVo calculationTaxView = new CalculationTaxViewVo();
        calculationTaxView.setSpecialDeduction(specialDeduction);
        calculationTaxView.setPeriod(period);
        calculationTaxView.setTaxes(0d);
        calculationTaxView.setAfterTaxIncome(0d);
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
                // 扣除附加扣除额
                Double additionalDeduction = getAdditionalDeduction(calculationTax);
                if (taxableIncome > additionalDeduction) {
                    taxableIncome = taxableIncome - additionalDeduction;
                } else {
                    taxableIncome = 0d;
                }
            }
        }
        calculationTaxView.setTaxableIncome(taxableIncome);
        calculationTaxView.setTotalTaxableIncome(totalTaxIncome + taxableIncome);
        log.info("taxableIncome = " + taxableIncome);
        if (taxableIncome > 0) {
            ArrayList<TaxRateDto> taxRateDtos = getTaxRate();
            if (!CollectionUtils.isEmpty(taxRateDtos)) {
                Double finalTaxableIncome = taxableIncome;
                int size = taxRateDtos.size();
                for (int i = 0; i < size; i++) {
                    TaxRateDto taxRateDto = taxRateDtos.get(i);
                    if (calculationTaxView.getTotalTaxableIncome() > taxRateDto.getIncomeMin() && calculationTaxView.getTotalTaxableIncome() <= taxRateDto.getIncomeMax()) {
                        // 判断是否跨多个税率区间
                        if ((calculationTaxView.getTotalTaxableIncome() - taxRateDto.getIncomeMin()) <= finalTaxableIncome && i != 0) {
                            // 跨两个税率
                            TaxRateDto firstRateDto = taxRateDtos.get(i - 1);
                            int firstRate = firstRateDto.getTaxRate();
                            Double firstTaxes = Double.valueOf(String.format("%.2f", ((firstRateDto.getIncomeMax() - totalTaxIncome) / 100 * firstRate)));
                            int rate = taxRateDto.getTaxRate();
                            Double secondTaxes = Double.valueOf(String.format("%.2f", (calculationTaxView.getTotalTaxableIncome() - taxRateDto.getIncomeMin()) / 100 * rate));
                            calculationTaxView.setTaxes(firstTaxes + secondTaxes);
                            calculationTaxView.setAfterTaxIncome(preTaxIncome - calculationTaxView.getSpecialDeduction().getTotalSpecialDeduction() - calculationTaxView.getTaxes());
                            calculationTaxView.setTaxRate(rate);
                        } else {
                            // 单个税率区间
                            int rate = taxRateDto.getTaxRate();
                            calculationTaxView.setTaxes(Double.valueOf(String.format("%.2f", (finalTaxableIncome / 100 * rate))));
                            calculationTaxView.setAfterTaxIncome(preTaxIncome - calculationTaxView.getSpecialDeduction().getTotalSpecialDeduction() - calculationTaxView.getTaxes());
                            calculationTaxView.setTaxRate(rate);
                        }
                        calculationTaxView.setTotalTaxes(totalTaxes);
                        break;
                    }
                }
            }
        } else {
            calculationTaxView.setAfterTaxIncome(preTaxIncome - calculationTaxView.getSpecialDeduction().getTotalSpecialDeduction());
        }
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
        // 子女教育
        if (CHECK_FLAG.equals(calculationTax.getChildEducation())) {
            additionalDeduction += calculationTaxConfig.getChildEducation();
        }
        // 继续教育附加扣除-学历教育
        if (CHECK_FLAG.equals(calculationTax.getContinuingEducationA())) {
            additionalDeduction += calculationTaxConfig.getContinuingEducationA();
        }
        // 继续教育附加扣除-职业资格
        if (CHECK_FLAG.equals(calculationTax.getContinuingEducationB())) {
            additionalDeduction += calculationTaxConfig.getContinuingEducationB();
        }
        // 住房贷款
        if (CHECK_FLAG.equals(calculationTax.getHousingLoan())) {
            additionalDeduction += calculationTaxConfig.getHousingLoan();
        }
        // 住房租金
        if (CHECK_FLAG.equals(calculationTax.getHousingRent())) {
            additionalDeduction += calculationTaxConfig.getHousingRent();
        }
        // 赡养老人
        if (CHECK_FLAG.equals(calculationTax.getSupportOldMan())) {
            // 独生子女独占
            additionalDeduction += calculationTaxConfig.getSupportOldMan();
        } else if (SPORT_OLD_MAN_AVERAGE_FLAG.equals(calculationTax.getSupportOldMan())) {
            // 平分
            additionalDeduction += calculationTaxConfig.getSupportOldMan() / 2;
        }
        return additionalDeduction;
    }
}
