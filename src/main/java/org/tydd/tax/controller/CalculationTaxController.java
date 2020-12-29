package org.tydd.tax.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tydd.common.ResponseVo;
import org.tydd.tax.dto.CalculationTaxDto;
import org.tydd.tax.service.ICalculationTaxService;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax.controller
 * @Description 个税计算
 * @Date 2020/12/29
 */
@Api(value="个人所得税")
@Slf4j
@RestController
@RequestMapping(value = "tax")
public class CalculationTaxController {

    @Autowired
    private ICalculationTaxService calculationTaxService;

    @ApiOperation(value = "计算个税 - 按月", notes = "按照月份累进计算个税")
    @PostMapping(value = "/calculation/month")
    public ResponseVo calculationTaxForMonth(@Validated @RequestBody CalculationTaxDto calculationTax) {
        return ResponseVo.success(calculationTaxService.calculateTax(calculationTax));
    }
}
