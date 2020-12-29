package org.tydd.tax.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.dto
 * @Description 税率表
 * @Date 2020/12/28
 */
@Data
@ToString
public class TaxRateDto {

    /** 所得额下限（不包含） */
    private int incomeMin;

    /** 所得额上限（包含） */
    private int incomeMax;

    /** 税率（%） */
    private int taxRate;
}
