<template>
    <div>
        <a-table
            :columns="columns"
            :data-source="data"
            :pagination="pagination"
            :loading="loading"
            style="margin-top:2rem;"
        >
            <template slot="status" slot-scope="text">
                <a-tag v-if="text === 0" :color="'volcano'">未处理</a-tag>
                <a-tag v-if="text === 1" :color="'green'">已处理</a-tag>
            </template>
            <!-- 删除 -->
            <template slot="operation" slot-scope="text, record">
                <a-button
                v-if="record.status === 0"
                type="danger"
                @click="del(row.id)"
                style="margin-right: 2rem"
                >处理完成</a-button
                >
            </template>
        </a-table>
    </div>
</template>

<script>

import { getTaskTime} from '../../utils/common'

const columns = [
  {
    title: "编号",
    dataIndex: "id",
    key: 'id',
    width: "10%",
  },
  {
    title: "内容",
    dataIndex: "content",
    key: 'content',
    width: "50%",
  },
  {
    title: "联系方式",
    dataIndex: "phone",
    key: 'phone',
    width: "10%",
  },
  {
    title: "时间",
    dataIndex: "create_time",
    key: 'create_time',
    width: "15%",
  },
  {
    title: "处理状态",
    dataIndex: "status",
    key: 'status',
    width: "10%",
    scopedSlots: { customRender: "status" },
  },
  {
    title: "操作",
    dataIndex: "operation",
    key: 'operation',
    scopedSlots: { customRender: "operation" },
  },
];

export default {
    name: 'MessageManage',
    data() {
        return {
            columns,
            loading: false,
            data: [],
            pagination: {
                pageSize: 5,
                current: 1,
            },
        }
    },
    mounted() {
        this.get_message()
    },
    methods: {
        /**
         * 查询留言信息
         */
        get_message() {
            this.loading = true
            let param = {
                'pagelimit': this.pagination.pageSize,
                'pagenum': this.pagination.current
            }
            this.$flight_api.get_messsages(param).then((res) => {
                console.log(res)
                if (res.code == 200) {
                    for (let index = 0; index < res.data.length; index++) {
                        res['data'][index]['create_time'] = getTaskTime(res['data'][index]['create_time'])
                    }
                    this.data = res.data
                } else {
                    this.$message.error('请求数据失败! ' + res.msg);
                }
                this.loading = false
            })
        },

        /**
         * 处理留言信息
         */
        del(id) {
            let param = {
                'id': id
            }
             this.$flight_api.remove_messsage(param).then((res) => {
                 if (res.code == 200) {
                    for (let index = 0; index < res.data.length; index++) {
                        res['data'][index]['create_time'] = getTaskTime(res['data'][index]['create_time'])
                    }
                    this.data = res.data
                } else {
                    this.$message.error('请求数据失败! ' + res.msg);
                }
                this.loading = false
             })
        }
    }
}
</script>