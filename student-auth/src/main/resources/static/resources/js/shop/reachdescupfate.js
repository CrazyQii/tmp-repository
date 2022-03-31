$(function () {
    //var isEdit = reachPointId ? true : false;
    var intUrl = '/exceladmin/getexcelinitinfo';
    var excelInfoUrl = '/exceladmin/getreachdesc';
    var editExcelUrl = '/exceladmin/modifyreachdesc';
    getexcelinfo();

    function getexcelinfo() {
        $.getJSON(excelInfoUrl, function (data) {
            if (data.success) {
                var reachDesc = data.reachDesc;
                $('#reach-record').val(reachDesc.reachRecord);
                $('#reach-change').val(reachDesc.reachChange);
                $('#reach-resources').val(reachDesc.reachResources);
                $('#excel-id').val(reachDesc.excelId);
                $('#word-id').val(reachDesc.wordId);
            }
        });
    }

    $('#submit').click(function () {
        var reachDesc = {};
        //if (isEdit) {
        // 若属于编辑，则给excelId赋值
        //reachPoint.reachPointId = reachPointId;
        //}
        reachDesc.excelId = $('#excel-id').val();
        reachDesc.wordId = $('#word-id').val();
        reachDesc.reachRecord = $('#reach-record').val();
        reachDesc.reachChange = $('#reach-change').val();
        reachDesc.reachResources = $('#reach-resources').val();
        var formData = new FormData();
        formData.append('reachDescStr', JSON.stringify(reachDesc));

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