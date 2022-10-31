<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="小区编号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.code"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="小区名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.houseName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="物业类型"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.propertyType">
                  <a-select-option value="1">公寓住宅</a-select-option>
                  <a-select-option value="2">商业物业</a-select-option>
                  <a-select-option value="3">工业物业</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="权属类别"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.tenureCategory">
                  <a-select-option value="1">商品房住宅</a-select-option>
                  <a-select-option value="2">央产房</a-select-option>
                  <a-select-option value="3">军产房</a-select-option>
                  <a-select-option value="4">小产权房</a-select-option>
                  <a-select-option value="5">自建房</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="建筑类型"
                  :labelCol="{span: 5}"
                  :wrapperCol="{span: 18, offset: 1}">
                  <a-select v-model="queryParams.buildingType">
                    <a-select-option value="1">居住建筑</a-select-option>
                    <a-select-option value="2">公共建筑</a-select-option>
                    <a-select-option value="3">工业建筑</a-select-option>
                    <a-select-option value="4">农业建筑</a-select-option>
                    <a-select-option value="5">大量性建筑</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="统一供暖"
                  :labelCol="{span: 5}"
                  :wrapperCol="{span: 18, offset: 1}">
                  <a-select v-model="queryParams.unifiedHeating">
                    <a-select-option value="1">是</a-select-option>
                    <a-select-option value="2">否</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="供水供电"
                  :labelCol="{span: 5}"
                  :wrapperCol="{span: 18, offset: 1}">
                  <a-select v-model="queryParams.waterSupply">
                    <a-select-option value="1">民用</a-select-option>
                    <a-select-option value="2">商用</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="6" :sm="24">
                <a-form-item
                  label="小区地址"
                  :labelCol="{span: 5}"
                  :wrapperCol="{span: 18, offset: 1}">
                  <a-input v-model="queryParams.address"/>
                </a-form-item>
              </a-col>
            </template>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
            <a @click="toggleAdvanced" style="margin-left: 8px">
              {{ advanced ? '收起' : '展开' }}
              <a-icon :type="advanced ? 'up' : 'down'"/>
            </a>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">新增</a-button>
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
        <template slot="operation" slot-scope="text, record">
          <a-icon type="folder-open" @click="view(record)" title="查 看" style="margin-right: 15px"></a-icon>
          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>
        </template>
      </a-table>
    </div>
    <house-add
      v-if="houseAdd.visiable"
      @close="handlehouseAddClose"
      @success="handlehouseAddSuccess"
      :houseAddVisiable="houseAdd.visiable">
    </house-add>
    <house-edit
      ref="houseEdit"
      @close="handlehouseEditClose"
      @success="handlehouseEditSuccess"
      :houseEditVisiable="houseEdit.visiable">
    </house-edit>
    <house-view
      @close="handlehouseViewClose"
      :houseShow="houseView.visiable"
      :houseData="houseView.data">
    </house-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import houseAdd from './HouseAdd'
import houseEdit from './HouseEdit'
import {mapState} from 'vuex'
import moment from 'moment'
import houseView from "./HouseView";
moment.locale('zh-cn')

export default {
  name: 'house',
  components: {houseView, houseAdd, houseEdit, RangeDate},
  data () {
    return {
      advanced: false,
      houseAdd: {
        visiable: false
      },
      houseEdit: {
        visiable: false
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
      houseView: {
        visiable: false,
        data: null
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
        title: '房屋编号',
        dataIndex: 'code'
      }, {
        title: '小区名称',
        dataIndex: 'communityName'
      }, {
        title: '房屋地址',
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
        title: '房间数量',
        dataIndex: 'roomNumber',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '间'
          } else {
            return '- -'
          }
        }
      }, {
        title: '客厅数量',
        dataIndex: 'livingRoomNumber',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '卫生间数量',
        dataIndex: 'bathroomNumber',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '房间面积',
        dataIndex: 'roomSize',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text + '㎡'
          } else {
            return '- -'
          }
        }
      }, {
        title: '楼层',
        dataIndex: 'floor',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '装修类型',
        dataIndex: 'decorationType',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>精装修</a-tag>
            case 2:
              return <a-tag>普通装修</a-tag>
            case 3:
              return <a-tag>暂无装修</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '房屋类型',
        dataIndex: 'houseType',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>普通住宅</a-tag>
            case 2:
              return <a-tag>高层楼</a-tag>
            case 3:
              return <a-tag>别墅</a-tag>
            case 4:
              return <a-tag>大平层</a-tag>
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
    handlehouseViewClose () {
      this.houseView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.houseAdd.visiable = true
    },
    handlehouseAddClose () {
      this.houseAdd.visiable = false
    },
    handlehouseAddSuccess () {
      this.houseAdd.visiable = false
      this.$message.success('新增小区成功')
      this.search()
    },
    view (row) {
      this.houseView.data = row
      this.houseView.visiable = true
    },
    edit (record) {
      this.$refs.houseEdit.setFormValues(record)
      this.houseEdit.visiable = true
    },
    handlehouseEditClose () {
      this.houseEdit.visiable = false
    },
    handlehouseEditSuccess () {
      this.houseEdit.visiable = false
      this.$message.success('修改小区成功')
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
          that.$delete('/cos/house-info/' + ids).then(() => {
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
      if (params.propertyType === undefined) {
        delete params.propertyType
      }
      if (params.tenureCategory === undefined) {
        delete params.tenureCategory
      }
      if (params.buildingType === undefined) {
        delete params.buildingType
      }
      if (params.unifiedHeating === undefined) {
        delete params.unifiedHeating
      }
      if (params.waterSupply === undefined) {
        delete params.waterSupply
      }


      this.$get('/cos/house-info/page', {
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
