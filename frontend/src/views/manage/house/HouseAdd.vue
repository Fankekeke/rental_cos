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
          <span style="font-size: 13px">房屋信息填报</span>
        </a-divider>
        <a-col :span="4">
          <a-form-item label='房屋地址'>
            <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='装修类型'>
            <a-select v-decorator="[
                'decorationType',
                ]">
              <a-select-option value="1">精装修</a-select-option>
              <a-select-option value="2">普通装修</a-select-option>
              <a-select-option value="3">暂无装修</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='房屋类型'>
            <a-select v-decorator="[
                'houseType',
                ]">
              <a-select-option value="1">普通住宅</a-select-option>
              <a-select-option value="2">高层楼</a-select-option>
              <a-select-option value="3">别墅</a-select-option>
              <a-select-option value="4">大平层</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='朝向'>
            <a-input v-decorator="[
            'towards'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='房间数量'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'roomNumber'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='客厅数量'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'livingRoomNumber'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='卫生间数量'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'bathroomNumber'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='房间面积'>
            <a-input-number style="width: 100%" :min="1" :step="0.1" v-decorator="[
            'roomSize'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='楼层'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'floor'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="4">
          <a-form-item label='标签'>
            <a-input v-decorator="[
            'tags'
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='所属业主'>
            <a-input-search
              v-decorator="[
              'ownerCode'
              ]"
              enter-button="选择"
              @search="showChildrenDrawer"
            />
          </a-form-item>
        </a-col>
        <a-col :span="24"></a-col>
        <a-divider orientation="left">
          <span style="font-size: 13px">选择地区</span>
        </a-divider>
        <a-col :span="8">
          <a-form-item label='所属小区'>
            <a-input-search
              v-model="communityName"
              enter-button="选择"
              @search="communityDrawer = true"
            />
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='所属地区'>
            <a-cascader :options="options" v-model="defaultValue" :fieldNames="{label: 'title', value: 'title', children: 'children'}" placeholder="Please select" @change="onChange" />
          </a-form-item>
        </a-col>
        <a-col :span="24"></a-col>
        <a-col :span="16">
          <a-form-item label='房源概况'>
            <a-textarea v-decorator="[
            'propertyOverview'
            ]" :rows="4"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
    <check-community :childrenDrawerShow="communityDrawer" @handlerClosed="handlerCommunityClosed"></check-community>
  </a-drawer>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import {mapState} from 'vuex'
import CheckCommunity from '../community/CheckCommunity'
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
  components: {CheckCommunity},
  props: {
    houseAddVisiable: {
      default: false
    }
  },
  computed: {
    show: {
      get: function () {
        return this.houseAddVisiable
      },
      set: function () {
      }
    }
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
      communityDrawer: false,
      options: [],
      province: '',
      city: '',
      area: '',
      defaultValue: [],
      communityName: '',
      community: null
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
    handlerCommunityClosed (community) {
      this.communityDrawer = false
      this.defaultValue = []
      this.communityName = community.communityName
      this.community = community
      if (community['province']) {
        this.defaultValue.push(community['province'])
      }
      if (community['city']) {
        this.defaultValue.push(community['city'])
      }
      if (community['area']) {
        this.defaultValue.push(community['area'])
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
