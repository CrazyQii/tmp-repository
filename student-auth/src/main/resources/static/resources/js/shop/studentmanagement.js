$(function () {
    var listUrl = '/exceladmin/getstudentlist';
    var addUrl = '/exceladmin/addstudents';
    var deleteUrl = '/exceladmin/removestudents';
    var excelAddUrl = '/exceladmin/excelimport';
    var excelDownLoad = '/exceladmin/downloadstudentsexcel';
    getList();

    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success) {
                    var dataList = data.data;
                    $('.category-wrap').html('');
                    var tempHtml = '';
                    dataList.map(function (item, index) {
                        tempHtml += ''
                            + '<div class="row row-student-grade now">'
                            + '<div class="col-15 reach-student-name">'
                            + item.studentId
                            + '</div>'
                            + '<div class="col-15">'
                            + item.studentName
                            + '</div>'
                            + '<div class="col-15">'
                            + item.classGrade
                            + '</div>'
                            + '<div class="col-15">'
                            + item.workGrade
                            + '</div>'
                            + '<div class="col-15">'
                            + item.expGrade
                            + '</div>'
                            + '<div class="col-15">'
                            + item.lastGrade
                            + '</div>'
                            + '<div class="col-10"><a href="#" class="button delete" data-id="'
                            + item.studentId
                            + '">删除</a></div>'
                            + '</div>';
                    });
                    $('.category-wrap').append(tempHtml);
                }
            });
    }

    $('#new').click(function () {
        var tempHtml = '<div class="row row-student-grade temp">'
            + '<div class="col-15"><input class="category-input studentId" type="number" placeholder="学生学号"></div>'
            + '<div class="col-15"><input class="category-input studentName" type="text" placeholder="学生姓名"></div>'
            + '<div class="col-15"><input class="category-input classGrade" type="number" placeholder="课堂表现"></div>'
            + '<div class="col-15"><input class="category-input workGrade" type="number" placeholder="作业考核"></div>'
            + '<div class="col-15"><input class="category-input expGrade" type="number" placeholder="实验考核"></div>'
            + '<div class="col-15"><input class="category-input lastGrade" type="number" placeholder="期末考核"></div>'
            + '<div class="col-10"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);
    });
    $("#file_form").submit(
        function () {
            //首先验证文件格式
            var fileName = $('#file_input').val();
            if (fileName === '') {
                alert('请选择文件');
                return false;
            }
            var fileType = (fileName.substring(fileName
                .lastIndexOf(".") + 1, fileName.length))
                .toLowerCase();
            if (fileType !== 'xls' && fileType !== 'xlsx') {
                alert('文件格式不正确，excel文件！');
                return false;
            }

            $("#file_form").ajaxSubmit({
                dataType: "json",
                success: function (data, textStatus) {
                    if (data['result'] === 'OK') {
                        console.log('上传文件成功');
                    } else {
                        console.log('文件格式错误');
                    }
                    return false;
                }
            });
            return false;
        });
    $('#submit').click(function () {
        var tempArr = $('.temp');
        var studentList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.studentId = $(item).find('.studentId').val();
            tempObj.studentName = $(item).find('.studentName').val();
            tempObj.classGrade = $(item).find('.classGrade').val();
            tempObj.workGrade = $(item).find('.workGrade').val();
            tempObj.expGrade = $(item).find('.expGrade').val();
            tempObj.lastGrade = $(item).find('.lastGrade').val();
            if (tempObj.studentId) {
                studentList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(studentList),
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                } else {
                    $.toast('提交失败！');
                }
            }
        });
    });


    $("#download").click(function () {
        OutputExce();
    });

    function OutputExce() {
        window.location.href = excelDownLoad;
    }

    function goPoint(status, reachPointid) {
        return '<a href="/exceladmin/reachpointupdate?reachPointid=' + reachPointid
            + '">修改</a>';
    }

    $('.category-wrap').on('click', '.row-student-grade.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();

        });
    $('.category-wrap').on('click', '.row-student-grade.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定么?', function () {
                $.ajax({
                    url: deleteUrl,
                    type: 'POST',
                    data: {
                        studentId: target.dataset.id
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.toast('删除成功！');
                            getList();
                        } else {
                            $.toast('删除失败！');
                        }
                    }
                });
            });
        });

});

