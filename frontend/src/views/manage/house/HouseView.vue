<template>
  <a-modal v-model="show" title="房屋详情" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="houseData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>小区编号：</b>
          {{ houseData.code !== null ? houseData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>房间数量：</b>
          {{ houseData.roomNumber !== null ? houseData.roomNumber : '- -' }}
        </a-col>
        <a-col :span="8"><b>客厅数量：</b>
          {{ houseData.livingRoomNumber !== null ? houseData.livingRoomNumber : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>卫生间数量：</b>
          {{ houseData.bathroomNumber !== null ? houseData.bathroomNumber : '- -' }}
        </a-col>
        <a-col :span="8"><b>装修类型：</b>
          <span v-if="houseData.decorationType === 1">精装修</span>
          <span v-if="houseData.decorationType === 2">普通装修</span>
          <span v-if="houseData.decorationType === 3">暂无装修</span>
        </a-col>
        <a-col :span="8"><b>所在地：</b>
          {{ houseData.address !== null ? houseData.address : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>房间面积：</b>
          {{ houseData.roomSize !== null ? houseData.roomSize : '- -' }}
        </a-col>
        <a-col :span="8"><b>楼层：</b>
          {{ houseData.floor !== null ? houseData.floor : '- -' }}
        </a-col>
        <a-col :span="8"><b>房屋类型：</b>
          <span v-if="houseData.houseType === 1">普通住宅</span>
          <span v-if="houseData.houseType === 2">高层楼</span>
          <span v-if="houseData.houseType === 3">别墅</span>
          <span v-if="houseData.houseType === 3">大平层</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>朝向：</b>
          {{ houseData.towards !== null ? houseData.towards : '- -' }}
        </a-col>
        <a-col :span="8"><b>经度：</b>
          {{ houseData.longitude !== null ? houseData.longitude : '- -' }}
        </a-col>
        <a-col :span="8"><b>纬度：</b>
          {{ houseData.latitude !== null ? houseData.latitude : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>标签：</b>
          {{ houseData.tags !== null ? houseData.tags : '- -' }}
        </a-col>
        <a-col :span="8"><b>所属小区：</b>
          {{ houseData.communityName !== null ? houseData.communityName : '- -' }}
        </a-col>
        <a-col :span="8"><b>所属业主：</b>
          {{ houseData.userName !== null ? houseData.userName : '- -' }}
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
          {{  houseData.province + houseData.city + houseData.area }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="15">
        <a-col style="margin-bottom: 8px"><span style="font-size: 15px;font-weight: 650;color: #000c17">详情位置</span></a-col>
      </a-row>
      <div>
        <a-card :bordered="false" style="height: 400px">
          <div id="areas" style="width: 100%;height: 350px;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
        </a-card>
      </div>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import baiduMap from '@/utils/map/baiduMap'
moment.locale('zh-cn')
export default {
  name: 'houseView',
  props: {
    houseShow: {
      type: Boolean,
      default: false
    },
    houseData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.houseShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      community: null
    }
  },
  watch: {
    houseShow: function (value) {
      if (value) {
        this.getCommunity(this.houseData.communityCode)
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.houseData)
          }, 500)
        }, 200)
      }
    }
  },
  methods: {
    getCommunity (communityCode) {
      this.$get(`/cos/community-info/detail/${communityCode}`).then((r) => {
        console.log(r.data)
        this.community = r.data.data
      })
    },
    local (house) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(house.longitude, house.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
  /deep/ .ant-table-tbody {
    font-size: 12px;
  }
  /deep/ .ant-table-thead {
    font-size: 13px;
  }
</style>
