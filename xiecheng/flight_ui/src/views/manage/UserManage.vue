<template>
  <div>
    <a-page-header
      style="border: 1px solid rgb(235, 237, 240)"
      title="用户管理中心"
    />
    <a-table
      :columns="columns"
      :data-source="data"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
      bordered
    >
    <template slot="restAsset" slot-scope="text">
        {{ text }} <span style="float: right; font-weight: 700;">￥</span>
      </template>
      <!-- 用户操作 -->
      <template slot="operation" slot-scope="text, row">
        <a-button type="danger" @click="showDeleteConfirm(row.id)"
          >删除用户</a-button
        >
      </template>
    </a-table>
  </div>
</template>

<script>
import { formatDate } from "../../utils/common";
const columns = [
  {
    title: "昵称",
    dataIndex: "nickname",
    width: "10%",
  },
  {
    title: "账号",
    dataIndex: "phone",
    width: "15%",
  },
  {
    title: "创建时间",
    dataIndex: "create_time",
    width: "20%",
  },
  {
    title: "操作",
    dataIndex: "operation",
    scopedSlots: { customRender: "operation" },
  },
];

export default {
  name: "UserManage",
  data() {
    return {
      data: [],
      pagination: {
        pageSize: 15,
        current: 1,
      },
      loading: false,
      columns: columns,
    };
  },
  mounted() {
    this.getAccountData(this.pagination.pageSize, this.pagination.current);
  },
  methods: {
    // 表格分页
    handleTableChange(pagination) {
      const pager = { ...this.pagination };
      pager.current = pagination.current;
      this.pagination = pager;
      this.getData(pagination.pageSize, pagination.current);
    },

    /**
     * 获取账户信息列表
     */
    getAccountData(pagelimit, pageno) {
      this.loading = true;
      let data = {
        pagelimit: pagelimit,
        pagenum: pageno,
      };
      this.$user_api.user_list(data).then((res) => {
        console.log(res)
        if (res.code == 200) {
            this.loading = false;
            this.data = res.data.accounts;
            for (let i = 0; i < this.data.length; i++) {
                this.data[i]["create_time"] = formatDate(this.data[i]["create_time"]);
            }
            // 分页处理
            const pagination = { ...this.pagination };
            pagination.total = res.data.sum;
            this.pagination = pagination;
        } else {
            this.$message.error('获取用户数据失败!!!' + res.msg);
        }
      });
    },
    // 删除弹窗
    showDeleteConfirm(id) {
      let that = this;
      that.$confirm({
        title: "确认删除该用户？",
        okText: "确认",
        okType: "danger",
        cancelText: "取消",
        onOk() {
          that.deleteUser(id);
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    deleteUser(id) {
      // 删除数据
      let data = { id: id };
      this.$user_api.delete_user(data).then((res) => {
        if (res.code == 200) {
          this.$message.success("删除成功！");
        } else {
          this.$message.error("删除失败！" + res.msg);
        }
      });
    },
  },
};
</script>