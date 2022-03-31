$(function () {
    var subjectId = getQueryString('subjectid');
    var isEdit = subjectId ? true : false;
    var intUrl = '/exceladmin/getexcelinitinfo';
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
                $('#class-name').val(gradeSubject.subjectName);
                $('#work-grade').val(gradeSubject.subjectScore);
                $('#subject-desc').val(gradeSubject.subjectDesc);
                $('#user-id').val(gradeSubject.userId);
                $('#point-id').val(gradeSubject.subjectId);
            }
        });
    }

    $('#submit').click(function () {
        var gradeSubject = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            gradeSubject.subjectId = subjectId;
        }
        gradeSubject.userId = $('#user-id').val();
        gradeSubject.subjectId = $('#point-id').val();
        gradeSubject.className = $('#class-name').val();
        gradeSubject.workGrade = $('#work-grade').val();
        gradeSubject.subjectDesc = $('#subject-desc').val();
        var formData = new FormData();
        formData.append('gradeSubjectStr', JSON.stringify(gradeSubject));
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