<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="员工姓名"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.staffName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="租房人"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.rentUserName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="当前状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.step" allowClear>
                  <a-select-option value="1">等待审批</a-select-option>
                  <a-select-option value="2">通过</a-select-option>
                  <a-select-option value="2">驳回</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="缴费方式"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.payType" allowClear>
                  <a-select-option value="1">押一付一</a-select-option>
                  <a-select-option value="2">押一付三</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">新增</a-button>-->
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.houseAddress }}
              </template>
              {{ record.houseAddress.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="contentShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.communityName }}
              </template>
              {{ record.communityName.slice(0, 30) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon v-if="record.step === '1'" type="pushpin" theme="twoTone" twoToneColor="#4a9ff5" @click="audit(record)" title="审 核"></a-icon>
          <a-icon v-else type="bulb" theme="twoTone" twoToneColor="#4a9ff5" @click="audit(record)" title="查 看"></a-icon>
        </template>
      </a-table>
    </div>
    <delivery-add
      v-if="deliveryAdd.visiable"
      @close="handledeliveryAddClose"
      @success="handledeliveryAddSuccess"
      :deliveryAddVisiable="deliveryAdd.visiable">
    </delivery-add>
    <delivery-audit :rentAuditVisiable="rentAudit.visiable" :deliveryInfo="rentAudit.data" @close="rentAuditClose" @success="rentAuditSuccess"></delivery-audit>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import deliveryAdd from './DeliveryAdd'
import {mapState} from 'vuex'
import moment from 'moment'
import deliveryAudit from "./DeliveryAudit";
moment.locale('zh-cn')

export default {
  name: 'delivery',
  components: {deliveryAudit, deliveryAdd, RangeDate},
  data () {
    return {
      advanced: false,
      deliveryAdd: {
        visiable: false
      },
      deliveryEdit: {
        visiable: false
      },
      rentAudit: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
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
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '合同编号',
        dataIndex: 'contractCode',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '房屋地址',
        dataIndex: 'houseAddress',
        scopedSlots: { customRender: 'titleShow' }
      }, {
        title: '小区地址',
        dataIndex: 'communityName',
        scopedSlots: { customRender: 'contentShow' }
      }, {
        title: '租房人',
        dataIndex: 'rentUserName',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '所属业主',
        dataIndex: 'ownerUserName',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '租金/月',
        dataIndex: 'contractPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '当前状态',
        dataIndex: 'step',
        customRender: (text, row, index) => {
          switch (text) {
            case '1':
              return <a-tag>正在审核</a-tag>
            case '2':
              return <a-tag>审核通过</a-tag>
            case '3':
              return <a-tag color="red">驳 回</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '租用时间',
        dataIndex: 'rentDay',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '个月'
          } else {
            return '- -'
          }
        }
      }, {
        title: '缴费方式',
        dataIndex: 'payType',
        customRender: (text, row, index) => {
          switch (text) {
            case '1':
              return <a-tag>押一付一</a-tag>
            case '2':
              return <a-tag>押一付三</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    audit (row) {
      this.rentAudit.visiable = true
      this.rentAudit.data = row
    },
    rentAuditClose () {
      this.rentAudit.visiable = false
    },
    rentAuditSuccess () {
      this.rentAudit.visiable = false
      this.$message.success('审批成功')
      this.search()
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.deliveryAdd.visiable = true
    },
    handledeliveryAddClose () {
      this.deliveryAdd.visiable = false
    },
    handledeliveryAddSuccess () {
      this.deliveryAdd.visiable = false
      this.$message.success('新增公告成功')
      this.search()
    },
    edit (record) {
      this.$refs.deliveryEdit.setFormValues(record)
      this.deliveryEdit.visiable = true
    },
    handledeliveryEditClose () {
      this.deliveryEdit.visiable = false
    },
    handledeliveryEditSuccess () {
      this.deliveryEdit.visiable = false
      this.$message.success('修改公告成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/delivery-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
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
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
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
      if (params.step === undefined) {
        delete params.step
      }
      if (params.payType === undefined) {
        delete params.payType
      }
      this.$get('/cos/delivery-review/page', {
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
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
