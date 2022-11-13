<template>
  <a-drawer
    title="修改房屋"
    :maskClosable="false"
    width=1350
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="houseEditVisiable"
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
              v-model="userName"
              enter-button="选择"
              @search="userDrawer = true"
            />
          </a-form-item>
        </a-col>
        <a-col :span="24"></a-col>
        <a-divider orientation="left">
          <span style="font-size: 13px">其它</span>
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
            <a-cascader disabled :options="options" v-model="defaultValue" :fieldNames="{label: 'title', value: 'title', children: 'children'}" placeholder="Please select" @change="onChange" />
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
        <a-divider orientation="left">
          <span style="font-size: 13px">图片</span>
        </a-divider>
        <a-col :span="24">
          <a-form-item label='室内图' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="indoorMapFileList"
              @preview="indoorMapHandlePreview"
              @change="indoorMapPicHandleChange"
            >
              <div v-if="indoorMapFileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="indoorMapPreviewVisible" :footer="null" @cancel="indoorMapHandleCancel">
              <img alt="example" style="width: 100%" :src="indoorMapPreviewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='户型图' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="housePlanFileList"
              @preview="housePlanHandlePreview"
              @change="housePlanPicHandleChange"
            >
              <div v-if="housePlanFileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="housePlanPreviewVisible" :footer="null" @cancel="housePlanHandleCancel">
              <img alt="example" style="width: 100%" :src="housePlanPreviewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='环境图' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="environmentMapFileList"
              @preview="environmentMapHandlePreview"
              @change="environmentMapPicHandleChange"
            >
              <div v-if="environmentMapFileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="environmentMapPreviewVisible" :footer="null" @cancel="environmentMapHandleCancel">
              <img alt="example" style="width: 100%" :src="environmentMapPreviewImage" />
            </a-modal>
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
    <check-user :childrenDrawerShow="userDrawer" @handlerClosed="handlerUserClosed"></check-user>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'
import CheckCommunity from '../community/CheckCommunity'
import CheckUser from "../user/CheckUser";
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
  name: 'houseEdit',
  props: {
    houseEditVisiable: {
      default: false
    }
  },
  components: {
    CheckUser, CheckCommunity
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.houseEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      indoorMapFileList: [],
      housePlanFileList: [],
      environmentMapFileList: [],
      previewVisible: false,
      indoorMapPreviewVisible: false,
      housePlanPreviewVisible: false,
      environmentMapPreviewVisible: false,
      previewImage: '',
      indoorMapPreviewImage: '',
      housePlanPreviewImage: '',
      environmentMapPreviewImage: '',
      localPoint: {},
      stayAddress: '',
      communityDrawer: false,
      userDrawer: false,
      options: [],
      province: '',
      city: '',
      area: '',
      defaultValue: [],
      communityName: '',
      community: null,
      userName: '',
      user: null
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
    indoorMapHandleCancel () {
      this.indoorMapPreviewVisible = false
    },
    housePlanHandleCancel () {
      this.housePlanPreviewVisible = false
    },
    environmentMapHandleCancel () {
      this.environmentMapPreviewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    async indoorMapHandlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.indoorMapPreviewImage = file.url || file.preview
      this.indoorMapPreviewVisible = true
    },
    async housePlanHandlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.housePlanPreviewImage = file.url || file.preview
      this.housePlanPreviewVisible = true
    },
    async environmentMapHandlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.environmentMapPreviewImage = file.url || file.preview
      this.environmentMapPreviewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    indoorMapPicHandleChange ({ fileList }) {
      this.indoorMapFileList = fileList
    },
    housePlanPicHandleChange ({ fileList }) {
      this.housePlanFileList = fileList
    },
    environmentMapPicHandleChange ({ fileList }) {
      this.environmentMapFileList = fileList
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
    handlerUserClosed (user) {
      this.userDrawer = false
      this.userName = user.userName
      this.user = user
    },
    showChildrenDrawer () {
      this.childrenDrawer = true
    },
    onChildrenDrawerClose () {
      this.childrenDrawer = false
    },
    imagesInit (indoorMap, housePlan, environmentMap) {
      this.indoorMapFileList = []
      this.housePlanFileList = []
      this.environmentMapFileList = []
      if (indoorMap !== null && indoorMap !== '') {
        let indoorMapImageList = []
        indoorMap.split(',').forEach((image, index) => {
          indoorMapImageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.indoorMapFileList = indoorMapImageList
      }
      if (housePlan !== null && housePlan !== '') {
        let housePlanImageList = []
        housePlan.split(',').forEach((image, index) => {
          housePlanImageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.housePlanFileList = housePlanImageList
      }
      if (environmentMap !== null && environmentMap !== '') {
        let environmentMapImageList = []
        environmentMap.split(',').forEach((image, index) => {
          environmentMapImageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.environmentMapFileList = environmentMapImageList
      }
    },
    setFormValues ({...house}) {
      this.rowId = house.id
      let fields = ['address', 'roomNumber', 'livingRoomNumber', 'bathroomNumber', 'roomSize', 'floor', 'decorationType', 'houseType', 'towards', 'propertyOverview', 'tags', 'indoorMap', 'housePlan', 'environmentMap', 'communityName', 'userName', 'province', 'city', 'area']
      let obj = {}
      Object.keys(house).forEach((key) => {
        if (key === 'decorationType' || key === 'houseType') {
          if (house[key]) {
            house[key] = house[key].toString()
          }
        }
        if (key === 'communityName') {
          this.communityName = house[key]
        }
        if (key === 'userName') {
          this.userName = house[key]
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = house[key]
        }
      })
      setTimeout(() => {
        if (obj['province']) {
          this.defaultValue.push(obj['province'])
        }
        if (obj['city']) {
          this.defaultValue.push(obj['city'])
        }
        if (obj['area']) {
          this.defaultValue.push(obj['area'])
        }
      }, 200)
      this.imagesInit(obj['indoorMap'], obj['housePlan'], obj['environmentMap'])
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.defaultValue = []
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      let indoorMaps = []
      let housePlans = []
      let environmentMaps = []
      this.indoorMapFileList.forEach(image => {
        indoorMaps.push(image.name)
      })
      this.housePlanFileList.forEach(image => {
        housePlans.push(image.name)
      })
      this.environmentMapFileList.forEach(image => {
        environmentMaps.push(image.name)
      })
      if (this.communityName == null || this.userName == null) {
        this.$message.error('请选择所属小区和业主！')
        return false
      }
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          if (this.community !== null) {
            values.province = this.community.province
            values.city = this.community.city
            values.area = this.community.area
            values.longitude = this.community.longitude
            values.latitude = this.community.latitude
            values.communityCode = this.community.code
          }
          if (this.user !== null) {
            values.ownerCode = this.user.code
          }
          values.indoorMap = indoorMaps.length > 0 ? indoorMaps.join(',') : null
          values.housePlan = housePlans.length > 0 ? housePlans.join(',') : null
          values.environmentMap = environmentMaps.length > 0 ? environmentMaps.join(',') : null
          this.$put('/cos/house-info', {
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
