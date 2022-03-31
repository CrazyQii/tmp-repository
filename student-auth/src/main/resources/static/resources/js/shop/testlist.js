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
        , url: '/exceladmin/getexcellist' //数据接口
        , parseData: function (res) { //res 即为原始返回的数据
            console.log(res);
            return {
                "code": 0, //解析接口状态
                "msg": "", //解析提示文本
                "count": 100, //解析数据长度
                "data": res.excelList //解析数据列表
            }
        }
        , title: '用户表'
        , page: true //开启分页
        , toolbar: '#toolbarDemo' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        , totalRow: true //开启合计行
        , cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            , {field: 'excelName', title: '课程名称', width: 400, sort: true, fixed: 'left', totalRowText: '合计：'}
            , {field: 'classesName', title: '班级信息', width: 500}
            , {fixed: 'right', title: '操作', width: 400, align: 'center', toolbar: '#barDemo'}
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
                    content: '/exceladmin/testinput',//这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
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
                content: '/exceladmin/testinput?excelId=' + data.excelId,//这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
                area: ['800px', '600px']
            });
        } else if (layEvent === 'delete') {
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: "/exceladmin/delexcelbyid?excelId=" + data.excelId,
                    dataType: "json",
                    type: "get",
                    success: function (data) {
                        obj.del(); //删除对应行（tr）的DOM结构
                    }
                })
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if (layEvent === 'more') {
            //下拉菜单
            dropdown.render({
                elem: this //触发事件的 DOM 对象
                , show: true //外部事件触发即显示
                , data: [{
                    title: '下载题目表单'
                    , id: 'downgrade'
                }, {
                    title: '下载指标点1'
                    , id: 'downpoint1'
                }, {
                    title: '下载指标点2'
                    , id: 'downpoint2'
                }, {
                    title: '成绩分析表'
                    , id: 'analysis'
                }, {
                    title: '学生成绩excel表'
                    , id: 'student'
                }]
                , click: function (menudata) {
                    if (menudata.id === 'del') {
                        /*layer.confirm('真的删除行么', function(index){
                          obj.del(); //删除对应行（tr）的DOM结构
                          layer.close(index);
                          //向服务端发送删除指令
                        });*/
                    } else if (menudata.id === 'edit') {
                        layer.msg('编辑操作，当前行 ID:' + data.id);
                    } else if (menudata.id === 'downgrade') {
                        window.location.href = "/exceladmin/downGradeSubejctWord?excelId=" + data.excelId;
                        //layer.msg('编辑操作，当前行 ID:'+ data.id);
                    } else if (menudata.id === 'downpoint1') {
                        window.location.href = "/exceladmin/downReachPointWord1?excelId=" + data.excelId;
                        //layer.msg('编辑操作，当前行 ID:'+ data.id);
                    } else if (menudata.id === 'downpoint2') {
                        window.location.href = "/exceladmin/downReachPointWord2?excelId=" + data.excelId;
                        //layer.msg('编辑操作，当前行 ID:'+ data.id);
                    } else if (menudata.id === 'analysis') {
                        window.location.href = "/exceladmin/downAnalysisWord?excelId=" + data.excelId;
                        //layer.msg('编辑操作，当前行 ID:'+ data.id);
                    } else if (menudata.id === 'student') {
                        window.location.href = "/exceladmin/downstudentexe?excelId=" + data.excelId;
                        //layer.msg('编辑操作，当前行 ID:'+ data.id);
                    }
                }
                , style: 'margin-left: -50px; box-shadow: 1px 1px 10px rgb(0 0 0 / 12%);' //设置额外样式
            })
        }
    });


});