<template>
    <div>
        <a-steps>
            <a-step status="finish" title="信息确认">
            <a-icon slot="icon" type="form" />
            </a-step>
            <a-step status="finish" title="待支付">
            <a-icon slot="icon" type="transaction" />
            </a-step>
            <a-step status="wait" title="支付成功">
                <a-icon slot="icon" type="smile-o" />
            </a-step>
        </a-steps>

        <a-page-header
            style="border: 1px solid rgb(235, 237, 240); margin-top: 2rem;"
            title="订单支付中，若支付完成，请刷新页面"
        />
        
    </div>
</template>

<script>
export default {
    name: 'WaitPay',
    data() {
        return {
            id: this.$route.params.id,
        }
    },
    mounted() {
        this.get_order_detail()
    },
    methods: {
        get_order_detail() {
            let params = {
                'order_id': this.id
            }
            this.$pay_api.query_pay(params).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    if (res.data.pay_ready == 1) {
                        this.$router.push({
                            path: `/pay-success`,
                        })
                    }
                } else {
                    this.$message.error("获取订单信息失败:" + res.msg);
                }
            })
        }
    }
}
</script>