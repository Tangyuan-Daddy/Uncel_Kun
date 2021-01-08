//form表单数据序列化为json格式
$.fn.serializeJson = function() {
    const serializeObj = {};
    const array = this.serializeArray();
    const str = this.serialize();
    $(array).each(
        function() {
            if (serializeObj[this.name])  {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [
                        serializeObj[this.name], this.value ];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
    return serializeObj;
};

toastr.options.positionClass = 'toast-bottom-right';

function calculationTax() {
    const formJson = $("#taxForm").serializeJson();
    console.log(formJson);
    $.ajax({
        type: "post",
        dataType:'json',
        url: "http://182.92.230.190/tydd/tax/calculation/month",
        contentType: 'application/json',
        data: JSON.stringify($("#taxForm").serializeJson()),
        success: function(data){
            if (data.code == 1) {
                toastr.success('计算完成。');
                buildResultDiv(data.data);
            } else {
                toastr.warning('计算异常。');
            }
        }
    });
}

function buildResultDiv(data) {
    let replaceHtml = '';
    const templateSummaryHtml = $('#template-summary').html();
    replaceHtml += templateSummaryHtml.replace('${preTaxIncome}', data.preTaxIncome).replace('${afterTaxIncome}', data.afterTaxIncome);
    replaceHtml += "<hr>";
    const viewList = data.viewList;
    for (let i = 0; i < viewList.length; i++) {
        const view = viewList[i];
        const templateHtml = $('#template-view').html();
        replaceHtml += templateHtml.replace('${period}', view.period)
            .replace('${afterTaxIncome}', view.afterTaxIncome)
            .replace('${taxes}', view.taxes)
            .replace('${taxRate}', view.taxRate)
            .replace('${totalTaxes}', view.totalTaxes)
            .replace('${totalTaxableIncome}', view.totalTaxableIncome)
            .replace('${housingProvidentFund}', view.specialDeduction.housingProvidentFund)
            .replace('${endowmentInsurance}', view.specialDeduction.endowmentInsurance)
            .replace('${medicalInsurance}', view.specialDeduction.medicalInsurance)
            .replace('${unemploymentInsurance}', view.specialDeduction.unemploymentInsurance)
            .replace('${employmentInjuryInsurance}', view.specialDeduction.employmentInjuryInsurance)
            .replace('${maternityInsurance}', view.specialDeduction.maternityInsurance);
        if (i != viewList.length - 1) {
            replaceHtml += '<hr>';
        }
    }
    $('#resultDiv').html(replaceHtml);
}

function clickCountButton() {
    calculationTax();
}

function openBaseNumberInput() {
    if ($("input[name='customBase']").prop('checked')) {
        $("input[name='baseNumber']").removeAttr('disabled');
    } else {
        $("input[name='baseNumber']").attr('disabled', 'disabled');
        $("input[name='baseNumber']").val('');
    }
}

$(function(){
    $("#countBtn").click(function(){clickCountButton()});
    $("input[name='customBase']").click(function () {
        openBaseNumberInput();
    });
});