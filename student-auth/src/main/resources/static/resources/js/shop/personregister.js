/**
 *
 */
$(function () {
    var userId = getQueryString('userId');
    var isEdit = userId ? true : false;
    var intUrl = '/exceladmin/regist';
    var registerExcelUrl = '/exceladmin/regist';
    var personInfoUrl = "/exceladmin/getpersonbyid?userId=" + userId;
    var editPersonUrl = '/exceladmin/modifyPerson';
    if (!isEdit) {
        getpersoninitinfo();
    } else {
        getpersoninfo();
    }

    function getpersoninfo(userId) {
        $.getJSON(personInfoUrl, function (data) {
            if (data.success) {
                var person = data.person;
                $('#user-name').val(person.userName);
                $('#teacher-name').val(person.teacherName);
                $('#user-password').val(person.userPassword);

            }
        });
    }

    function getpersoninitinfo() {
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
        var person = {};
        if (isEdit) {
            // 若属于编辑，则给excelId赋值
            person.userId = userId;
        }
        person.userName = $('#user-name').val();
        person.teacherName = $('#teacher-name').val();
        person.userPassword = $('#user-password').val();
        person.gender = $('input[name="gender"]:checked').val();
        var options = $("#select option:selected");
        var options2 = $("#select2 option:selected");
        person.userType = options.val();
        person.enabkeStatus = options.val();
        var formData = new FormData();
        formData.append('personStr', JSON.stringify(person));
        $.ajax({
            url: isEdit ? editPersonUrl : registerExcelUrl,
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