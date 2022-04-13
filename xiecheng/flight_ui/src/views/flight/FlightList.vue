<template>
    <div>
        <a-row>
            <a-col :span="6">
                <span>航班日期：</span>
                <a-date-picker
                    format="YYYY-MM-DD"
                    :disabled-date="disabledDate"
                    @change="OnChange"
                    placeholder="选择始发日期"
                />
            </a-col>
            <a-col :span="6">
                <span>始发城市：</span>
                <a-cascader
                    :options="options"
                    expand-trigger="hover"
                    placeholder="选择始发城市"
                    @change="onChangeStartCity"
                />
            </a-col>
            
            <a-col :span="6">
                <span>到达城市：</span>
                <a-cascader
                    :options="options"
                    expand-trigger="hover"
                    placeholder="选择到达城市"
                    @change="onChangeArriveCity"
                />
            </a-col>
            <a-col :span="5">
                <a-button type="primary" @click="submit">
                    查询
                </a-button>
            </a-col>
             
        </a-row>
        <a-row style="margin-top: 1rem">
            <a-col :span="4"><a-statistic title="用户定位" :value="location" /></a-col>
            <a-col :span="5"><a-statistic title="机票日期" :value="date"  /></a-col>
        </a-row>
        <a-table
            :columns="columns"
            :data-source="data"
            :pagination="pagination"
            :loading="loading"
            @change="handleTableChange"
            style="margin-top:2rem;"
        >
            <!-- 公司详情页面 -->
            <template slot="dm" slot-scope="text">
                {{ text }}
            </template>
            <template slot="flight_company" slot-scope="text">
                {{ text }}
            </template>
            <!-- 删除 -->
            <template slot="operation" slot-scope="text, row">
                <a-button
                type="primary"
                @click="buy(row.id)"
                style="margin-right: 2rem"
                >购票</a-button
                >
            </template>
        </a-table>
    </div>
</template>

<script>
import { getTaskTime, getTaskDate, get_interval_time } from '../../utils/common'
import moment from 'moment';
import 'moment/locale/zh-cn';

// 表格表头
const columns = [
    {
    title: "航空公司",
    dataIndex: "flight_company",
    key: 'flight_company',
    width: "12%",
    scopedSlots: { customRender: "flight_company" },
  },
  {
    title: "机型",
    dataIndex: "flight_type",
    key: 'flight_type',
    width: "8%",
  },
  
  {
    title: "始发时间",
    dataIndex: "start_time",
    key: 'start_time',
    width: "15%",
    scopedSlots: { customRender: "start_time" },
  },
  {
    title: "到达时间",
    dataIndex: "end_time",
    key: 'end_time',
    width: "15%",
    scopedSlots: { customRender: "end_time" },
  },
  {
    title: "机票价格(￥)",
    dataIndex: "price",
    key: 'price',
    scopedSlots: { customRender: "price" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    key: 'operation',
    scopedSlots: { customRender: "operation" },
  },
];

// 城市选择
const options = [
    {
        value: 'zhejiang',
        label: '浙江省',
        children: [
        {
            value: 'hangzhou',
            label: '杭州市'
        },
        ],
    },
    {
        value: 'jiangsu',
        label: '江苏',
        children: [
        {
            value: 'nanjing',
            label: '南京'
        },
        ],
    },
]

export default {
    name: 'FlightList',
    props: {
        user_info: {
            type: Object,
        },
    },
    data() {
        return {
            loading: false,
            data: [],
            columns,
            pagination: {
                pageSize: 5,
                current: 1,
            },
            options,
            date: '',
            start_city: '',
            arrive_city: '',
            location: ''
        }
    },
    mounted() {
        this.get_location()
    },
    methods: {
        // 日期选择框配置
        moment,

        get_location() {
            this.$flight_api.get_location().then((res) => {
                console.log(res)
                if (res.code == 200) {
                    this.location = res.data['province'] + res.data['city']
                } else {
                    this.$message.error('ip地理位置定位失败! ' + res.msg);
                }
            })
        },

        /**
         * 表格分页操作
         */
        handleTableChange(pagination) {
            const pager = { ...this.pagination };
            pager.current = pagination.current;
            this.pagination = pager;
        },

        disabledDate(current) {
            // Can not select days before today and today
            return current && current <= moment().endOf('day');
        },

        /**
         * 获取航班班次
         */
        get_flight_list(param) {
            param = { 
                from_pos: param['from_pos'],
                start_time: param['start_time'],
                pagenum: param['pagenum'], 
                pagelimit: param['pagelimit'] 
            }
            this.loading = true
            this.$flight_api.get_flight_list(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    for (let index = 0; index < res.data['flights'].length; index++) {
                        res['data']['flights'][index]['start_time'] = getTaskTime(res['data']['flights'][index]['start_time'])
                        res['data']['flights'][index]['end_time'] = getTaskTime(res['data']['flights'][index]['end_time'])
                        res['data']['flights'][index]['interval'] = get_interval_time(res['data']['flights'][index]['start_time'], res['data']['flights'][index]['end_time'])
                    }
                    this.data = res['data']['flights']
                } else {
                    this.$message.error('请求数据失败! ' + res.msg);
                }
                this.loading = false
            })
        },

        // 点击始发城市下拉框
        onChangeStartCity(e) {
            this.start_city = e[e.length - 1]
        },
        
        // 点击到达城市下拉框
        onChangeArriveCity(e) {
            this.arrive_city = e[e.length - 1]
        },

        // 日期选择框
        OnChange(e) {
            if (e == null) {
                this.date = null
            } else {
                this.date = getTaskDate(String(e._d))
            }
        },

        // 点击搜索
        submit() {
            // 数据校验
            if (this.date == '') {
                this.$message.error('请选择机票日期');
                return
            }
            if (this.start_city == '') {
                this.$message.error('请选择始发城市');
                return
            }
            if (this.arrive_city == '') {
                this.$message.error('请选择到达城市');
                return
            }

            this.loading = true
            let param = {
                'from_pos': this.start_city,
                'to_pos': this.arrive_city,
                'start_time': this.date,
                'pagenum': 1,
                'pagelimit': 10
            }
            this.$flight_api.get_flight_list(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    if (res.data.flights.length == 0) { // 没有检索到数据
                        this.$message.error('很抱歉，当日没有两地机票！');
                    } else {
                        for (let index = 0; index < res.data['flights'].length; index++) {
                            res['data']['flights'][index]['start_time'] = getTaskTime(res['data']['flights'][index]['start_time'])
                            res['data']['flights'][index]['end_time'] = getTaskTime(res['data']['flights'][index]['end_time'])
                            res['data']['flights'][index]['interval'] = get_interval_time(res['data']['flights'][index]['start_time'], res['data']['flights'][index]['end_time'])
                        }
                        this.data = res['data']['flights']
                    }                    
                } else {
                    this.$message.error('请求数据失败! ' + res.msg);
                }
                this.loading = false
            })
        },

        // 跳转购买页面
        buy(id) {
            // 跳转路由
            this.$router.push({
                path: `/flight-detail/${id}`,
            });
        },
    }
}
</script>