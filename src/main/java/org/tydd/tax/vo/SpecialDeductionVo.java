package org.tydd.tax.vo;

import lombok.Data;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.vo
 * @Description 专项扣除（五险一金）
 * @Date 2020/12/29
 */
@Data
public class SpecialDeductionVo {

    /** 公积金 */
    private Double housingProvidentFund;

    /** 养老保险 */
    private Double endowmentInsurance;

    /** 医疗保险 */
    private Double medicalInsurance;

    /** 失业保险 */
    private Double unemploymentInsurance;

    /** 工伤保险 */
    private Double employmentInjuryInsurance;

    /** 生育保险 */
    private Double maternityInsurance;

    /** 专项扣除总额 */
    private Double totalSpecialDeduction;
}
