<template>
    <div>
        <a-steps>
            <a-step status="finish" title="信息确认">
            <a-icon slot="icon" type="form" />
            </a-step>
            <a-step status="wait" title="待支付">
            <a-icon slot="icon" type="transaction" />
            </a-step>
            <a-step status="wait" title="支付成功">
                <a-icon slot="icon" type="smile-o" />
            </a-step>
        </a-steps>

        <a-descriptions title="航班信息确认" layout="vertical" bordered style="margin-top: 2rem;">
            <a-descriptions-item label="航空公司">
            {{ flight.flight_company }}
            </a-descriptions-item>
            <a-descriptions-item label="起始地">
            {{ flight.from_pos }}
            </a-descriptions-item>
            <a-descriptions-item label="起飞时间">
            {{ flight.start_time }}
            </a-descriptions-item>
            <a-descriptions-item label="飞机型号">
            {{ flight.flight_type }}
            </a-descriptions-item>
            <a-descriptions-item label="到达地">
            {{ flight.to_pos }}
            </a-descriptions-item>
            <a-descriptions-item label="到达时间">
            {{ flight.end_time }}
            </a-descriptions-item>
        </a-descriptions>

        <a-row type="flex" justify="end" style="margin: 2rem 0rem;">
            <a-col :span="18">
                乘客信息列表
            </a-col>
            <a-col :span="4">
                <a-button
                    type="info"
                    @click="addPassenger()"
                    style="margin-right: 2rem"
                    >添加乘客信息</a-button
                >
            </a-col>
            
        </a-row>
        <a-list
            :loading="loading"
            item-layout="horizontal"
            :data-source="users"
        >
            <a-list-item slot="renderItem" slot-scope="item" style="padding: 0 5rem;">
                <a-list-item-meta>
                    <span slot="title">
                        <span style="margin-left: 5rem; width: 10rem;">{{ '姓名：' + item.name }}</span>
                        <span style="margin-left: 5rem; width: 10rem;">{{ ' 身份证: ' + item.id_card }}</span>
                        <span style="margin-left: 5rem; width: 10rem;">{{ ' 手机号码：' + item.phone }}</span>
                    </span>
                </a-list-item-meta>
                <a-button  type="danger" shape="round" size="small"  @click="deleted(item.id_card)">删除</a-button>
            </a-list-item>
        </a-list>
        <a-row type="flex" justify="end" style="margin-top: 2rem;">
            <a-col :span="4">
                <span style="font-weight: 700; font-size: 20px; ">价格:<span style="color: red; margin-left: 1rem;">￥{{ flight.price * this.users.length }}</span></span>
            </a-col>
            <a-col :span="4">
                <a-button
                    type="primary"
                    @click="confirm()"
                    style="margin-right: 2rem"
                    >确认</a-button
                >
            </a-col>
        </a-row>

        <a-drawer
            v-if="reFresh"
            title="乘客信息登记"
            :width="720"
            :visible="visible"
            :body-style="{ paddingBottom: '80px' }"
            @close="onClose"
        >
            <PassengerInfo :user_info="user_info" :flight_id="id"></PassengerInfo>   
        </a-drawer>


        <a-modal
            :visible="modelVisible"
            title="确认弹窗"
            ok-text="确认"
            cancel-text="取消"
            @ok="handleOk"
            @cancel="cancelOk"
            >
            <p>是否确认购买机票</p>
        </a-modal>
    </div>
</template>

<script>
import { getTaskTime } from '../../utils/common'
import PassengerInfo from "@/components/PassengerInfo.vue";

export default {
    name: 'FlightDetail',
    components: {
        PassengerInfo
    },
    data() {
        return {
            id: this.$route.params.id,
            flight: {},
            users: [],
            visible: false,
            reFresh: true,
            user_info: {},
            loading: false,
            modelVisible: false
        }
    }, 
    mounted() {
        this.get_userinfo()
        this.get_flight(this.id)
    },
    methods: {
        // 获取用户信息
        get_userinfo() {
            this.$user_api.user_info().then((res) => {
                // 请求后端数据
                if (res.code == 200) {
                    this.user_info = res.data;
                    console.log(this.user_info);
                    this.get_passengers()
                } else {
                    this.$message.error("用户信息获取失败:" + res.msg);
                    localStorage.removeItem("token");
                    this.$router.push({
                        path: `/login`,
                    });
                }
            });
        },
        
        // 返回航班详细信息
        get_flight(flight_id) {
            let param = { 
                'flight_id': flight_id
            }
            this.loading = true
            this.$flight_api.get_flight(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    this.flight = res.data
                    this.flight.start_time = getTaskTime(this.flight.start_time)
                    this.flight.end_time = getTaskTime(this.flight.end_time)
                } else {
                    this.$message.error('请求数据失败! ' + res.msg);
                }
                this.loading = false
                
            })
        },

        // 获取乘客列表
        get_passengers() {
            this.loading = true
            let param = {
                flight_id: this.id,
                account_id: this.user_info.id
            }
            console.log(param)
            this.$flight_api.get_passenger(param).then((res) => {
                // 请求后端数据
                console.log(res)
                if (res.code == 200) {
                    this.users = res.data;
                } else {
                    this.$message.error("乘客信息获取失败:" + res.msg);
                }
                this.loading = false
            });
        }, 

        // 弹窗确认信息
        confirm() {
            if (this.users.length == 0) {
                this.$message.error('请添加至少一位乘客');
            } else {
                this.modelVisible = true
            }
        }, 

        // 关闭弹窗
        cancelOk() {
            this.modelVisible = false;
        },

        // 生成订单
        handleOk() {
            this.loading = true
            let param = {
                money: this.flight.price * this.users.length,
                flight_id: this.id,
                account_id: this.user_info.id
            }

            this.$flight_api.post_order(param).then((res) => {
                // 请求后端数据
                console.log(res)
                if (res.code == 200) {
                    this.$message.success("生成订单成功");
                    // 跳转路由
                    this.$router.push({
                        path: `/wait-pay/${res.data.id}`,
                    });
                    window.open(`http://localhost:5000/order/pay?order_id=${res.data.id}&money=${res.data.money}&name=航班--${this.flight.flight_type}`,"_blank");
                } else {
                    this.$message.error("生成订单失败:" + res.msg);
                }
                this.loading = false
            });
            
        },

        // 添加客户信息
        addPassenger() {
            this.visible = true
            this.reFresh = false
            this.$nextTick(() => {
                this.reFresh = true
            })
        },

        // 关闭抽屉界面
        onClose() {
            this.visible = false
        },

        // 删除乘客
        deleted(id_card) {
            this.loading = true
            let param = {
                id_card: id_card,
                account_id: this.user_info.id
            }
            console.log(param)
            this.$flight_api.remove_passenger(param).then((res) => {
                // 请求后端数据
                console.log(res)
                if (res.code == 200) {
                    this.users = res.data;
                    this.$message.success("删除乘客信息成功！");
                } else {
                    this.$message.error("删除乘客信息失败:" + res.msg);
                }
                this.loading = false
            });
        }
    }
}

</script>