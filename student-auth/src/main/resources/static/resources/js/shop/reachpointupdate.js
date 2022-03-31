$(function () {
    var reachPointId = getQueryString('reachPointid');
    var isEdit = reachPointId ? true : false;
    var intUrl = '/exceladmin/getexcelinitinfo';
    var excelInfoUrl = "/exceladmin/getreachpointbyid?reachPointId=" + reachPointId;
    var editExcelUrl = '/exceladmin/modifyReachPoint';
    if (!isEdit) {
        getexcelinitinfo();
    } else {
        getexcelinfo();
    }

    function getexcelinfo(reachPointId) {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var reachPoint = data.reachPoint;
                $('#reach-score').val(reachPoint.reachScore);
                $('#point-desc').val(reachPoint.pointDesc);
                $('#teach-target').val(reachPoint.teachTarget);
                $('#reach-way').val(reachPoint.reachWay);
                $('#evaluation-basis').val(reachPoint.evaluationBasis);
                $('#excel-id').val(reachPoint.excelId);
                $('#point-id').val(reachPoint.pointId);
                $('#class-grade').val(reachPoint.classGrade);
                $('#work-grade').val(reachPoint.workGrade);
                $('#exp-grade').val(reachPoint.expGrade);
                $('#last-grade').val(reachPoint.lastGrade);
                $('#reach-record').val(reachPoint.reachRecord);
                $('#reach-change').val(reachPoint.reachChange);
                $('#reach-resources').val(reachPoint.reachResources);
            }
        });
    }

    $('#submit').click(function () {
        var reachPoint = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            reachPoint.reachPointId = reachPointId;
        }
        reachPoint.excelId = $('#excel-id').val();
        reachPoint.pointId = $('#point-id').val();
        reachPoint.reachScore = $('#reach-score').val();
        reachPoint.pointDesc = $('#point-desc').val();
        reachPoint.teachTarget = $('#teach-target').val();
        reachPoint.reachWay = $('#reach-way').val();
        reachPoint.evaluationBasis = $('#evaluation-basis').val();
        reachPoint.classGrade = $('#class-grade').val();
        reachPoint.workGrade = $('#work-grade').val();
        reachPoint.expGrade = $('#exp-grade').val();
        reachPoint.lastGrade = $('#last-grade').val();
        reachPoint.reachRecord = $('#reach-record').val();
        reachPoint.reachChange = $('#reach-change').val();
        reachPoint.reachResources = $('#reach-resources').val();
        var formData = new FormData();
        formData.append('reachPointStr', JSON.stringify(reachPoint));

        $.ajax({
            url: editExcelUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功');
                } else {
                    $.toast('提交失败!' + data.errMsg);
                }
            }
        });
    });
});