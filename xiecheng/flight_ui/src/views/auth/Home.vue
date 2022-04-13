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
    title: '普通用户',
    key: '0-0',
    slots: {
      icon: 'smile',
    },
  },
  {
    title: '普通用户',
    key: '1-0',
    children: [
      { title: 'leaf', key: '0-0-0' },
      { title: 'leaf', key: '0-0-1' },
    ],
  },
  {
    title: '管理员',
    key: '2-0',
    children: [
      { title: 'leaf', key: '0-0-0' },
      { title: 'leaf', key: '0-0-1' },
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