<template>
  <div v-if="newsInfo != null">
    <a-card :bordered="false">
      <div @click="back" slot="title">
        <a @click="back" href="#">返回</a>
        <span style="margin-left: 10px;font-size: 16px;font-weight: 600">{{ newsInfo.title }}</span>
      </div>
      <div v-html="newsInfo.content" style="padding: 23px;font-family: SimHei;"></div>
    </a-card>
  </div>
</template>

<script>
export default {
  name: 'NewsDetail',
  data () {
    return {
      newsInfo: null
    }
  },
  props: {
    noticeId: {
      type: Number
    }
  },
  watch: {
    noticeId: function (val) {
      if (val) {
        this.getNewsDetail(val)
      }
    }
  },
  methods: {
    getNewsDetail (noticeId) {
      this.$get(`/cos/bulletin-info/${noticeId}`).then((r) => {
        this.newsInfo = r.data.data
      })
    },
    back () {
      this.$emit('back')
    }
  }
}
</script>

<style scoped>
  /deep/ .ant-page-header-heading-title {
    letter-spacing: 1px;
    font-family: SimHei;
  }
  /deep/ .ant-card-head-title {
    font-size: 14px;
    font-family: SimHei;
  }
</style>
