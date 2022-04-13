<template>
    <div>
        <a-page-header
            style="border: 1px solid rgb(235, 237, 240)"
            title="我要留言"
            sub-title=""
        />
        <a-textarea style="margin: 2rem 0; height: 10rem;" placeholder="请留下您的意见和建议" :rows="4" v-model="value" allowClear/>
        <a-button type="primary" style="float: right" @click="submit">提交</a-button>
    </div>
</template>

<script>
export default {
    name: 'Message',
    props: {
        user_info: {
            type: Object,
        },
    },
    data() {
        return {
            value: ''
        }
    },
    methods: {
        submit() {
            let params = {
                account_id:  this.user_info.id,
                content: this.value,
                phone: this.user_info.phone
            }
            this.$flight_api.post_message(params).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    this.$message.success('留言成功，我们会尽快处理');
                    this.$router.push({
                        path: "/",
                    });
                } else {
                    this.$message.error('留言失败！！！' + res.msg);
                }
            }).catch((err) => {
                this.$message.error('网络连接失败！！！' + err);
            })
        }
    }
}
</script>