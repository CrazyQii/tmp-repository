$(function () {

    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form,
            jquery = layui.jquery,
            layer = layui.layer;

        form.on('submit(submitBtn)', function (obj) {

            $.ajax({
                url: '/exceladmin/login',
                type: 'POST',
                data: $('.layui-form').serialize(),
                success: function (data) {
                    if (data.success) {
                        if (data.type) {
                            layer.msg('登陆成功!', {
                                time: 2000, icon: 1, end: function () {
                                    window.location.href = "/exceladmin/index"
                                }
                            })
                        } else {
                            layer.msg('登陆成功!', {
                                time: 2000, icon: 1, end: function () {
                                    window.location.href = "/exceladmin/indexs"
                                }
                            })
                        }
                    } else {
                        layer.msg(data.errMsg)
                    }
                }
            })
        })
    })
})