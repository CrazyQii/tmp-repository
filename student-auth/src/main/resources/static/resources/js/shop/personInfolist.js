/**
 *
 */

//加载模块
layui.use(function () { //亦可加载特定模块：layui.use(['layer', 'laydate', function(){
                        //得到各种内置组件
    var table = layui.table //表格
        , dropdown = layui.dropdown //下拉菜单
        , layer = layui.layer //弹层
    //执行一个 table 实例
    table.render({
        elem: '#demo'
        , height: 620
        , url: '/exceladmin/getpersonInfolist' //数据接口
        , parseData: function (res) { //res 即为原始返回的数据
            console.log(res);
            return {
                "code": 0, //解析接口状态
                "msg": "", //解析提示文本
                "count": 100, //解析数据长度
                "data": res.personList //解析数据列表
            }
        }
        , title: '用户表'
        , page: true //开启分页
        , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , totalRow: true //开启合计行
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'userName', title: '用户账号', width: 200, sort: true, fixed: 'left', totalRowText: '合计：'}
            , {field: 'teacherName', title: '教师姓名', width: 200}
            , {field: 'userPassword', title: '教师密码', width: 200}
            , {field: 'userType', title: '账号权限', width: 200}
            , {field: 'gender', title: '教师性别', width: 100}
            , {field: 'enabkeStatus', title: '是否禁用', width: 100}
            , {fixed: 'right', title: '操作', width: 200, align: 'center', toolbar: '#barDemo'}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data; //获取选中的数据
        switch (obj.event) {
            case 'add':
                layer.msg('添加');
                layer.open({
                    type: 2,
                    content: '/exceladmin/personregister',//这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                    area: ['800px', '600px']
                });
                break;
            case 'update':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else if (data.length > 1) {
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：' + checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        }
        ;
    });

    //监听行工具事件
    table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'detail') {
            layer.open({
                type: 2,
                content: '/exceladmin/personregister?userId=' + data.userId,//这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                area: ['800px', '600px']
            });
        } else if (layEvent === 'more') {
            $.ajax({
                url: '/exceladmin/removeteacher',
                type: 'POST',
                data: {userId: data.userId},
                success: function (data) {
                    if (data.success) {
                        obj.del(); //删除对应行（tr）的DOM结构
                        layer.close(index);
                        //向服务端发送删除指令
                        layer.msg("删除成功", {icon: 6});
                    } else {
                        layer.msg("删除失败", {icon: 5});
                    }
                }
            });
        }
    });


});