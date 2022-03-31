$(function () {
    var listUrl = '/exceladmin/getreachpointlist';
    var addUrl = '/exceladmin/addreachpoints';
    var deleteUrl = '/exceladmin/removereachpoints';
    getList();

    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var tempHtml = '';
                dataList.map(function (item, index) {
                    tempHtml += ''
                        + '<div class="row row-reach-point now">'
                        + '<div class="col-33 reach-point-name">'
                        + item.pointId
                        + '</div>'
                        + '<div class="col-33">'
                        + item.reachScore
                        + '</div>'
                        + '<div class="col-15">'
                        + goPoint(item.enableStatus, item.reachPointId)
                        + '</div>'
                        + '<div class="col-15"><a href="#" class="button delete" data-id="'
                        + item.pointId
                        + '">删除</a></div>'
                        + '</div>';
                });
                $('.category-wrap').append(tempHtml);
            }
        });
    }

    $('#new').click(function () {
        var tempHtml = '<div class="row row-reach-point temp">'
            + '<div class="col-33"><input class="category-input category" type="number" placeholder="指标点id"></div>'
            + '<div class="col-33"><input class="category-input score" type="number" placeholder="达成值"></div>'
            + '<div class="col-15"><a href="#" class="button change">修改</a></div>'
            + '<div class="col-15"><a href="#" class="button delete">删除</a></div>'
            + '</div>';
        $('.category-wrap').append(tempHtml);
    });
    $('#submit').click(function () {
        var tempArr = $('.temp');
        var reachPointList = [];
        tempArr.map(function (index, item) {
            var tempObj = {};
            tempObj.pointId = $(item).find('.category').val();
            tempObj.reachScore = $(item).find('.score').val();
            if (tempObj.pointId && tempObj.reachScore) {
                reachPointList.push(tempObj);
            }
        });
        $.ajax({
            url: addUrl,
            type: 'POST',
            data: JSON.stringify(reachPointList),
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

    function goPoint(status, reachPointid) {
        return '<a href="/exceladmin/reachpointupdate?reachPointid=' + reachPointid
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
});