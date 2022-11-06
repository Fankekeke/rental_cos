<template>
  <a-drawer
    title="选择小区"
    width="1000"
    :destroyOnClose="true"
    :closable="false"
    :maskClosable="true"
    @close="onChildrenDrawerClose"
    :visible="childrenDrawerShow"
  >
    <a-row :gutter="15" style="margin-bottom: 10px">
      <a-col :md="12" :sm="24">
        <a-input-search v-model="queryParams.communityName" placeholder="输入小区名称查询" @search="search"/>
      </a-col>
    </a-row>
    <a-table ref="TableInfo"
             :columns="columns"
             :rowKey="record => record.id"
             :dataSource="dataSource"
             :pagination="pagination"
             :loading="loading"
             :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, type: 'radio'}"
             :scroll="{ x: 900 }"
             @change="handleTableChange">
      <template slot="titleShow" slot-scope="text, record">
        <template>
          <a-badge status="processing" v-if="record.rackUp === 1"/>
          <a-badge status="error" v-if="record.rackUp === 0"/>
          <a-tooltip>
            <template slot="title">
              {{ record.title }}
            </template>
            {{ record.title.slice(0, 8) }} ...
          </a-tooltip>
        </template>
      </template>
      <template slot="contentShow" slot-scope="text, record">
        <template>
          <a-tooltip>
            <template slot="title">
              {{ record.address }}
            </template>
            {{ record.address.slice(0, 30) }} ...
          </a-tooltip>
        </template>
      </template>
    </a-table>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'CheckCommunity',
  props: {
    childrenDrawerShow: {default: false}
  },
  data () {
    return {
      queryParams: {},
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '小区名称',
        dataIndex: 'communityName'
      }, {
        title: '小区地址',
        dataIndex: 'address',
        scopedSlots: { customRender: 'contentShow' }
      }, {
        title: '小区所在',
        dataIndex: 'province',
        customRender: (text, row, index) => {
          if (text !== null) {
            return row.province + row.city + row.area
          } else {
            return '- -'
          }
        }
      }, {
        title: '物业类型',
        dataIndex: 'propertyType',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>公寓住宅</a-tag>
            case 2:
              return <a-tag>商业物业</a-tag>
            case 3:
              return <a-tag>工业物业</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '所属商圈',
        dataIndex: 'businessDistrict',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '统一供暖',
        dataIndex: 'unifiedHeating',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag color="green">是</a-tag>
            case 2:
              return <a-tag color="pink">否</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '供水供电',
        dataIndex: 'waterSupply',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>民用</a-tag>
            case 2:
              return <a-tag>商用</a-tag>
            default:
              return '- -'
          }
        }
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
      let community = null
      this.dataSource.forEach(item => {
        if (item.id == selectedRowKeys) {
          community = item
        }
      })
      this.handlerClosed(community)
    },
    onChildrenDrawerClose () {
      this.handlerClosed()
    },
    handlerClosed (community) {
      this.$emit('handlerClosed', community)
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      this.$get('/cos/community-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
