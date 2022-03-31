$(function () {
    var intUrl = '/exceladmin/getstudentinitinfo2';
    var registerExcelUrl = '/exceladmin/addstudents';
    var editExcelUrl = '/exceladmin/modifyStudent';
    getexcelinitinfo();

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

    layui.use(['upload', 'element', 'layer'], function () {
        var student = {};
        var $ = layui.jquery
            , upload = layui.upload
            , element = layui.element
            , layer = layui.layer;
        upload.render({
            elem: '#test8'
            , url: '/exceladmin/importExcel' //改成您自己的上传接口
            , accept: 'file'
            , auto: false
            //,multiple: true
            , bindAction: '#test9'
            , before: function () {
                var options = $("#test option:selected");
                this.url = "/exceladmin/importExcel?excelId=" + options.val();
            }
            , error: function (index, upload) {
            }
            , done: function (res) {
                if (res.success) {
                    layer.msg('上传成功');
                    console.log(res)
                } else {
                    layer.msg('上传失败');
                    console.log(res)
                }
            }
        });
    });


});