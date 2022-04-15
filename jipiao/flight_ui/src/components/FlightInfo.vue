<template>
    <div style="width: 60%; margin: 0 auto">
        <a-form-model ref="ruleForm" :model="form">
            <a-form-model-item has-feedback label="航空公司" prop="pass">
                <a-input v-model="form.flight_company" type="text" autocomplete="off" />
            </a-form-model-item>
            <a-form-model-item has-feedback label="机型" prop="checkPass">
                <a-input v-model="form.flight_type" type="text" autocomplete="off" />
            </a-form-model-item>
            <a-form-model-item has-feedback label="座位数量" prop="checkPass">
                <a-input-number :min="0" :value="form.flight_number" @change="handleNumberChange" />
            </a-form-model-item>
            <a-form-model-item has-feedback label="机票价格" prop="checkPass">
                <a-input-number :min="0" :value="form.price" @change="handlePriceChange" />
            </a-form-model-item>
            <a-form-model-item has-feedback label="起始地点">
                <a-cascader
                    :options="options"
                    expand-trigger="hover"
                    placeholder="选择始发城市"
                    @change="onChangeStartCity"
                />
            </a-form-model-item>
            <a-form-model-item has-feedback label="到达地点">
                <a-cascader
                    :options="options"
                    expand-trigger="hover"
                    placeholder="选择始发城市"
                    @change="onChangeArrCity"
                />
            </a-form-model-item>
            <a-form-item label="航班时间">
                <a-range-picker
                :disabled-date="disabledDate"
                :disabled-time="disabledRangeTime"
                :show-time="{
                    hideDisabledOptions: true,
                    defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('11:59:59', 'HH:mm:ss')],
                }"
                @ok="handleOk"
                format="YYYY-MM-DD HH:mm:ss"
                />
            </a-form-item>
            <a-form-item>
                <a-button type="primary" html-type="submit" @click="handleSubmit">
                    确认
                </a-button>
            </a-form-item>
        </a-form-model>
    </div>
</template>

<script>
import { city_options, getTaskTime } from '../utils/common'
import moment from 'moment';
import 'moment/locale/zh-cn';

export default {
    name: 'FlightInfo',
    props: {
        flight: {
            type: Object,
        }
    },
    data() {
        return {
            form: {
                flight_company: this.flight.flight_company,
                flight_type: this.flight.flight_type,
                price: this.flight.price,
                from_pos: this.flight.from_pos,
                to_pos: this.flight.to_pos,
                start_time: this.flight.start_time,
                end_time: this.flight.end_time,
                flight_number: this.flight.flight_number
            },
            options: city_options
        }
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'register' });
    },
    methods: {

        moment,

        /**
         * 时间范围
         */
        range(start, end) {
            const result = [];
            for (let i = start; i < end; i++) {
                result.push(i);
            }
            return result;
        },

        /**
         * 禁用日期
         */
        disabledDate(current) {
            // Can not select days before today and today
            return current && current < moment().endOf('day');
        },

        /**
         * 时间范围
         */
        disabledRangeTime(_, type) {
            if (type === 'start') {
                return {
                disabledHours: () => this.range(0, 60).splice(24),
                disabledMinutes: () => this.range(60),
                disabledSeconds: () => [55, 56],
                };
            }
            return {
                disabledHours: () => this.range(0, 60).splice(24),
                disabledMinutes: () => this.range(0),
                disabledSeconds: () => [55, 56],
            };
        },

        /**
         * 处理日期选择框
         */
        handleOk(e) {
            this.form.start_time = getTaskTime(String(e[0]['_d']))
            this.form.end_time = getTaskTime(String(e[1]['_d']))
        },

        /**
         * 处理提交
         */
        handleSubmit() {
            if (this.form.from_pos === this.form.to_pos) {
                this.$message.error('始发城市和目的地城市不能是一个城市!');
                return
            }

            this.loading = true
            let param = {
                flight_id: this.flight.id,
                flight_company: this.form.flight_company,
                flight_type: this.form.flight_type,
                price: this.form.price,
                flight_number: this.form.flight_number,
                from_pos: this.form.from_pos,
                to_pos: this.form.to_pos,
                start_time: this.form.start_time,
                end_time: this.form.end_time
            }
            console.log(param)
            this.$flight_api.update_flight(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    this.$message.success('修改航班信息成功! ');
                    this.$router.go(0)
                } else {
                    this.$message.error('修改航班信息失败! ' + res.msg);
                }
                this.loading = false
            })
        },

        /**
         * 处理数据表单
         */
        handleNumberChange(value) {
            console.log(value)
            this.form.flight_number = value
        },
        
        handlePriceChange(value) {
            console.log(value)
            this.form.price = value    
        },


        /**
         * 始发城市处理
         */
        onChangeStartCity(e) {
            this.form.from_pos = e[e.length - 1]
        },

        /**
         * 到达城市处理
         */
        onChangeArrCity(e) {
            this.form.to_pos = e[e.length - 1]
        }
    }
}
</script>