/**
 *
 */
$(function () {
    var excelId = getQueryString('excelId');
    var isEdit = excelId ? true : false;
    var intUrl = '/exceladmin/getexcelinitinfo';
    var registerExcelUrl = '/exceladmin/registerExcel';
    var excelInfoUrl = "/exceladmin/getexcelbyid?excelId=" + excelId;
    var editExcelUrl = '/exceladmin/modifyExcel';
    if (!isEdit) {
        getexcelinitinfo();
    } else {
        getexcelinfo();
    }

    function getexcelinfo(excelId) {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var excel = data.excel;
                $('#excel-name').val(excel.excelName);
                $('#class-name').val(excel.classesName);
                $('#excel-term').val(excel.term);
                $('#excel-category').val(excel.major);
                $('#course').val(excel.course);
                $('#class-grade').val(excel.classGrade);
                var tempHtml = '';
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                var tempCourseHtml = '';
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='" + excel.area.areaId + "']").attr("selected", "selected");
            }
        });
    }

    function getexcelinitinfo() {
        $.getJSON(intUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                var tempCourseHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#area').html(tempAreaHtml);
            }
        });
    }

    $('#submit').click(function () {
        var excel = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            excel.excelId = excelId;
        }
        excel.excelName = $('#excel-name').val();
        excel.classesName = $('#class-name').val();
        excel.term = $('#excel-term').val();
        excel.major = $('#excel-category').val();
        excel.course = $('#course').val();
        excel.classGrade = $('#class-grade').val();
        excel.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var formData = new FormData();
        formData.append('excelStr', JSON.stringify(excel));
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
})