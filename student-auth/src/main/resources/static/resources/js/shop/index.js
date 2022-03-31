$(function () {
    var userId = getQueryString('userId');
    var personInfoUrl = '/exceladmin/getpersonmanagementinfo?userId=' + userId;
    getlist();

    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    function getlist(e) {
        $.ajax({
            url: "/exceladmin/getpersonInfolist",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    handleUser(data.user);
                }
            }
        });
    }


    $.getJSON(personInfoUrl, function (data) {
        if (data.redirect) {

            window.location.href = data.url;
        } else {
            if (data.userId != undefined && data.userId != null) {
                userId = data.userId;
                $('#user-name').text(data.teacherName);
            }
            $('#excelInfo')
                .attr('href', '/exceladmin/testinput?userId=' + userId);
            //$('#excelInfo')
            //.attr('href', '/exceladmin/exceloperation?excelId=' + excelId);
        }
    });
});

