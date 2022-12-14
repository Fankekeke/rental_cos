<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-card :bordered="false" class="card-area" style="width: 1200px;margin: 15px auto 0;">
      <div style="height: 500px;">
        <div style="height: 350px;background-image: url(../../../static/img/living-room-1853203_1920.jpg);padding: 50px">
          <div style="font-size: 35px;font-weight: 500;color: white;font-family: SimHei">安居客 全房源</div>
          <div style="font-size: 22px;font-weight: 500;color: white;font-family: SimHei">让您找房更轻松</div>
          <div style="height: 250px;margin-top: 100px">
            <a-card :bordered="false" hoverable style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
              <a-row style="color: #404040;margin-left: 60px;margin-top: 20px">
                <a-col :span="24"><b style="font-size: 13px;font-family: SimHei">位置：</b>全部</a-col>
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
                <a-col :span="24" style="font-size: 15px;font-family: SimHei;margin-top: 15px"><b style="font-size: 13px;">更多筛选：</b>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
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
    }
  },
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  }
}
</script>

<style scoped>
  >>> .ant-card-body {
    padding: 0;
  }
</style>
