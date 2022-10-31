<template>
  <a-drawer
    title="新增房屋"
    :maskClosable="false"
    width=1350
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="houseAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="10">
        <a-divider orientation="left">
          <span style="font-size: 13px">基础信息填报</span>
        </a-divider>
        <a-col :span="4">
          <a-form-item label='小区名称'>
            <a-input v-decorator="[
            'houseName',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='物业类型'>
            <a-select v-decorator="[
                'propertyType',
                ]">
              <a-select-option value="1">公寓住宅</a-select-option>
              <a-select-option value="2">商业物业</a-select-option>
              <a-select-option value="3">工业物业</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='竣工时间'>
            <a-input v-decorator="[
            'completionTime'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='权属类别'>
            <a-select v-decorator="[
                'tenureCategory',
                ]">
              <a-select-option value="1">商品房住宅</a-select-option>
              <a-select-option value="2">央产房</a-select-option>
              <a-select-option value="3">军产房</a-select-option>
              <a-select-option value="4">小产权房</a-select-option>
              <a-select-option value="5">自建房</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='所在地'>
            <a-input-search
              v-decorator="[
              'address'
              ]"
              enter-button="选择"
              @search="showChildrenDrawer"
            />
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='产权年限'>
            <a-input v-decorator="[
            'propertyTenure'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='总户数'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'totalHouses'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='总建面积'>
            <a-input-number style="width: 100%" :min="1" :step="0.1" v-decorator="[
            'totalConstructionArea'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='容积率'>
            <a-input-number style="width: 100%" :min="1" :step="0.1" v-decorator="[
            'volumeRate'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='经度'>
            <a-input v-decorator="[
            'longitude'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='纬度'>
            <a-input v-decorator="[
            'latitude'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='绿化率'>
            <a-input-number style="width: 100%" :min="1" :step="0.1" v-decorator="[
            'greeningRate'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='建筑类型'>
            <a-select v-decorator="[
                'buildingType',
                ]">
              <a-select-option value="1">居住建筑</a-select-option>
              <a-select-option value="2">公共建筑</a-select-option>
              <a-select-option value="3">工业建筑</a-select-option>
              <a-select-option value="4">农业建筑</a-select-option>
              <a-select-option value="5">大量性建筑</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='所属商圈'>
            <a-input v-decorator="[
            'businessDistrict'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='停车位'>
            <a-input v-decorator="[
            'parkingSpace'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='统一供暖'>
            <a-select v-decorator="[
                'unifiedHeating',
                ]">
              <a-select-option value="1">是</a-select-option>
              <a-select-option value="2">否</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='供水供电'>
            <a-select v-decorator="[
                'waterSupply',
                ]">
              <a-select-option value="1">民用</a-select-option>
              <a-select-option value="2">商用</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='物业费'>
            <a-input v-decorator="[
            'propertyCosts'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='停车费'>
            <a-input v-decorator="[
            'parkingFee'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='车位管理费'>
            <a-input v-decorator="[
            'parkingManagementFee'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='物业公司'>
            <a-input v-decorator="[
            'propertyCompany'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='开发商'>
            <a-input v-decorator="[
            'developer'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24"></a-col>
        <a-divider orientation="left">
          <span style="font-size: 13px">选择地区</span>
        </a-divider>
        <a-col :span="8">
          <a-cascader :options="options" :fieldNames="{label: 'title', value: 'title', children: 'children'}" placeholder="Please select" @change="onChange" />
        </a-col>

        <a-col :span="24"></a-col>
        <a-col :span="12">
          <a-form-item label='备注'>
            <a-textarea v-decorator="[
            'remark'
            ]" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>

    <drawerMap :childrenDrawerShow="childrenDrawer" @handlerClosed="handlerClosed"></drawerMap>

    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import drawerMap from '@/utils/map/searchmap/drawerMap'
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'houseAdd',
  props: {
    houseAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currenthouse: state => state.account.house
    }),
    show: {
      get: function () {
        return this.houseAddVisiable
      },
      set: function () {
      }
    }
  },
  components: {
    drawerMap
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      localPoint: {},
      stayAddress: '',
      childrenDrawer: false,
      options: [],
      province: '',
      city: '',
      area: ''
    }
  },
  mounted() {
    this.getCity()
  },
  methods: {
    getCity () {
      this.$get('/cos/sys-city/cityChild').then((r) => {
        this.options = r.data.data.children
      })
    },
    onChange(value) {
      if (value && value.length === 2) {
        this.city = value[0]
        this.area = value[1]
      }
      if (value && value.length === 3) {
        this.province = value[0]
        this.city = value[1]
        this.area = value[2]
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    handlerClosed (localPoint) {
      this.childrenDrawer = false
      if (localPoint !== null && localPoint !== undefined) {
        this.localPoint = localPoint
        console.log(this.localPoint)

        let address = baiduMap.getAddress(localPoint)
        address.getLocation(localPoint, (rs) => {
          if (rs != null) {
            if (rs.address !== undefined && rs.address.length !== 0) {
              this.stayAddress = rs.address
            }
            if (rs.surroundingPois !== undefined) {
              if (rs.surroundingPois.address !== undefined && rs.surroundingPois.address.length !== 0) {
                this.stayAddress = rs.surroundingPois.address
              }
            }
            let obj = {}
            obj['address'] = this.stayAddress
            obj['longitude'] = localPoint.lng
            obj['latitude'] = localPoint.lat
            this.form.setFieldsValue(obj)
          }
        })
      }
    },
    showChildrenDrawer () {
      this.childrenDrawer = true
    },
    onChildrenDrawerClose () {
      this.childrenDrawer = false
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          values.province = this.province
          values.city = this.city
          values.area = this.area
          this.$post('/cos/house-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
