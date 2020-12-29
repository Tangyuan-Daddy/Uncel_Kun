package org.tydd.tax.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.config
 * @Description 计算个税配置
 * @Date 2020/12/29
 */
@Data
@RefreshScope
@Configuration
public class CalculationTaxConfig {

    /** 子女教育附加扣除额 */
    @Value("${tax.deduction.childEducation}")
    private Double childEducation;

    /** 继续教育附加扣除额A（学历教育） */
    @Value("${tax.deduction.continuingEducationA}")
    private Double continuingEducationA;

    /** 继续教育附加扣除额B（职业资格） */
    @Value("${tax.deduction.continuingEducationB}")
    private Double continuingEducationB;

    /** 大病医疗附加扣除额-下限 */
    @Value("${tax.deduction.medicalTreatmentMin}")
    private Double medicalTreatmentMin;

    /** 大病医疗附加扣除额-上限 */
    @Value("${tax.deduction.medicalTreatmentMax}")
    private Double medicalTreatmentMax;

    /** 住房贷款附加扣除额 */
    @Value("${tax.deduction.housingLoan}")
    private Double housingLoan;

    /** 住房租金附加扣除额 */
    @Value("${tax.deduction.housingRent}")
    private Double housingRent;

    /** 赡养老人附加扣除额 */
    @Value("${tax.deduction.supportOldMan}")
    private Double supportOldMan;

    /** 免征额 */
    @Value("${tax.threshold}")
    private Integer taxThreshold;
}
