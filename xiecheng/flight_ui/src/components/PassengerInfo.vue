<template>
    <div style="width: 60%; margin: 0 auto">
        <a-form :form="form" @submit="handleSubmit">
            <a-form-item has-feedback>
                <span slot="label">
                    姓名
                </span>
                <a-input
                    v-decorator="[
                    'name',
                    {
                        rules: [{ required: true, message: '请输入您的姓名', whitespace: true }],
                    },
                    ]"
                />
            </a-form-item>
            <a-form-item has-feedback>
                <span slot="label">
                    身份证号码
                </span>
                <a-input
                    v-decorator="[
                    'id_card',
                    {
                        rules: [{ required: true, message: '请输入您的身份证号码', whitespace: true }],
                    },
                    ]"
                />
            </a-form-item>
            <a-form-item label="手机号码">
                <a-input
                    v-decorator="[
                    'phone',
                    {
                        rules: [{ required: true, message: '请输入你的手机号码!' }],
                    },
                    ]"
                    style="width: 100%"
                >
                    <a-select
                    slot="addonBefore"
                    v-decorator="['prefix', { initialValue: '86' }]"
                    style="width: 70px"
                    >
                    <a-select-option value="86">
                        +86
                    </a-select-option>
                    </a-select>
                </a-input>
            </a-form-item>
            <a-form-item>
                <a-button type="primary" html-type="submit">
                    添加
                </a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
export default {
    name: 'PassengerInfo',
    props: {
        user_info: {
            type: Object,
        },
        flight_id: {
            type: String,
        }
    },
    data() {
        return {
            passenger: {}
        }
    }, 
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'register' });
    },
    methods: {
        handleSubmit(e) {
            this.loading = true
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    let param = {
                        id_card: values['id_card'],
                        phone: values['phone'],
                        flight_id: this.flight_id,
                        name: values['name'],
                        account_id: this.user_info.id
                    }
                    this.$flight_api.post_passenger(param).then((res) => {
                        console.log(res)
                        if (res.code == 200) {
                            this.passenger = res.data
                            this.$message.success('添加乘客成功! ');
                            this.$router.go(0)
                        } else {
                            this.$message.error('添加乘客信息失败! ' + res.msg);
                        }
                        this.loading = false
                    }).catch(() => {
                        this.$message.error('乘客信息已经存在! ');
                    })
                } else {
                    this.loading = false
                }
            });
        },
    }
}
</script>