<template>
  <a-card :bordered="false" class="card-area" style="width: 1200px;margin: 15px auto 0;">
    <a-row :gutter="50">
      <a-col :span="16">
        <news-detail v-show="detailFlag" @back="detailFlag = false" :noticeId="noticeId"/>
        <div v-if="!detailFlag">
          <div style="padding: 30px" v-if="listData.length > 0">
            <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="listData">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
            <span key="tag">
              <a-icon type="tag" style="margin-right: 8px" />
              {{ item.date }}
            </span>
                </template>
                <a-list-item-meta>
                  <a slot="title" class="messageTitle" @click="detailInit(item.id)">{{ item.title }}</a>
                </a-list-item-meta>
                <div class="messageContent">
                  {{ item.content.slice(0, 138) }}...
                </div>
              </a-list-item>
            </a-list>
          </div>
          <div style="height: 500px" v-if="listData.length === 0">
            <a-empty :description="false" style="line-height: 500px"/>
          </div>
        </div>
      </a-col>
      <a-col :span="8">
        <p style="margin-bottom: 15px;font-size: 16px;font-weight: 600;font-family: SimHei;margin-left: 8px;margin-top: 15px">热门房源</p>
        <div style="width: 100%;margin-bottom: 15px;text-align: left" v-for="(item, index) in rentList" :key="index">
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
  </a-card>
</template>

<script>
import {mapState} from 'vuex'
import NewsDetail from './NewsDetail'

export default {
  name: 'News',
  components: {NewsDetail},
  computed: {
    ...mapState({
      user: state => state.account.user,
      client: state => state.account.client
    })
  },
  data () {
    return {
      listData: [],
      rentList: [],
      noticeId: null,
      detailFlag: false,
      pagination: {
        pageSize: 8
      }
    }
  },
  mounted () {
    this.getNews()
    this.getRentList()
  },
  methods: {
    getRentList () {
      this.$get('/cos/rent-info/page').then((r) => {
        this.rentList = r.data.data.records.splice(0, 3)
      })
    },
    detailInit (noticeId) {
      this.noticeId = noticeId
      this.detailFlag = true
    },
    getNews () {
      this.$get(`/cos/bulletin-info/list`).then((r) => {
        this.listData = r.data.data
      })
    }
  }
}
</script>

<style scoped>
  .messageTitle {
    color: #000c17;
    font-weight: 600;
    font-family: SimHei;
  }
  .messageContent {
    line-height: 1.8;
    font-family: SimHei;
  }
  >>> .ant-card-meta-title {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-card-meta-description {
    font-size: 12px;
    font-family: SimHei;
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
