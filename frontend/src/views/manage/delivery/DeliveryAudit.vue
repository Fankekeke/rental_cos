<template>
  <a-modal v-model="show" title="租房合同审批" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        驳回
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="rentData !== null && houseInfo !== null && community !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="已提交" />
          <a-step title="正在审核" />
          <a-step :title="currentText" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">合同信息</span></a-col>
        <a-col :span="8"><b>合同编号：</b>
          {{ deliveryInfo.contractCode !== null ? deliveryInfo.contractCode : '- -' }}
        </a-col>
        <a-col :span="8"><b>租房人：</b>
          <a-popover trigger="hover">
            <template slot="content">
              <a-avatar shape="square" size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + deliveryInfo.rentUserAvatar" />
            </template>
            <a>{{ deliveryInfo.rentUserName !== null ? deliveryInfo.rentUserName : '- -' }}</a>
          </a-popover>
        </a-col>
        <a-col :span="8"><b>房屋业主：</b>
          <a-popover trigger="hover">
            <template slot="content">
              <a-avatar shape="square" size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/' + deliveryInfo.ownerUserAvatar" />
            </template>
            <a>{{ deliveryInfo.ownerUserName !== null ? deliveryInfo.ownerUserName : '- -' }}</a>
          </a-popover>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>小区名称：</b>
          {{ deliveryInfo.communityName !== null ? deliveryInfo.communityName : '- -' }}
        </a-col>
        <a-col :span="16"><b>房屋地址：</b>
          {{ deliveryInfo.houseAddress !== null ? deliveryInfo.houseAddress : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>地址：</b>
          {{ deliveryInfo.province }} {{ deliveryInfo.city }} {{ deliveryInfo.area }}
        </a-col>
        <a-col :span="8"><b>提交时间：</b>
          {{ deliveryInfo.createDate !== null ? deliveryInfo.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>缴费方式：</b>
          <span v-if="deliveryInfo.payType == 1">押一付一</span>
          <span v-if="deliveryInfo.payType == 2">押一付三</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>居住时常：</b>
          {{ deliveryInfo.rentDay }}月
        </a-col>
        <a-col :span="8"><b>开始时间：</b>
          {{ deliveryInfo.startLive !== null ? deliveryInfo.startLive : '- -' }}
        </a-col>
        <a-col :span="8"><b>结束时间：</b>
          {{ deliveryInfo.endLive !== null ? deliveryInfo.endLive : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>每月房租：</b>
          {{ deliveryInfo.contractPrice }}元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">出租信息</span></a-col>
        <a-col :span="16"><b>出租标题：</b>
          {{ rentData.title !== null ? rentData.title : '- -' }}
        </a-col>
        <a-col :span="8"><b>租金每月：</b>
          {{ rentData.rentPrice !== null ? rentData.rentPrice : '- -' }}元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>发布时间：</b>
          {{ rentData.createDate !== null ? rentData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>出租状态：</b>
          <span v-if="rentData.flag === 1">上架</span>
          <span v-if="rentData.flag === 2">下架</span>
          <span v-if="rentData.flag === 2">已被出租</span>
        </a-col>
        <a-col :span="8"><b>合租类型：</b>
          <span v-if="rentData.rentType === 1">整租</span>
          <span v-if="rentData.rentType === 2">合租</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>发布时间：</b>
          {{ rentData.createDate !== null ? rentData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>访问量：</b>
          {{ rentData.views !== null ? rentData.views : '- -' }}次
        </a-col>
        <a-col :span="8"><b>房间类型：</b>
          <span v-if="rentData.roomType === 1">主卧</span>
          <span v-if="rentData.roomType === 2">次卧</span>
        </a-col>
      </a-row>
      <br/>
      <a-row :gutter="10" style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">包含设施</span></a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasBed == 1 ? 'green' : 'red'}">床</div>
          <img src="http://127.0.0.1:9527/imagesWeb/chuang.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasRefrigerator == 1 ? 'green' : 'red'}">冰箱</div>
          <img src="http://127.0.0.1:9527/imagesWeb/dankaimenbingxiang.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasAirConditioner == 1 ? 'green' : 'red'}">空调</div>
          <img src="http://127.0.0.1:9527/imagesWeb/kongtiao.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasBroadband == 1 ? 'green' : 'red'}">宽带</div>
          <img src="http://127.0.0.1:9527/imagesWeb/kuandai.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasBalcony == 1 ? 'green' : 'red'}">阳台</div>
          <img src="http://127.0.0.1:9527/imagesWeb/loutianyangtai.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasGasStoves == 1 ? 'green' : 'red'}">燃气灶</div>
          <img src="http://127.0.0.1:9527/imagesWeb/ranqizao.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasWaterHeater == 1 ? 'green' : 'red'}">热水器</div>
          <img src="http://127.0.0.1:9527/imagesWeb/reshuiqi.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasSofa == 1 ? 'green' : 'red'}">沙发</div>
          <img src="http://127.0.0.1:9527/imagesWeb/shafa_1.png">
        </a-col>
        <a-col :span="24" style="margin: 20px 0px"></a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasCook == 1 ? 'green' : 'red'}">做饭</div>
          <img src="http://127.0.0.1:9527/imagesWeb/weibolu.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasBathroom == 1 ? 'green' : 'red'}">卫生间</div>
          <img src="http://127.0.0.1:9527/imagesWeb/weiyu.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasTelevision == 1 ? 'green' : 'red'}">电视</div>
          <img src="http://127.0.0.1:9527/imagesWeb/dianshi.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasWashingMachine == 1 ? 'green' : 'red'}">洗衣机</div>
          <img src="http://127.0.0.1:9527/imagesWeb/xiyiji.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasWardrobe == 1 ? 'green' : 'red'}">衣柜</div>
          <img src="http://127.0.0.1:9527/imagesWeb/yigui.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasRangeHood == 1 ? 'green' : 'red'}">油烟机</div>
          <img src="http://127.0.0.1:9527/imagesWeb/youyanji.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasHeating == 1 ? 'green' : 'red'}">暖气</div>
          <img src="http://127.0.0.1:9527/imagesWeb/zaotai.png">
        </a-col>
        <a-col :span="3" style="text-align: center">
          <div :style="{color: rentData.hasDoorLock == 1 ? 'green' : 'red'}">智能门锁</div>
          <img src="http://127.0.0.1:9527/imagesWeb/zhinengmensuo.png">
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">房间图片</span></a-col>
        <span v-if="fileList.length === 0">暂无图片</span>
        <a-upload
          name="avatar"
          action="http://127.0.0.1:9527/file/fileUpload/"
          list-type="picture-card"
          :file-list="fileList"
          @preview="handlePreview"
          @change="picHandleChange"
        >
        </a-upload>
        <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
          <img alt="example" style="width: 100%" :src="previewImage" />
        </a-modal>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">租金备注</span></a-col>
        {{ rentData.rentRemark !== null ? rentData.rentRemark : '- -' }}元
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">出租要求</span></a-col>
        {{ rentData.rentalRequest !== null ? rentData.rentalRequest : '- -' }}元
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">房间信息</span></a-col>
        <a-col :span="8"><b>小区编号：</b>
          {{ community.code !== null ? community.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>房间数量：</b>
          {{ houseInfo.roomNumber !== null ? houseInfo.roomNumber : '- -' }}
        </a-col>
        <a-col :span="8"><b>客厅数量：</b>
          {{ houseInfo.livingRoomNumber !== null ? houseInfo.livingRoomNumber : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>卫生间数量：</b>
          {{ houseInfo.bathroomNumber !== null ? houseInfo.bathroomNumber : '- -' }}
        </a-col>
        <a-col :span="8"><b>装修类型：</b>
          <span v-if="houseInfo.decorationType === 1">精装修</span>
          <span v-if="houseInfo.decorationType === 2">普通装修</span>
          <span v-if="houseInfo.decorationType === 3">暂无装修</span>
        </a-col>
        <a-col :span="8"><b>所在地：</b>
          {{ houseInfo.address !== null ? houseInfo.address : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>房间面积：</b>
          {{ houseInfo.roomSize !== null ? houseInfo.roomSize : '- -' }}
        </a-col>
        <a-col :span="8"><b>楼层：</b>
          {{ houseInfo.floor !== null ? houseInfo.floor : '- -' }}
        </a-col>
        <a-col :span="8"><b>房屋类型：</b>
          <span v-if="houseInfo.houseType == 1">普通住宅</span>
          <span v-if="houseInfo.houseType == 2">高层楼</span>
          <span v-if="houseInfo.houseType == 3">别墅</span>
          <span v-if="houseInfo.houseType == 3">大平层</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>朝向：</b>
          {{ houseInfo.towards !== null ? houseInfo.towards : '- -' }}
        </a-col>
        <a-col :span="8"><b>经度：</b>
          {{ houseInfo.longitude !== null ? houseInfo.longitude : '- -' }}
        </a-col>
        <a-col :span="8"><b>纬度：</b>
          {{ houseInfo.latitude !== null ? houseInfo.latitude : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>标签：</b>
          {{ houseInfo.tags !== null ? houseInfo.tags : '- -' }}
        </a-col>
        <a-col :span="8"><b>所属小区：</b>
          {{ community.communityName !== null ? community.communityName : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="community != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">所属物业配套</span></a-col>
        <a-col :span="8"><b>绿化率：</b>
          {{ community.greeningRate !== null ? community.greeningRate : '- -' }}
        </a-col>
        <a-col :span="8"><b>建筑类型：</b>
          <span v-if="community.buildingType === 1">居住建筑</span>
          <span v-if="community.buildingType === 2">公共建筑</span>
          <span v-if="community.buildingType === 3">工业建筑</span>
          <span v-if="community.buildingType === 4">农业建筑</span>
          <span v-if="community.buildingType === 5">大量性建筑</span>
          <span v-if="community.buildingType == null">- -</span>
        </a-col>
        <a-col :span="8"><b>所属商圈：</b>
          {{ community.businessDistrict !== null ? community.businessDistrict : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="community != null">
        <a-col :span="8"><b>停车位：</b>
          {{ community.parkingSpace !== null ? community.parkingSpace : '- -' }}
        </a-col>
        <a-col :span="8"><b>统一供暖：</b>
          <span v-if="community.unifiedHeating === 1" style="color: green">是</span>
          <span v-if="community.unifiedHeating === 2" style="color: red">否</span>
          <span v-if="community.unifiedHeating == null">- -</span>
        </a-col>
        <a-col :span="8"><b>供水供电：</b>
          <span v-if="community.waterSupply === 1">民用</span>
          <span v-if="community.waterSupply === 2">商用</span>
          <span v-if="community.waterSupply == null">- -</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="community != null">
        <a-col :span="8"><b>物业费：</b>
          {{ community.propertyCosts !== null ? community.propertyCosts : '- -' }}
        </a-col>
        <a-col :span="8"><b>停车费：</b>
          {{ community.parkingFee !== null ? community.parkingFee : '- -' }}
        </a-col>
        <a-col :span="8"><b>车位管理费：</b>
          {{ community.parkingManagementFee !== null ? community.parkingManagementFee : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="community != null">
        <a-col :span="8"><b>物业公司：</b>
          {{ community.propertyCompany !== null ? community.propertyCompany : '- -' }}
        </a-col>
        <a-col :span="8"><b>开发商：</b>
          {{ community.developer !== null ? community.developer : '- -' }}
        </a-col>
        <a-col :span="8"><b>位置：</b>
          {{  community.province + community.city + community.area }}
        </a-col>
      </a-row>
      <br/>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'

export default {
  name: 'DeliveryAudit',
  props: {
    rentAuditVisiable: {
      default: false
    },
    deliveryInfo: {
      type: Object,
      default: null
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.rentAuditVisiable
      },
      set: function () {
      }
    }
  },
  watch: {
    rentAuditVisiable: function (value) {
      if (value) {
        switch (this.deliveryInfo.step) {
          case '1':
            this.current = 1
            break
          case '2':
            this.current = 2
            this.currentText = '审核通过'
            break
          case '3':
            this.current = 2
            this.currentText = '审核驳回'
            break
        }
        this.getChargeDetail(this.deliveryInfo.chargeId)
      }
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      rentData: null,
      chargeInfo: null,
      community: null,
      houseInfo: null,
      current: 0,
      currentText: '审核结果'
    }
  },
  methods: {
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
    getChargeDetail (chargeId) {
      this.$get(`/cos/rent-charge/detail/${chargeId}`).then((r) => {
        this.chargeInfo = r.data.data
        this.getHouseInfo(this.chargeInfo.houseCode)
        this.getRentInfo(this.chargeInfo.rentId)
      })
    },
    getCommunity (communityCode) {
      this.$get(`/cos/community-info/detail/${communityCode}`).then((r) => {
        this.community = r.data.data
      })
    },
    getHouseInfo (houseCode) {
      this.$get(`/cos/house-info/detail/${houseCode}`).then((r) => {
        this.houseInfo = r.data.data
        this.getCommunity(this.houseInfo.communityCode)
      })
    },
    getRentInfo (rentId) {
      this.$get(`/cos/rent-info/detail/${rentId}`).then((r) => {
        this.rentData = r.data.data
      })
    },
    onClose () {
      this.$emit('close')
    },
    handleSubmit () {

    }
  }
}
</script>

<style scoped>

</style>
