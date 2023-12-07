<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-card :bordered="false" class="card-area" style="width: 1200px;margin: 15px auto 0;">
      <div style="height: 500px;">
        <div style="height: 350px;background-image: url(../../../static/img/heart-3097561_1280.png);padding: 50px">
          <div style="height: 250px;margin-top: 150px">
            <a-card :bordered="false" hoverable style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
              <a-row style="color: #404040;margin-left: 60px;margin-top: 20px">
                <a-col :span="24" style="font-size: 15px;font-family: SimHei;margin-top: 15px"><b style="font-size: 13px;">租金：</b>
                  <a style="margin-right: 15px">1000以下</a>
                  <a style="margin-right: 15px">1000~1500</a>
                  <a style="margin-right: 15px">1500~2000</a>
                  <a style="margin-right: 15px">2000~2500</a>
                  <a style="margin-right: 15px">2500~3000</a>
                  <a style="margin-right: 15px">3000~4000</a>
                  <a style="margin-right: 15px">4000~5000</a>
                  <a style="margin-right: 15px">5000~6000</a>
                  <a style="margin-right: 15px">6000~8000</a>
                  <a style="margin-right: 15px">8000~15000</a>
                </a-col>
                <a-col :span="24" style="font-size: 15px;font-family: SimHei;margin-top: 15px"><b style="font-size: 13px;">房型：</b>
                  <a style="margin-right: 15px">全部</a>
                  <a style="margin-right: 15px">一室</a>
                  <a style="margin-right: 15px">二室</a>
                  <a style="margin-right: 15px">三室</a>
                  <a style="margin-right: 15px">四室</a>
                  <a style="margin-right: 15px">五室以上</a>
                </a-col>
                <a-col :span="24" style="font-size: 15px;font-family: SimHei;margin-top: 15px"><b style="font-size: 13px;">类型：</b>
                  <a style="margin-right: 15px">全部</a>
                  <a style="margin-right: 15px">整租</a>
                  <a style="margin-right: 15px">合租</a>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </div>
      <div style="padding: 50px;padding-top: 0;margin: 15px auto 0;">
        <a-list item-layout="vertical" size="large" :data-source="rentList">
          <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
            <img
              slot="extra"
              width="272"
              alt="logo"
              :src="'http://127.0.0.1:9527/imagesWeb/' + item.roomPictures.split(',')[0]"
            />
            <a-list-item-meta :description="item.communityName">
              <a slot="title">{{ item.title }}</a>
            </a-list-item-meta>
            <div style="font-size: 12px;font-family: SimHei;">
              {{ item.rentRemark }}
            </div>
            <br/>
            <div style="font-size: 12px;font-family: SimHei;">
              租金：<span style="color: red">{{ item.rentPrice }} 元</span>
              <a-tag v-if="item.rentType == 1">整租</a-tag>
              <a-tag v-if="item.rentType == 2">合租</a-tag>
              <a-tag>{{ item.roomNumber }}室</a-tag>
            </div>
          </a-list-item>
        </a-list>
      </div>
    </a-card>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Rent',
  data () {
    return {
      rentList: []
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  mounted () {
    this.getRentList()
  },
  methods: {
    getRentList () {
      this.$get(`/cos/rent-info/page`, {flag: '1'}).then((r) => {
        this.rentList = r.data.data.records
      })
    }
  }
}
</script>

<style scoped>
  >>> .ant-card-body {
    padding: 0;
  }

  >>> .ant-list-item-meta-title {
    font-weight: 700;
    font-size: 14px;
    font-family: SimHei;
  }
</style>
