$(function () {
    $('#log-out').click(function () {
        // 清除session
        $.ajax({
            url: "/exceladmin/logout",
            type: "post",
            async: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    var usertype = $("#log-out").attr("usertype");
                    // 清除成功后退出到登录界面
                    window.location.href = "/exceladmin/login?usertype=" + usertype;
                    return false;
                }
            },
            error: function (data, error) {
                alert('登出失败');
            }
        });
    });
});