<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <div>
      <div style="height: 500px;">
        <div style="height: 350px;background-image: url(../../static/img/living-room-1853203_1920.jpg);padding: 50px">
          <div style="font-size: 35px;font-weight: 500;color: white;font-family: SimHei">安居客 全房源</div>
          <div style="font-size: 22px;font-weight: 500;color: white;font-family: SimHei">让您找房更轻松</div>
          <div style="height: 250px;margin-top: 100px">
            <a-card :bordered="false" hoverable style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
              <a-row style="padding: 50px;margin: 0 auto">
                <a-col :span="16">
                  <a-input placeholder="请输入小区名称 地址" />
                </a-col>
                <a-col :span="6" :offset="2">
                  <a-button type="primary">
                    查找
                  </a-button>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </div>
      <div style="width: 1200px;margin: 0 auto;font-family: SimHei">
        <a-row style="padding-left: 24px;padding-right: 24px;margin-top: 15px" :gutter="20">
          <a-col style="margin-bottom: 15px"><span style="font-size: 18px;font-weight: 600;color: #000c17">精选小区</span></a-col>
          <a-col :span="8" v-for="(item, index) in communityList" :key="index">
            <div style="width: 100%;margin-bottom: 15px;text-align: left">
              <a-divider orientation="left">
                <span style="font-size: 12px;font-family: SimHei;">{{item.province}}{{item.city}}{{item.area}} - {{item.code}}</span>
              </a-divider>
              <a-card :bordered="false" @click="communityDetail(item)" hoverable>
                <a-card-meta :title="item.communityName" :description="item.address.slice(0, 50)+'...'"></a-card-meta>
                <div style="font-size: 12px;font-family: SimHei;margin-top: 8px">
                  <span v-if="item.propertyType == 1">公寓住宅 |</span>
                  <span v-if="item.propertyType == 2">商业物业 |</span>
                  <span v-if="item.propertyType == 3">工业物业 |</span>
                  <span style="margin-left: 2px" v-if="item.tenureCategory == 1">商品房住宅 |</span>
                  <span style="margin-left: 2px" v-if="item.tenureCategory == 2">央产房 |</span>
                  <span style="margin-left: 2px" v-if="item.tenureCategory == 3">军产房 |</span>
                  <span style="margin-left: 2px" v-if="item.tenureCategory == 4">小产权房 |</span>
                  <span style="margin-left: 2px" v-if="item.tenureCategory == 5">自建房 |</span>
                  {{ item.propertyTenure }}年
                  <a style=" font-size: 13px;float: right">详情</a>
                </div>
              </a-card>
            </div>
          </a-col>
        </a-row>
        <a-row style="padding-left: 24px;padding-right: 24px;margin-top: 15px" :gutter="20">
          <a-col style="margin-bottom: 15px"><span style="font-size: 18px;font-weight: 600;color: #000c17">热门租房</span></a-col>
          <a-col :span="8" v-for="(item, index) in rentList" :key="index">
            <div style="width: 100%;margin-bottom: 15px;text-align: left">
              <a-divider orientation="left">
                <span style="font-size: 12px;font-family: SimHei;">{{item.province}}{{item.city}}{{item.area}} - {{item.communityName}}</span>
              </a-divider>
              <a-card :bordered="false" @click="rentDetail(item)" hoverable>
                <a-carousel autoplay style="height: 150px;" v-if="item.roomPictures !== undefined && item.roomPictures !== ''">
                  <div style="width: 100%;height: 150px" v-for="(item, index) in item.roomPictures.split(',')" :key="index">
                    <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 100%">
                  </div>
                </a-carousel>
                <a-card-meta :title="item.houseAddress" :description="item.rentalRequest.slice(0, 25)+'...'" style="margin-top: 10px"></a-card-meta>
                <div style="font-size: 12px;font-family: SimHei;margin-top: 8px">
                  <span>{{ item.towards }}</span> |
                  <span style="margin-left: 2px">{{ item.roomNumber }}室{{ item.livingRoomNumber }}厅</span> |
                  <span style="margin-left: 2px" v-if="item.rentType == 1">整租</span>
                  <span style="margin-left: 2px" v-if="item.rentType == 2">合租</span>
                  <span style="color: #f5222d; font-size: 13px;float: right">{{ item.rentPrice }}元</span>
                </div>
              </a-card>
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>
<script>
import HeadInfo from '@/views/common/HeadInfo'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'HomePage',
  components: {HeadInfo},
  data () {
    return {
      series: [],
      chartOptions: {
        chart: {
          toolbar: {
            show: false
          }
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '35%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        fill: {
          opacity: 1

        }
      },
      todayIp: '',
      todayVisitCount: '',
      totalVisitCount: '',
      userRole: '',
      userDept: '',
      lastLoginTime: '',
      welcomeMessage: '',
      communityList: [],
      rentList: []
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    getRentList () {
      this.$get('/cos/rent-info/page').then((r) => {
        this.rentList = r.data.data.records
      })
    },
    getCommunityList () {
      this.$get('/cos/community-info/page').then((r) => {
        this.communityList = r.data.data.records.splice(0, 3)
      })
    }
  },
  mounted () {
    this.getCommunityList()
    this.getRentList()
  }
}
</script>
<style scoped>
  >>> .ant-card-meta-title {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-card-meta-description {
    font-size: 12px;
    font-family: SimHei;
  }
  >>> .ant-divider-with-text-left {
    margin: 0;
  }

  >>> .ant-card-head-title {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-card-extra {
    font-size: 13px;
    font-family: SimHei;
  }
  .ant-carousel >>> .slick-slide {
    text-align: center;
    height: 150px;
    line-height: 150px;
    overflow: hidden;
  }
</style>
