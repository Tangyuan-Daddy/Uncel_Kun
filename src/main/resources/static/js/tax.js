function calculationTax() {
    $.ajax({
        type: "post",
        url: "http://127.0.0.1:7001/tydd/tax/calculation/month",
        data: $("[name='taxForm']").serialize(),
        success: function(data){
            console.log(data);
        }
    });
}

function clickCountButton() {
    calculationTax();
}

$(function(){
    $("#countBtn").click(function(){clickCountButton()});
});