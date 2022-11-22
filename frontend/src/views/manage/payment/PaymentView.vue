<template>
  <a-modal v-model="show" title="缴费详情" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="paymentData !== null && contractInfo != null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">合同信息</span></a-col>
        <a-col :span="8"><b>租房用户：</b>
          {{ contractInfo.rentUserName }}
        </a-col>
        <a-col :span="8"><b>所属业主：</b>
          {{ contractInfo.ownerUserName }}
        </a-col>
        <a-col :span="8"><b>小区名称：</b>
          {{ contractInfo.communityName }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="16"><b>房屋地址：</b>
          {{ contractInfo.houseAddress }}
        </a-col>
        <a-col :span="8"><b>居住时间：</b>
          {{ contractInfo.rentDay }} 月
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>开始住房时间：</b>
          {{ contractInfo.startLive }}
        </a-col>
        <a-col :span="8"><b>住房结束时间：</b>
          {{ contractInfo.endLive }}
        </a-col>
        <a-col :span="8"><b>合同类型：</b>
          <span v-if="contractInfo.parentId == null">正常</span>
          <span v-else>续租</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>缴费方式：</b>
          <span v-if="contractInfo.payType == 1">押一付一</span>
          <span v-if="contractInfo.payType == 2">押一付三</span>
        </a-col>
        <a-col :span="8"><b>合同金额：</b>
          {{ contractInfo.contractPrice }}元 /月
        </a-col>
        <a-col :span="8"><b>合同编号：</b>
          {{ contractInfo.contractCode }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">缴费信息</span></a-col>
        <a-col :span="8"><b>本次缴费金额：</b>
          {{ paymentData.amount }}元
        </a-col>
        <a-col :span="8"><b>可供时间：</b>
          {{ paymentData.rentDay }}月
        </a-col>
        <a-col :span="8"><b>缴费类型：</b>
          <span v-if="paymentData.paymentType == 1">房租</span>
          <span v-if="paymentData.paymentType == 2">押金</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>缴费时间：</b>
          {{ paymentData.createDate }}
        </a-col>
        <a-col :span="8"><b>本次开始时间：</b>
          {{ paymentData.startDate }}
        </a-col>
        <a-col :span="8"><b>本次结束时间：</b>
          {{ paymentData.endDate }}
        </a-col>
      </a-row>
      <br/>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'paymentView',
  props: {
    paymentShow: {
      type: Boolean,
      default: false
    },
    paymentData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.paymentShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      contractInfo: null
    }
  },
  watch: {
    paymentShow: function (value) {
      if (value) {
        this.dataInit(this.paymentData.contractCode)
      }
    }
  },
  methods: {
    dataInit (contractCode) {
      this.$get(`/cos/delivery-review/download/contract/${contractCode}`).then((r) => {
        this.contractInfo = r.data.data
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
