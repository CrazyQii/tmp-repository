$(function () {
    var reachPointId = getQueryString('reachPointId');
    var isEdit = reachPointId ? true : false;
    var intUrl = '/exceladmin/getreachpointinitinfo';
    var registerExcelUrl = '/exceladmin/addreachpoints';
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
                $('#point-graduation').val(reachPoint.pointGraduation);
                $('#point-id-one').val(reachPoint.pointIdOne);
                $('#point-desc-one').val(reachPoint.pointDescOne);
                $('#reach-score-one').val(reachPoint.reachScoreOne);
                $('#teach-target-one').val(reachPoint.teachTargetOne);
                $('#reach-way-one').val(reachPoint.reachWayOne);
                $('#evaluation-basis-one').val(reachPoint.evaluationBasisOne);
                $('#point-id-two').val(reachPoint.pointIdTwo);
                $('#point-desc-two').val(reachPoint.pointDescTwo);
                $('#reach-score-two').val(reachPoint.reachScoreTwo);
                $('#teach-target-two').val(reachPoint.teachTargetTwo);
                $('#reach-way-two').val(reachPoint.reachWayTwo);
                $('#evaluation-basis-two').val(reachPoint.evaluationBasisTwo);
                $('#reach-record').val(reachPoint.reachRecord);
                $('#reach-change').val(reachPoint.reachChange);
                $('#reach-resources').val(reachPoint.reachResources);
                $('#excel-id').val(reachPoint.excelId);
                $('#point-id').val(reachPoint.reachPointId);
                var contentIdHtml = $("#test").html("");
                $.each(data.excellist, function (index, value) {
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                $("#test").val(reachPoint.excelId);
                layui.form.render('select');
            }
        });
    }

    function getexcelinitinfo() {
        $.ajax({//通过Ajax来提交数据
            url: intUrl,
            dataType: "json",
            type: "get",
            success: function (data) {
                console.log(data);
                var contentIdHtml = $("#test").html("");
                contentIdHtml.append('<option value>请选择</option>');
                $.each(data.excelList, function (index, value) {
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                layui.form.render("select");
            }
        })

    }

    $('#submit').click(function () {
        var reachPoint = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            reachPoint.reachPointId = reachPointId;
        }
        reachPoint.excelId = $('#excel-id').val();
        reachPoint.pointIdOne = $('#point-id-one').val();
        reachPoint.pointDescOne = $('#point-desc-one').val();
        reachPoint.reachScoreOne = $('#reach-score-one').val();
        reachPoint.teachTargetOne = $('#teach-target-one').val();
        reachPoint.reachWayOne = $('#reach-way-one').val();
        reachPoint.evaluationBasisOne = $('#evaluation-basis-one').val();
        reachPoint.pointIdTwo = $('#point-id-two').val();
        reachPoint.pointDescTwo = $('#point-desc-two').val();
        reachPoint.reachScoreTwo = $('#reach-score-two').val();
        reachPoint.teachTargetTwo = $('#teach-target-two').val();
        reachPoint.reachWayTwo = $('#reach-way-two').val();
        reachPoint.evaluationBasisTwo = $('#evaluation-basis-two').val();
        reachPoint.reachRecord = $('#reach-record').val();
        reachPoint.reachChange = $('#reach-change').val();
        reachPoint.reachResources = $('#reach-resources').val();
        reachPoint.pointGraduation = $('#point-graduation').val();
        var options = $("#test option:selected");
        reachPoint.excelId = options.val();
        var formData = new FormData();
        formData.append('reachPointStr', JSON.stringify(reachPoint));
        $.ajax({
            url: isEdit ? editExcelUrl : registerExcelUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    layer.msg('提交成功');
                } else {
                    layer.msg('提交失败!' + data.errMsg);
                }
            }
        });
    });
});