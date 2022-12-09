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
      noticeId: null,
      detailFlag: false,
      pagination: {
        pageSize: 8
      }
    }
  },
  mounted () {
    this.getNews()
  },
  methods: {
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
</style>
