$(function () {
    getlist();

    function getlist(e) {
        $.ajax({
            url: "/exceladmin/getexcellist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleList(data.excelList);
                    handleUser(data.user);
                }
            }
        });
    }

    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function handleList(data) {
        var html = '';
        data.map(function (item, index) {
            html += '<div class="row row-excel"><div class="col-40">'
                + item.excelName + '</div><div class="col-40">'
                + item.classesName
                + '</div><div class="col-20">'
                + goExcel(item.enableStatus, item.excelId) + '</div></div>';

        });
        $('.excel-wrap').html(html);
    }


    function goExcel(status, id) {
        return '<a href="/exceladmin/excelmanagement?excelId=' + id
            + '">进入</a>';
    }
});