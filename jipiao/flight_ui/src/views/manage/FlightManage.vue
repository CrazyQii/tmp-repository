<template>
    <div>
        <a-page-header
            style="border: 1px solid rgb(235, 237, 240)"
            title="航班信息管理"
            sub-title=""
        />

        <a-row style="margin-top: 2rem;">
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
                <a-button type="primary" @click="addFlight" style="margin-left: 2rem;">
                    添加航班
                </a-button>
            </a-col>
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
                type="danger"
                @click="del(row.id)"
                style="margin-right: 2rem"
                >删除</a-button
                >
                <a-button
                    type="primary"
                    @click="edit(row)"
                    style="margin-right: 2rem"
                    >编辑</a-button
                >
            </template>
        </a-table>

        <a-drawer
            v-if="reFresh"
            title="机票信息"
            :width="720"
            :visible="visible"
            :body-style="{ paddingBottom: '80px' }"
            @close="onClose"
        >
            <FlightInfo :flight="flight"></FlightInfo>   
        </a-drawer>
    </div>
</template>

<script>
import { getTaskTime, getTaskDate, get_interval_time, city_options } from '../../utils/common'
import moment from 'moment';
import 'moment/locale/zh-cn';
import FlightInfo from "@/components/FlightInfo.vue";

const columns = [
  {
    title: "始发城市",
    dataIndex: "from_pos",
    key: 'from_pos',
  },
  {
    title: "目的地城市",
    dataIndex: "to_pos",
    key: 'to_pos'
  },
  {
    title: "航空公司",
    dataIndex: "flight_company",
    key: 'flight_company',
    width: "15%",
    scopedSlots: { customRender: "flight_company" },
  },
  {
    title: "机型",
    dataIndex: "flight_type",
    key: 'flight_type',
    width: "10%",
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

export default {
    name: 'FlightManage',
    components: {
        FlightInfo
    },
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
            options: city_options,
            date: '',
            start_city: '',
            arrive_city: '',
            visible: false,
            flight: {},
            reFresh: true,
        }
    },
    mounted() {
        let param = { 
            'pagenum': this.pagination.current,
            'pagelimit': this.pagination.pageSize
        }
        this.get_flight(param)
    },
    methods: {
        // 日期选择框配置
        moment,

        disabledDate(current) {
            // Can not select days before today and today
            return current && current <= moment().endOf('day');
        },

        /**
         * 表格分页操作
         */
        handleTableChange(pagination) {
            const pager = { ...this.pagination };
            pager.current = pagination.current;
            this.pagination = pager;
        },

        /**
         * 初始化所有航班
         */
        get_flight(params) {
            // 数据校验
            if (this.date != '') {
                params['start_time'] = this.date
            }
            if (this.start_city != '') {
                params['from_pos'] = this.start_city
            }
            if (this.arrive_city != '') {
               params['to_pos'] = this.arrive_city
            }
            this.loading = true
            this.$flight_api.get_flight_list(params).then((res) => {
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
            let params = { 
                'pagenum': this.pagination.current,
                'pagelimit': this.pagination.pageSize
            }
            this.get_flight(params)
        },

        // 编辑航班
        edit(row) {
            this.visible = true
            this.flight = row
            this.reFresh = false
            this.$nextTick(() => {
                this.reFresh = true
            })
        },

        // 添加航班
        addFlight() {
            this.visible = true
            this.reFresh = false
            this.flight = {}
            this.$nextTick(() => {
                this.reFresh = true
            })
        },

        onClose() {
            this.visible = false
        },

        // 删除航班
        del(id) {
            // 跳转路由
            console.log(id)
            let params = {
                'flight_id': id
            }
            if (this.date != '') {
                params['start_time'] = this.date
            }
            if (this.start_city != '') {
                params['from_pos'] = this.start_city
            }
            if (this.arrive_city != '') {
               params['to_pos'] = this.arrive_city
            }
            this.$flight_api.remove_flight(params).then((res) => {
                if (res.code == 200) {
                    this.$message.success('删除成功');
                    for (let index = 0; index < res.data['flights'].length; index++) {
                        res['data']['flights'][index]['start_time'] = getTaskTime(res['data']['flights'][index]['start_time'])
                        res['data']['flights'][index]['end_time'] = getTaskTime(res['data']['flights'][index]['end_time'])
                        res['data']['flights'][index]['interval'] = get_interval_time(res['data']['flights'][index]['start_time'], res['data']['flights'][index]['end_time'])
                    }
                    this.data = res['data']['flights']
                } else {
                    this.$message.error('删除数据失败! ' + res.msg);
                }
            })
        },
    }
}
</script>