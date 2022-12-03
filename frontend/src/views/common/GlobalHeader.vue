<template>
  <a-layout-header :class="[fixHeader && 'ant-header-fixedHeader', layout === 'side' ? (sidebarOpened ? 'ant-header-side-opened' : 'ant-header-side-closed') : null, theme, 'global-header' ]">
    <div :class="['global-header-wide', layout]">
      <router-link v-if="isMobile || layout === 'head'" to="/" :class="['logo', isMobile ? null : 'pc', theme]">
        <img width="32" src="static/img/logo.png"  alt=""/>
        <h1 v-if="!isMobile">{{systemName}}</h1>
      </router-link>
      <a-divider v-if="isMobile" type="vertical" />
      <a-icon v-if="layout === 'side'" class="trigger" :type="collapsed ? 'menu-unfold' : 'menu-fold'" @click="toggleCollapse"/>
      <div v-if="layout === 'head'" class="global-header-menu">
        <i-menu style="height: 64px; line-height: 64px;" class="system-top-menu" :theme="theme" mode="horizontal" :menuData="menuData" @select="onSelect"/>
      </div>
      <div :class="['global-header-right', theme]">
        <a-badge :count="messageList.length" @click="visible = true" v-if="currentUser.roleId != 75">
          <a-icon type="bell" style="font-size: 18px"/>
        </a-badge>
        <header-avatar class="header-item"/>
      </div>
    </div>
    <a-drawer
      title="消息通知"
      placement="right"
      :closable="false"
      :visible="visible"
      width="450"
      @close="onClose"
    >
      <a-card :bordered="false" hoverable v-for="(item, index) in messageList" :key="index" style="margin-bottom: 10px">
        <a slot="extra" href="#" @click="checkMessage(item.id)" style="font-size: 13px;font-family: SimHei">确认</a>
        <span slot="title">
          <b style="font-size: 14px;font-family: SimHei">{{ item.title }}</b>
        </span>
        <div style="font-size: 13px;font-family: SimHei">{{ item.content }}</div>
        <a-col :span="24" style="font-size: 13px;margin-top: 10px;text-align: right">
          <span>{{ item.createDate }}</span>
        </a-col>
      </a-card>
    </a-drawer>
  </a-layout-header>
</template>

<script>
import HeaderAvatar from './HeaderAvatar'
import IMenu from '@/components/menu/menu'
import { mapState } from 'vuex'

export default {
  name: 'GlobalHeader',
  components: {IMenu, HeaderAvatar},
  props: ['collapsed', 'menuData'],
  computed: {
    ...mapState({
      isMobile: state => state.setting.isMobile,
      layout: state => state.setting.layout,
      systemName: state => state.setting.systemName,
      sidebarOpened: state => state.setting.sidebar.opened,
      fixHeader: state => state.setting.fixHeader,
      currentUser: state => state.account.user
    }),
    theme () {
      return this.layout === 'side' ? 'light' : this.$store.state.setting.theme
    }
  },
  data () {
    return {
      visible: false,
      messageList: []
    }
  },
  mounted() {
    this.selectMessageList()
  },
  methods: {
    checkMessage (id) {
      this.$get(`/cos/message-info/setStatus/${id}`).then((r) => {
        this.selectMessageList()
      })
    },
    selectMessageList () {
      this.$get('/cos/message-info/message/list', { userId: this.currentUser.userId, roleId: this.currentUser.roleId }).then((r) => {
        this.messageList = r.data.data
        console.log(JSON.stringify(this.messageList))
      })
    },
    onClose () {
      this.visible = false
    },
    toggleCollapse () {
      this.$emit('toggleCollapse')
    },
    onSelect (obj) {
      this.$emit('menuSelect', obj)
    }
  }
}
</script>

<style lang="less" scoped>
  .ant-card-head-title {
    padding: 0;
  }
  .trigger {
    font-size: 20px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
  .header-item{
    padding: 0 19px;
    display: inline-block;
    height: 100%;
    cursor: pointer;
    vertical-align: middle;
    i{
      font-size: 16px;
      color: rgba(0,0,0,.65);
    }
  }
  .global-header{
    padding: 0 12px 0 0;
    -webkit-box-shadow: 0 1px 4px rgba(0,21,41,.08);
    box-shadow: 0 1px 4px rgba(0,21,41,.08);
    position: relative;
    &.light{
      background: #fff;
    }
    &.dark{
      background: #393e46;
    }
    .global-header-wide{
      &.head{
        padding: 0 24px;
      }
      &.side{
      }
      .logo {
        height: 64px;
        line-height: 58px;
        vertical-align: top;
        display: inline-block;
        padding: 0 12px 0 24px;
        cursor: pointer;
        font-size: 20px;
        &.pc{
          padding: 0 12px 0 0;
        }
        img {
          display: inline-block;
          vertical-align: middle;
        }
        h1{
          display: inline-block;
          font-size: 16px;
        }
        &.dark h1{
          color: #fff;
        }
      }
      .global-header-menu{
        display: inline-block;
      }
      .global-header-right{
        float: right;
        &.dark{
          color: #fff;
          i{
            color: #fff;
          }
        }
      }
    }
  }
  .ant-header-fixedHeader {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 15;
    width: 100%;
    transition: width .2s;

    &.ant-header-side-opened {
      width: 100%;
      padding-left: 254px;
    }

    &.ant-header-side-closed {
      width: 100%;
      padding-left: 80px;
    }
  }
</style>
