<template>
  <div style="height:85vh;width: 100%">
    <a-row :gutter="15" style="height: 100%;">
      <a-col :span="18" style="height: 100%;">
        <div id="areas" style="width: 100%;height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
      </a-col>
      <a-col :span="6" style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
        <div>
          <div v-if="scenicShow" class="scenicInfo" style="height: 670px; overflow-y: auto">
            <img :src="'http://127.0.0.1:9527/imagesWeb/'+scenicData.images.split(',')[0]" alt="" width="100%"
                 style="height: 180px;object-fit: cover">
            <a-card :title="scenicData.name">
              <a slot="extra" @click="scenicBack">返回</a>
              <a-tabs default-active-key="1">
                <a-tab-pane key="1" tab="基础信息">
                  <ul>
                    <li>地址：{{ scenicData.address }}</li>
                    <br/>
                    <li>等级：{{ scenicData.level }}</li>
                    <br/>
                    <li>开园时间：{{ scenicData.startDate }} ~ {{ scenicData.endDate }}</li>
                    <br/>
                    <li>门票：{{ scenicData.price }} 元</li>
                    <br/>
                    <li>景区特色：{{ scenicData.feature }}</li>
                  </ul>
                </a-tab-pane>
                <a-tab-pane key="2" tab="路线规划">
                  <a-timeline>
                    <a-timeline-item v-for="(item,index) in roadData" :key="index">
                      <div v-html="item"></div>
                    </a-timeline-item>
                  </a-timeline>
                </a-tab-pane>
              </a-tabs>
            </a-card>
          </div>
          <a-tabs default-active-key="1" v-if="!scenicShow && !hotelShow">
            <a-tab-pane key="1" tab="景点" style="height: 600px; overflow-y: auto">
              <a-card @click="scenicDetail(item)" hoverable style="width: 100%;margin-bottom: 15px"
                      v-for="(item, index) in scenicList" :key="index">
                <a-popover v-if="item.images !== null" v-for="(item1, index1) in item.images.split(',')" :key="index1">
                  <template slot="content">
                    <a-avatar shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/'+item1"/>
                  </template>
                  <a-avatar shape="square" :size="70" icon="user" style="margin-bottom: 15px;margin-right: 10px" :src="'http://127.0.0.1:9527/imagesWeb/'+item1"/>
                </a-popover>
                <a-card-meta :title="item.name" :description="item.feature.slice(0, 25)+'...'">
                </a-card-meta>
              </a-card>
            </a-tab-pane>
            <a-tab-pane key="2" tab="民宿">
              <a-card  @click="hotelDetail(item)" hoverable style="width: 100%;margin-bottom: 15px" v-for="(item, index) in hotelList" :key="index" v-if="item.images !== null">
                <a-popover v-if="item.images !== null" v-for="(item1, index1) in item.images.split(',')" :key="index1">
                  <template slot="content">
                    <a-avatar shape="square" :size="132" icon="user" :src="'http://127.0.0.1:9527/imagesWeb/'+item1"/>
                  </template>
                  <a-avatar shape="square" :size="70" icon="user" style="margin-bottom: 15px;margin-right: 10px" :src="'http://127.0.0.1:9527/imagesWeb/'+item1"/>
                </a-popover>
                <a-card-meta :title="item.name" :description="item.content.slice(0, 25)+'...'">
                </a-card-meta>
              </a-card>
            </a-tab-pane>
          </a-tabs>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
export default {
  name: 'Map',
  data () {
    return {
      listData: [],
      pagination: {
        pageSize: 3
      },
      actions: [
        { type: 'star-o', text: '156' },
        { type: 'like-o', text: '156' },
        { type: 'message', text: '2' },
      ],
      scenicList: [],
      hotelList: [],
      scenicShow: false,
      scenicData: null,
      hotelShow: false,
      hotelData: null,
      checkedList: ['Apple', 'Orange'],
      indeterminate: true,
      checkAll: false,
      plainOptions: ['Apple', 'Pear', 'Orange']
    }
  },
  mounted () {
    baiduMap.initMap('areas')
  },
  methods: {
    onChange (checkedList) {
      this.indeterminate = !!this.checkedList.length && this.checkedList.length < this.plainOptions.length
      this.checkAll = this.checkedList.length === this.plainOptions.length
    },
    onCheckAllChange (e) {
      Object.assign(this, {
        checkedList: e.target.checked ? this.plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked
      })
    }
  }
}
</script>

<style scoped>

</style>
