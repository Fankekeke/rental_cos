<template>
  <a-modal v-model="show" title="发送信息" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="24">
          <a-form-item label='发送对象' v-bind="formItemLayout">
            <a-radio-group v-model="record.sendUserType" button-style="solid">
              <a-radio-button value="1">
                用户
              </a-radio-button>
              <a-radio-button value="2">
                员工
              </a-radio-button>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='选择人员' v-bind="formItemLayout">
            <a-select
              v-if="record.sendUserType == 1"
              show-search
              :value="record.sendAccount"
              placeholder="input search text"
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @change="userHandleChange"
            >
              <a-select-option v-for="d in userList" :key="d.code">
                {{ d.userName }}
              </a-select-option>
            </a-select>
            <a-select
              v-if="record.sendUserType == 2"
              show-search
              :value="record.sendAccount"
              placeholder="input search text"
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @change="staffHandleChange"
            >
              <a-select-option v-for="d in staffList" :key="d.staffCode">
                {{ d.staffName }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='发送模板' v-bind="formItemLayout">
            <a-select
              show-search
              :value="record.templateId"
              placeholder="input search text"
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @change="handleChange"
            >
              <a-select-option v-for="d in templateList" :key="d.id">
                {{ d.title }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='公告内容' v-bind="formItemLayout">
            <a-textarea disabled :rows="6" v-model="record.content"/>
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
  name: 'recordAdd',
  props: {
    recordAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.recordAddVisiable
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
      templateList: [],
      userList: [],
      staffList: [],
      record: {
        templateId: '',
        sendUserType: '1',
        sendAccount: '',
        userName: '',
        email: '',
        avatar: '',
        content: ''
      }
    }
  },
  watch: {
    recordAddVisiableL: function (value) {
      if (value) {
        this.templateId = ''
        this.sendUserType = '1'
        this.sendAccount = ''
        this.userName= ''
        this.email = ''
        this.avatar = ''
        this.content = ''
      }
    }
  },
  mounted() {
    this.getTemplate()
    this.getUserList()
    this.getStaffList()
  },
  methods: {
    handleChange (value) {
      this.record.templateId = value
      this.templateList.forEach(item => {
        if (item.id === value) {
          this.record.content = item.content
        }
      })
    },
    userHandleChange (value) {
      this.record.sendAccount = value
      this.userList.forEach(item => {
        if (item.code === value) {
          this.record.userName = item.userName
          this.record.avatar = item.avatar
          this.record.email = item.email
        }
      })
    },
    staffHandleChange (value) {
      this.record.sendAccount = value
      this.staffList.forEach(item => {
        if (item.staffCode === value) {
          this.record.userName = item.staffName
          this.record.avatar = item.avatar
          this.record.email = item.email
        }
      })
    },
    getTemplate () {
      this.$get('/cos/message-template/list').then((r) => {
        this.templateList = r.data.data
      })
    },
    getUserList () {
      this.$get('/cos/user-info/list').then((r) => {
        this.userList = r.data.data
      })
    },
    getStaffList () {
      this.$get('/cos/staff-info/list').then((r) => {
        this.staffList = r.data.data
      })
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
          this.$post('/cos/message-record', this.record).then((r) => {
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
