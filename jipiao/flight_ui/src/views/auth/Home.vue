<template>
  <div>
    <a-page-header
      style="border: 1px solid rgb(235, 237, 240)"
      title="欢迎来到航班订票系统"
    />

  <a-tree :tree-data="treeData" show-icon default-expand-all :default-selected-keys="['0-0-0']">
    <a-icon slot="switcherIcon" type="down" />
    <a-icon slot="smile" type="smile-o" />
    <a-icon slot="meh" type="smile-o" />
    <template slot="custom" slot-scope="{ selected }">
      <a-icon :type="selected ? 'frown' : 'frown-o'" />
    </template>
  </a-tree>

  </div>
</template>

<script>
const treeData = [
  {
    title: '系统架构树形图',
    key: '0-0',
    slots: {
      icon: 'smile',
    },
  },
  {
    title: '普通用户',
    key: '1-0',
    children: [
      { 
        title: '机票模块', 
        key: '1-0-0',
        children: [
          { title: '机票信息查询', key: '1-0-1' },
          { title: '机票预定', key: '1-0-2' },
          { title: 'ip定位', key: '1-0-3' },
          { title: '乘客管理', key: '1-0-4' },
        ]
      },
      { 
        title: '订单模块', 
        key: '1-1-0',
        children: [
          { title: '订单查询', key: '1-1-1' },
          { title: '订单支付', key: '1-1-2' },
          { title: '取消订单', key: '1-1-3' },
        ]
      },
      { title: '个人信息查询', key: '1-2-1' },
      { title: '留言', key: '1-3-1' },
    ],
  },
  {
    title: '管理员',
    key: '2-1',
    children: [
      { title: '航班管理', key: '2-0-0' },
      { title: '用户管理', key: '2-0-1' },
      { title: '留言管理', key: '2-0-2' },
    ],
  },
];

export default {
  data() {
    return {
      treeData,
    };
  },
  methods: {
    onSelect(selectedKeys, info) {
      console.log('selected', selectedKeys, info);
    },
    onCheck(checkedKeys, info) {
      console.log('onCheck', checkedKeys, info);
    },
  },
};
</script>