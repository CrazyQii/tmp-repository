$(function () {
    var exeStudentId = getQueryString('exeStudentId');
    var isEdit = exeStudentId ? true : false;
    var intUrl = '/exceladmin/getstudentinitinfo';
    var registerExcelUrl = '/exceladmin/addstudents';
    var excelInfoUrl = "/exceladmin/getstudentbyid?exeStudentId=" + exeStudentId;
    var editExcelUrl = '/exceladmin/modifyStudent';
    if (!isEdit) {
        getexcelinitinfo();
    } else {
        getexcelinfo();
    }

    function getexcelinfo(exeStudentId) {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var student = data.student;
                $('#student-id').val(student.studentId);
                $('#student-name').val(student.studentName);
                $('#class-grade').val(student.classGrade);
                $('#work-grade').val(student.workGrade);
                $('#exp-grade').val(student.expGrade);
                $('#last-grade').val(student.lastGrade);
                $('#excel-id').val(student.excelId);
                $('#point-id').val(student.exeStudentId);
                var contentIdHtml = $("#test").html("");
                $.each(data.excellist, function (index, value) {
                    contentIdHtml.append("<option value=" + value.excelId + ">" + value.excelName + "</option>");
                });
                $("#test").val(student.excelId);
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
        var student = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            student.exeStudentId = exeStudentId;
        }
        student.excelId = $('#excel-id').val();
        student.pointIdOne = $('#point-id-one').val();
        student.studentId = $('#student-id').val();
        student.studentName = $('#student-name').val();
        student.classGrade = $('#class-grade').val();
        student.workGrade = $('#work-grade').val();
        student.expGrade = $('#exp-grade').val();
        student.lastGrade = $('#last-grade').val();
        var options = $("#test option:selected");
        student.excelId = options.val();
        var formData = new FormData();
        formData.append('studentStr', JSON.stringify(student));
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