<template>
  <a-drawer title="新增房价走势" :maskClosable="false" width=900 placement="right" :closable="false" @close="onClose"
    :visible="trendAddVisiable" style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='所属小区'>
            <a-input-search
              v-model="communityName"
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
import { mapState } from 'vuex'
import CheckCommunity from '../community/CheckCommunity'
import CheckUser from "../user/CheckUser";
function getBase64(file) {
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
  name: 'trendAdd',
  props: {
    trendAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.trendAddVisiable
      },
      set: function () {
      }
    }
  },
  components: {CheckCommunity},
  data() {
    return {
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
    reset() {
      this.loading = false
      this.form.resetFields()
    },
    onClose() {
      this.reset()
      this.$emit('close')
    },
    handleSubmit() {
      if (this.community === null) {
        this.$message.error('请选择小区信息')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          values.communityCode = this.community.code
          this.$post('/cos/house-price-trend', {
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
