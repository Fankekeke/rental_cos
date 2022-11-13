<template>
  <a-modal v-model="show" title="修改公告" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='公告标题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'title',
            { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='上传人' v-bind="formItemLayout">
            <a-input v-decorator="[
            'publisher',
            { rules: [{ required: true, message: '请输入上传人!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='公告类型' v-bind="formItemLayout">
            <a-select v-decorator="[
              'type',
              { rules: [{ required: true, message: '请输入公告类型!' }] }
              ]">
              <a-select-option value="1">画报</a-select-option>
              <a-select-option value="2">导购</a-select-option>
              <a-select-option value="3">新盘发布</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='公告状态' v-bind="formItemLayout">
            <a-select v-decorator="[
              'rackUp',
              { rules: [{ required: true, message: '请输入公告状态!' }] }
              ]">
              <a-select-option value="0">下架</a-select-option>
              <a-select-option value="1">已发布</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='公告内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
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
      previewImage: ''
    }
  },
  methods: {
    setFormValues ({...trend}) {
      this.rowId = trend.id
      let fields = ['title', 'content', 'publisher', 'rackUp', 'type']
      let obj = {}
      Object.keys(trend).forEach((key) => {
        if (key === 'rackUp' || key === 'type') {
          trend[key] = trend[key].toString()
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
          this.$put('/cos/trend-info', {
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
