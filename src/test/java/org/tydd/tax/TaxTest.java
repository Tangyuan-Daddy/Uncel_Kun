package org.tydd.tax;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tydd.ServiceProviderApplication;
import org.tydd.tax.dto.TaxRateDto;
import org.tydd.tax.service.ICalculationTaxService;

import javax.annotation.Resource;
import java.util.LinkedList;

/**
 * @author minkun
 * @Project UncleK
 * @Package org.tydd.tax
 * @Description
 * @Date 2020/12/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProviderApplication.class)
public class TaxTest {

    @Resource
    @Qualifier("taxCalculationService")
    private ICalculationTaxService taxCalculationService;

    @Test
    public void testGetTaxRate() {
        LinkedList<TaxRateDto> taxRates = taxCalculationService.getTaxRate();
        log.info(taxRates.toString());
    }
}
