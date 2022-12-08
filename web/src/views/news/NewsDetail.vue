<template>
  <div v-if="newsInfo != null">
    <a-page-header
      :title="newsInfo.messTitle"
      :sub-title="newsInfo.sendDate"
      @back="back"
    />
    <div v-html="newsInfo.content" style="padding: 23px;font-family: SimHei;"></div>
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
</style>
