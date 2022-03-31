$(function () {
    var analysisId = getQueryString('analysisId');
    var isEdit = analysisId ? true : false;
    var intUrl = '/exceladmin/getanalinitinfo';
    var registerExcelUrl = '/exceladmin/addanalysis';
    var excelInfoUrl = "/exceladmin/getanalysisbyid?analysisId=" + analysisId;
    var editExcelUrl = '/exceladmin/modifyAnalysis';
    if (!isEdit) {
        getexcelinitinfo();
    } else {
        getexcelinfo();
    }

    function getexcelinfo(analysisId) {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var analysis = data.analysis;
                $('#assessment-analysis').val(analysis.assessmentAnalysis);
                $('#term-analysis').val(analysis.termAnalysis);
                $('#course-analysis').val(analysis.courseAnalysis);
                $('#graduation-analysis').val(analysis.graduationAnalysis);
                $('#edcation-analysis').val(analysis.edcationAnalysis);
                $('#excel-id').val(analysis.excelId);
                $('#point-id').val(analysis.analysisId);
                var contentIdHtml = $("#test").html("");
                $.each(data.excellist, function (index, value) {
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                $("#test").val(analysis.excelId);
                layui.form.render('select');
            }
        });
    }

    function getexcelinitinfo() {
        $.ajax({
            url: intUrl,
            dataType: "json",
            type: "get",
            success: function (data) {
                console.log(data);
                var contentIdHtml = $("#test").html("");
                contentIdHtml.append('<option value>请选择</option>');
                $.each(data.excelList, function (index, value) {
                    //$('#test').append(new Option(value.excelId,value.excelName));
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                layui.form.render("select");
            }
        })

    }

    $('#submit').click(function () {
        var analysis = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            analysis.analysisId = analysisId;
        }
        analysis.excelId = $('#excel-id').val();
        analysis.assessmentAnalysis = $('#assessment-analysis').val();
        analysis.termAnalysis = $('#term-analysis').val();
        analysis.courseAnalysis = $('#course-analysis').val();
        analysis.graduationAnalysis = $('#graduation-analysis').val();
        analysis.edcationAnalysis = $('#edcation-analysis').val();

        var options = $("#test option:selected");
        analysis.excelId = options.val();
        var formData = new FormData();
        formData.append('analysisStr', JSON.stringify(analysis));
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