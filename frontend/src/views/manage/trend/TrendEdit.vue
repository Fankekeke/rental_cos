<template>
  <a-drawer title="修改房价走势" :maskClosable="false" width=900 placement="right" :closable="false" @close="onClose"
    :visible="trendEditVisiable" style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='所属小区'>
            <a-input-search
              v-model="communityName"
              disabled
              enter-button="选择"
              @search="communityDrawer = true"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='房屋均价'>
            <a-input-number style="width: 100%" :min="1" :step="1" v-decorator="[
            'housePrice'
            ]"/>
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
import CheckCommunity from '../community/CheckCommunity'
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
  name: 'trendEdit',
  props: {
    trendEditVisiable: {
      default: false
    }
  },
  components: {CheckCommunity},
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.trendEditVisiable
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
      previewVisible: false,
      previewImage: '',
      communityDrawer: false,
      communityName: '',
      community: null
    }
  },
  methods: {
    handlerCommunityClosed (community) {
      this.communityDrawer = false
      this.communityName = community.communityName
      this.community = community
    },
    setFormValues ({...trend}) {
      this.rowId = trend.id
      let fields = ['housePrice', 'communityName']
      let obj = {}
      Object.keys(trend).forEach((key) => {
        if (key === 'communityName') {
          this.communityName = trend[key]
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = trend[key]
        }
      })
      this.form.setFieldsValue(obj)
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
        values.id = this.rowId
        if (!err) {
          this.loading = true
          this.$put('/cos/house-price-trend', {
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
