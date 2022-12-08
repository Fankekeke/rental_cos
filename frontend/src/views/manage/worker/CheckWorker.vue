<template>
  <a-drawer
    title="选择员工"
    width="1000"
    :destroyOnClose="true"
    :closable="false"
    :maskClosable="true"
    @close="onChildrenDrawerClose"
    :visible="childrenDrawerShow"
  >
    <a-row :gutter="15" style="margin-bottom: 10px">
      <a-col :md="12" :sm="24" v-if="rentData !== null && rentData.staffName !== null" style="font-size: 13px;font-family: SimHei">
        <b>当前所选：</b> {{ rentData.staffName }}
      </a-col>
      <a-col :md="12" :sm="24">
        <a-input-search v-model="queryParams.staffName" placeholder="输入用户名称查询" @search="search"/>
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
      <template slot="avatarShow" slot-scope="text, record">
        <template>
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
              {{ record.content }}
            </template>
            {{ record.content.slice(0, 30) }} ...
          </a-tooltip>
        </template>
      </template>
    </a-table>
  </a-drawer>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: 'CheckWorker',
  props: {
    childrenDrawerShow: {default: false},
    rentData: {
      type: Object,
      default: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '人员姓名',
        dataIndex: 'staffName'
      }, {
        title: '联系方式',
        dataIndex: 'phone'
      }, {
        title: '照片',
        dataIndex: 'avatar',
        customRender: (text, record, index) => {
          if (!record.avatar) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.avatar.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.avatar.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '人员类型',
        dataIndex: 'staffType',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>销售员</a-tag>
            case 2:
              return <a-tag>超级销售员</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '性别',
        dataIndex: 'sex',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>男</a-tag>
            case 2:
              return <a-tag>女</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '员工状态',
        dataIndex: 'staffStatus',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="green">正常</a-tag>
            case 1:
              return <a-tag color="red">离职</a-tag>
            default:
              return '- -'
          }
        }
      }]
    }
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
  watch: {
    childrenDrawerShow: function (value) {
      if (value) {
        this.selectedRowKeys = []
      }
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
      console.log(JSON.stringify(selectedRowKeys))
      let staff = null
      this.dataSource.forEach(item => {
        if (item.id == selectedRowKeys) {
          staff = item
        }
      })
      if (staff !== null) {
        this.$post('/cos/rent-charge', {
          staffCode: staff.staffCode,
          rentId: this.rentData.id
        }).then((r) => {
          this.handlerClosed()
        })
      } else {
        this.handlerClosed()
      }
    },
    onChildrenDrawerClose () {
      this.handlerClosed()
    },
    handlerClosed () {
      this.$emit('handlerClosed')
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
      this.$get('/cos/staff-info/page', {
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
