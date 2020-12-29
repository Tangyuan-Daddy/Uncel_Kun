package org.tydd.tax.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.dto
 * @Description 个税计算dto
 * @Date 2020/12/29
 */
@Data
@ToString
public class CalculationTaxDto {

    /** 税前收入 */
    @NotNull(message = "税前收入不能为空")
    @Min(value = 1)
    private Double preTaxIncome;

    /** 公积金比例 */
    @NotNull(message = "公积金比例不能为空")
    @Min(value = 0)
    private Double housingProvidentFundRatio;

    /** 养老保险比例 */
    @NotNull(message = "养老保险比例不能为空")
    @Min(value = 0)
    private Double endowmentInsuranceRatio;

    /** 医疗保险比例 */
    @NotNull(message = "医疗保险比例不能为空")
    @Min(value = 0)
    private Double medicalInsuranceRatio;

    /** 失业保险比例  */
    @NotNull(message = "失业保险比例不能为空")
    @Min(value = 0)
    private Double unemploymentInsuranceRatio;

    /** 工伤保险比例  */
    @NotNull(message = "工伤保险比例不能为空")
    @Min(value = 0)
    private Double employmentInjuryInsuranceRatio;

    /** 生育保险比例  */
    @NotNull(message = "生育保险比例不能为空")
    @Min(value = 0)
    private Double maternityInsuranceRatio;

    /** 是否有子女教育附加扣除（1：有 2：无） */
    private String childEducation;

    /** 是否有继续教育附加扣除-学历教育（1：有 2：无） */
    private String continuingEducationA;

    /** 是否有继续教育附加扣除-职业资格（1：有 2：无） */
    private String continuingEducationB;

    /** 是否有大病医疗附加扣除（1：有 2：无） */
    private String medicalTreatment;

    /** 大病医疗花费总额 */
    private String medicalTreatmentSum;

    /** 是否有住房贷款附加扣除（1：有 2：无） */
    private String housingLoan;

    /** 是否有住房租金附加扣除（1：有 2：无） */
    private String housingRent;

    /** 是否有赡养老人附加扣除（1：有 2：无） */
    private String supportOldMan;

    /** 总期数 */
    private Integer totalPeriod;
}
