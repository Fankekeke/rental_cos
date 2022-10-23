<template>
  <a-modal v-model="show" title="小区详情" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="communityData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>小区编号：</b>
          {{ communityData.code !== null ? communityData.code : '- -' }}
        </a-col>
        <a-col :span="8"><b>小区名称：</b>
          {{ communityData.communityName !== null ? communityData.communityName : '- -' }}
        </a-col>
        <a-col :span="8"><b>物业类型：</b>
          <span v-if="communityData.propertyType === 1">公寓住宅</span>
          <span v-if="communityData.propertyType === 2">商业物业</span>
          <span v-if="communityData.propertyType === 3">工业物业</span>
          <span v-if="communityData.propertyType == null">- -</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>竣工时间：</b>
          {{ communityData.completionTime !== null ? communityData.completionTime : '- -' }}
        </a-col>
        <a-col :span="8"><b>权属类别：</b>
          <span v-if="communityData.tenureCategory === 1">商品房住宅</span>
          <span v-if="communityData.tenureCategory === 2">央产房</span>
          <span v-if="communityData.tenureCategory === 3">军产房</span>
          <span v-if="communityData.tenureCategory === 4">小产权房</span>
          <span v-if="communityData.tenureCategory === 5">自建房</span>
          <span v-if="communityData.tenureCategory == null">- -</span>
        </a-col>
        <a-col :span="8"><b>所在地：</b>
          {{ communityData.address !== null ? communityData.address : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>产权年限：</b>
          {{ communityData.propertyTenure !== null ? communityData.propertyTenure : '- -' }}
        </a-col>
        <a-col :span="8"><b>总户数：</b>
          {{ communityData.totalHouses !== null ? communityData.totalHouses : '- -' }}
        </a-col>
        <a-col :span="8"><b>总建面积：</b>
          {{ communityData.totalConstructionArea !== null ? communityData.totalConstructionArea : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>容积率：</b>
          {{ communityData.volumeRate !== null ? communityData.volumeRate : '- -' }}
        </a-col>
        <a-col :span="8"><b>经度：</b>
          {{ communityData.longitude !== null ? communityData.longitude : '- -' }}
        </a-col>
        <a-col :span="8"><b>纬度：</b>
          {{ communityData.latitude !== null ? communityData.latitude : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">物业配套</span></a-col>
        <a-col :span="8"><b>绿化率：</b>
          {{ communityData.greeningRate !== null ? communityData.greeningRate : '- -' }}
        </a-col>
        <a-col :span="8"><b>建筑类型：</b>
          <span v-if="communityData.buildingType === 1">居住建筑</span>
          <span v-if="communityData.buildingType === 2">公共建筑</span>
          <span v-if="communityData.buildingType === 3">工业建筑</span>
          <span v-if="communityData.buildingType === 4">农业建筑</span>
          <span v-if="communityData.buildingType === 5">大量性建筑</span>
          <span v-if="communityData.buildingType == null">- -</span>
        </a-col>
        <a-col :span="8"><b>所属商圈：</b>
          {{ communityData.businessDistrict !== null ? communityData.businessDistrict : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>停车位：</b>
          {{ communityData.parkingSpace !== null ? communityData.parkingSpace : '- -' }}
        </a-col>
        <a-col :span="8"><b>统一供暖：</b>
          <span v-if="communityData.unifiedHeating === 1" style="color: green">是</span>
          <span v-if="communityData.unifiedHeating === 2" style="color: red">否</span>
          <span v-if="communityData.unifiedHeating == null">- -</span>
        </a-col>
        <a-col :span="8"><b>供水供电：</b>
          <span v-if="communityData.waterSupply === 1">民用</span>
          <span v-if="communityData.waterSupply === 2">商用</span>
          <span v-if="communityData.waterSupply == null">- -</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物业费：</b>
          {{ communityData.propertyCosts !== null ? communityData.propertyCosts : '- -' }}
        </a-col>
        <a-col :span="8"><b>停车费：</b>
          {{ communityData.parkingFee !== null ? communityData.parkingFee : '- -' }}
        </a-col>
        <a-col :span="8"><b>车位管理费：</b>
          {{ communityData.parkingManagementFee !== null ? communityData.parkingManagementFee : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物业公司：</b>
          {{ communityData.propertyCompany !== null ? communityData.propertyCompany : '- -' }}
        </a-col>
        <a-col :span="8"><b>开发商：</b>
          {{ communityData.developer !== null ? communityData.developer : '- -' }}
        </a-col>
        <a-col :span="8"><b>位置：</b>
          {{  communityData.province + communityData.city + communityData.area }}
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
  name: 'communityView',
  props: {
    communityShow: {
      type: Boolean,
      default: false
    },
    communityData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.communityShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false
    }
  },
  watch: {
    communityShow: function (value) {
      if (value) {
        setTimeout(() => {
          baiduMap.initMap('areas')
          setTimeout(() => {
            this.local(this.communityData)
          }, 500)
        }, 200)
      }
    }
  },
  methods: {
    local (community) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(community.longitude, community.latitude)
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
