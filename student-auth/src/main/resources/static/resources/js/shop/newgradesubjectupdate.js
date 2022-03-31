$(function () {
    var subjectId = getQueryString('subjectId');
    var isEdit = subjectId ? true : false;
    var intUrl = '/exceladmin/getgradesubjectinitinfo';
    var registerExcelUrl = '/exceladmin/addgradesubject';
    var excelInfoUrl = "/exceladmin/getgradesubjectbyid?subjectId=" + subjectId;
    var editExcelUrl = '/exceladmin/modifyGradeSubject';
    if (!isEdit) {
        getexcelinitinfo();
    } else {
        getexcelinfo();
    }

    function getexcelinfo(subjectId) {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var gradeSubject = data.gradeSubject;
                $('#subject-name').val(gradeSubject.subjectName);
                $('#subject-score').val(gradeSubject.subjectScore);
                $('#subject-desc').val(gradeSubject.subjectDesc);
                $('#excel-id').val(gradeSubject.excelId);
                $('#point-id').val(gradeSubject.subjectId);
                var contentIdHtml = $("#test").html("");
                $.each(data.excellist, function (index, value) {
                    //$('#test').append(new Option(value.excelId,value.excelName));
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                //$("select[name='interest-search']").val(gradeSubject.excelId);
                $("#test").val(gradeSubject.excelId);
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
        var gradeSubject = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            gradeSubject.subjectId = subjectId;
        }
        gradeSubject.subjectName = $('#subject-name').val();
        gradeSubject.subjectScore = $('#subject-score').val();
        gradeSubject.subjectDesc = $('#subject-desc').val();

        var options = $("#test option:selected");
        gradeSubject.excelId = options.val();
        var formData = new FormData();
        formData.append('gradeSubjectStr', JSON.stringify(gradeSubject));
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