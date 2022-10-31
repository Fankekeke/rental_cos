<template>
  <a-modal v-model="show" title="小区详情" @cancel="onClose" :width="900">
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
        <a-col :span="8"><b>小区名称：</b>
          {{ houseData.houseName !== null ? houseData.houseName : '- -' }}
        </a-col>
        <a-col :span="8"><b>物业类型：</b>
          <span v-if="houseData.propertyType === 1">公寓住宅</span>
          <span v-if="houseData.propertyType === 2">商业物业</span>
          <span v-if="houseData.propertyType === 3">工业物业</span>
          <span v-if="houseData.propertyType == null">- -</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>竣工时间：</b>
          {{ houseData.completionTime !== null ? houseData.completionTime : '- -' }}
        </a-col>
        <a-col :span="8"><b>权属类别：</b>
          <span v-if="houseData.tenureCategory === 1">商品房住宅</span>
          <span v-if="houseData.tenureCategory === 2">央产房</span>
          <span v-if="houseData.tenureCategory === 3">军产房</span>
          <span v-if="houseData.tenureCategory === 4">小产权房</span>
          <span v-if="houseData.tenureCategory === 5">自建房</span>
          <span v-if="houseData.tenureCategory == null">- -</span>
        </a-col>
        <a-col :span="8"><b>所在地：</b>
          {{ houseData.address !== null ? houseData.address : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>产权年限：</b>
          {{ houseData.propertyTenure !== null ? houseData.propertyTenure : '- -' }}
        </a-col>
        <a-col :span="8"><b>总户数：</b>
          {{ houseData.totalHouses !== null ? houseData.totalHouses : '- -' }}
        </a-col>
        <a-col :span="8"><b>总建面积：</b>
          {{ houseData.totalConstructionArea !== null ? houseData.totalConstructionArea : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>容积率：</b>
          {{ houseData.volumeRate !== null ? houseData.volumeRate : '- -' }}
        </a-col>
        <a-col :span="8"><b>经度：</b>
          {{ houseData.longitude !== null ? houseData.longitude : '- -' }}
        </a-col>
        <a-col :span="8"><b>纬度：</b>
          {{ houseData.latitude !== null ? houseData.latitude : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">物业配套</span></a-col>
        <a-col :span="8"><b>绿化率：</b>
          {{ houseData.greeningRate !== null ? houseData.greeningRate : '- -' }}
        </a-col>
        <a-col :span="8"><b>建筑类型：</b>
          <span v-if="houseData.buildingType === 1">居住建筑</span>
          <span v-if="houseData.buildingType === 2">公共建筑</span>
          <span v-if="houseData.buildingType === 3">工业建筑</span>
          <span v-if="houseData.buildingType === 4">农业建筑</span>
          <span v-if="houseData.buildingType === 5">大量性建筑</span>
          <span v-if="houseData.buildingType == null">- -</span>
        </a-col>
        <a-col :span="8"><b>所属商圈：</b>
          {{ houseData.businessDistrict !== null ? houseData.businessDistrict : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>停车位：</b>
          {{ houseData.parkingSpace !== null ? houseData.parkingSpace : '- -' }}
        </a-col>
        <a-col :span="8"><b>统一供暖：</b>
          <span v-if="houseData.unifiedHeating === 1" style="color: green">是</span>
          <span v-if="houseData.unifiedHeating === 2" style="color: red">否</span>
          <span v-if="houseData.unifiedHeating == null">- -</span>
        </a-col>
        <a-col :span="8"><b>供水供电：</b>
          <span v-if="houseData.waterSupply === 1">民用</span>
          <span v-if="houseData.waterSupply === 2">商用</span>
          <span v-if="houseData.waterSupply == null">- -</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物业费：</b>
          {{ houseData.propertyCosts !== null ? houseData.propertyCosts : '- -' }}
        </a-col>
        <a-col :span="8"><b>停车费：</b>
          {{ houseData.parkingFee !== null ? houseData.parkingFee : '- -' }}
        </a-col>
        <a-col :span="8"><b>车位管理费：</b>
          {{ houseData.parkingManagementFee !== null ? houseData.parkingManagementFee : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>物业公司：</b>
          {{ houseData.propertyCompany !== null ? houseData.propertyCompany : '- -' }}
        </a-col>
        <a-col :span="8"><b>开发商：</b>
          {{ houseData.developer !== null ? houseData.developer : '- -' }}
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
      loading: false
    }
  },
  watch: {
    houseShow: function (value) {
      if (value) {
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
