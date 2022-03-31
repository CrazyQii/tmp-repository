$(function () {
    var excelId = getQueryString('excelId');
    var excelInfoUrl = '/exceladmin/getexcelmanagementinfo?excelId=' + excelId;
    $.getJSON(excelInfoUrl, function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.excelId != undefined && data.excelId != null) {
                excelId = data.excelId;
            }
            $('#excelInfo')
                .attr('href', '/exceladmin/testinput?excelId=' + excelId);
            //$('#excelInfo')
            //.attr('href', '/exceladmin/exceloperation?excelId=' + excelId);
        }
    });
});