<template>
    <div>
        <a-table 
            :columns="columns" 
            :data-source="orders" 
            bordered 
            :pagination="pagination"
            :loading="loading" 
            @change="handleTableChange">
            <template slot="title">账单列表</template>
            <template slot="pay_ready" slot-scope="text">
                <a-tag v-if="text === 0" :color="'volcano'">未支付</a-tag>
                <a-tag v-if="text === 1" :color="'green'">已支付</a-tag>
                <a-tag v-if="text === 2">已过期</a-tag>
                <a-tag v-if="text === 3">已退票</a-tag>
            </template>
            <template slot="rest_time" slot-scope="text, record">
                <a-statistic-countdown
                    v-if="record.pay_ready === 0"
                    :value="record.pay_end_time"
                />
            </template>
            <template slot="operation" slot-scope="text, record">
                <a-button v-if="record.pay_ready === 0" type="primary" size="small" @click="pay(record)">支付</a-button>
                <a-button v-if="record.pay_ready === 0" type="danger" style="margin-left: 2rem" size="small" @click="cancelOrder(record)">取消订单</a-button>
                <a-button v-if="record.pay_ready === 1" type="primary" size="small" @click="cancelOrder(record)">退票</a-button>
            </template>
        </a-table>
    </div>
</template>

<script>
import { getTaskTime } from '../../utils/common'



const columns = [
  {
    title: "订单编号",
    dataIndex: "id",
    key: 'id',
  },
  {
    title: "始发城市",
    dataIndex: "flight.from_pos",
    key: 'from_pos',
    width: "10%",
  },
  {
    title: "目的地城市",
    dataIndex: "flight.to_pos",
    key: 'to_pos',
    width: "10%",
  },
  {
    title: "始发时间",
    dataIndex: "flight.start_time",
    key: 'start_time',
    scopedSlots: { customRender: "start_time" },
    sorter: (a, b) => Date.parse(a.flight.start_time) - Date.parse(b.flight.start_time),
  },
  {
    title: "到达时间",
    dataIndex: "flight.end_time",
    key: 'end_time',
    scopedSlots: { customRender: "end_time" },
    sorter: (a, b) => Date.parse(a.flight.end_time) - Date.parse(b.flight.end_time),
  },
  {
    title: "机票价格(￥)",
    dataIndex: "money",
    key: 'money',
    scopedSlots: { customRender: "money" },
    sorter: (a, b) => a.money - b.money,
  },
  {
    title: "剩余支付时间",
    key: 'rest_time',
    scopedSlots: { customRender: "rest_time" },
  },
  {
    title: "订单状态",
    dataIndex: "pay_ready",
    key: 'pay_ready',
    scopedSlots: { customRender: "pay_ready" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    key: 'operation',
    scopedSlots: { customRender: "operation" },
  },
];


export default {
    name: 'FlightOrder',
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
            orders: []
        }
    }, 
    mounted() {
        this.get_user_info()
    },
    methods: {

        // 获取用户信息
        get_user_info() {
            if (this.user_info.id == null) {
                this.$user_api.user_info().then((res) => {
                    if (res.code == 200) {
                        this.user_info = res.data
                        this.get_order_list(this.user_info.id)
                    }
                })
            } else {
                this.get_order_list(this.user_info.id)
            }
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
         * 获取订单列表
         */
        get_order_list(user_id) {
            this.loading = true
            let param = {
                account_id: user_id
            }
            console.log(param)
            this.$flight_api.get_order_list(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                   this.orders = res.data.orders
                   for (let i = 0; i < this.orders.length; i++) {
                        this.orders[i].pay_end_time = getTaskTime(this.orders[i].pay_end_time)
                        if (this.orders[i].pay_ready == 0) {
                            this.quertPay(i, this.orders[i].id)
                        }
                   }     
                } else {
                    this.$message.error('请求数据失败! 请回退首页 ' + res.msg);
                }
                this.loading = false
            })
        },

        // 点击支付按，跳转外链
        pay(record) {
            window.open(`http://localhost:5000/order/pay?order_id=${record.id}&money=${record.money}&name=航班--${record.flight.flight_type}`,"_blank");
            this.loading = true
            this.$message.success('支付完成，请刷新页面')
        },

        // 查询实际支付情况
        quertPay(i, order_id) {
            let params = {
                order_id: order_id,
            }
            this.$pay_api.query_pay(params).then((res) => {
                console.log(res)
                if (res.code == 200) {
                   this.orders[i].pay_ready = res.data.pay_ready
                } else {
                    this.$message.error('支付失败！！！' + res.msg);
                }
            })
        },

        // 点击取消支付订单按钮
        cancelOrder(record) {
            this.loading = true
            let param = {
                order_id: record.id,
                account_id: this.user_info.id,
                pagelimit: this.pagination.pageSize,
                pagenum: this.pagination.current
            }
            console.log(param)
            this.$flight_api.remove_order(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                   this.orders = res.data.orders    
                   this.$message.error('取消订单成功');  
                } else {
                    this.$message.error('请求数据失败!' + res.msg);
                }
                this.loading = false
            })
        }
    }
}
</script>