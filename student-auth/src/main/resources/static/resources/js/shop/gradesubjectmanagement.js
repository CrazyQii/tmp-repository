$(function () {
    var listUrl = '/exceladmin/getgradesubjectlist';
    var addUrl = '/exceladmin/addgradesubject';
    var deleteUrl = '/exceladmin/removegradesubject';
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
                            + '<div class="row row-reach-point now">'
                            + '<div class="col-33 reach-point-name">'
                            + item.subjectId
                            + '</div>'
                            + '<div class="col-33">'
                            + item.subjectScore
                            + '</div>'
                            + '<div class="col-15">'
                            + goPoint(item.enableStatus, item.subjectId)
                            + '</div>'
                            + '<div class="col-15"><a href="#" class="button delete" data-id="'
                            + item.subjectId
                            + '">删除</a></div>'
                            + '</div>';
                    });
                    $('.category-wrap').append(tempHtml);
                }
            });
    }

    $('#new').click(function () {
        var tempHtml = '<div class="row row-reach-point temp">'
            + '<div class="col-33"><input class="category-input category" type="number" placeholder="题型"></div>'
            + '<div class="col-33"><input class="category-input score" type="number" placeholder="课程目标(分数)"></div>'
            + '<div class="col-15"><a href="#" class="button change">添加简要描述</a></div>'
            + '<div class="col-15"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);
    });
    $('#submit').click(function () {
        var tempArr = $('.temp');
        var gradeSubjectList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.subjectName = $(item).find('.category').val();
            tempObj.subjectScore = $(item).find('.score').val();
            if (tempObj.subjectName && tempObj.subjectScore) {
                gradeSubjectList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(gradeSubjectList),
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

    function goPoint(status, subjectid) {
        return '<a href="/exceladmin/gradesubjectupdate?subjectid=' + subjectid
            + '">修改</a>';
    }

    $('.category-wrap').on('click', '.row-reach-point.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();

        });
    $('.category-wrap').on('click', '.row-reach-point.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定么?', function () {
                $.ajax({
                    url: deleteUrl,
                    type: 'POST',
                    data: {
                        pointId: target.dataset.id
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
})