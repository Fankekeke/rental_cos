<template>
  <div>
    <div style="display: inline-block; height: 100%; vertical-align: initial">
      <span style="cursor: pointer">
        <a-popover placement="bottom">
          <template slot="content">
            <div style="font-size: 12px">
              <a-menu slot="overlay">
                <a-menu-item key="0" style="font-size: 13px;font-family: SimHei">
                  <a @click="openProfile"><a-icon type="user" />个人信息</a>
                </a-menu-item>
                <a-menu-item key="1" style="font-size: 13px;font-family: SimHei">
                  <a @click="openMessage"><a-icon type="message" />我的消息</a>
                </a-menu-item>
                <a-menu-item key="3" style="font-size: 13px;font-family: SimHei">
                  <a @click="openApartment"><a-icon type="apartment" />我的租房</a>
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="4" style="font-size: 13px;font-family: SimHei">
                  <a @click="logout"><a-icon type="export" style="color: red"/>退出登陆</a>
                </a-menu-item>
              </a-menu>
            </div>
          </template>
          <a-badge dot>
            <a-avatar shape="square" :src="avatar" icon="user"/>
          </a-badge>
        </a-popover>
      </span>
    </div>
    <update-password
      @success="handleUpdate"
      @cancel="handleCancelUpdate"
      :user="user"
      :updatePasswordModelVisible="updatePasswordModelVisible">
    </update-password>
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'
import UpdatePassword from '../personal/UpdatePassword'

export default {
  name: 'HeaderAvatar',
  components: {UpdatePassword},
  data () {
    return {
      updatePasswordModelVisible: false
    }
  },
  computed: {
    ...mapState({
      settingBar: state => state.setting.settingBar.opened,
      user: state => state.account.user
    }),
    avatar () {
      return `static/avatar/${this.user.avatar}`
    }
  },
  methods: {
    handleSettingClick () {
      this.setSettingBar(!this.settingBar)
    },
    openProfile () {
      this.$router.push('/personal')
    },
    openMessage () {
      this.$router.push('/message')
    },
    openApartment () {
      this.$router.push('/rent')
    },
    updatePassword () {
      this.updatePasswordModelVisible = true
    },
    handleCancelUpdate () {
      this.updatePasswordModelVisible = false
    },
    handleUpdate () {
      this.updatePasswordModelVisible = false
      this.$message.success('更新密码成功，请重新登录系统')
      setTimeout(() => {
        this.logout()
      }, 1500)
    },
    logout () {
      this.$get(`logout/${this.user.id}`).then(() => {
        return new Promise((resolve, reject) => {
          this.$db.clear()
          location.reload()
        })
      }).catch(() => {
        this.$message.error('退出系统失败')
      })
    },
    ...mapMutations({setSettingBar: 'setting/setSettingBar'})
  }
}
</script>

<style lang="less" scoped>
  .ant-avatar-sm {
    width: 30px;
    height: 30px;
  }
  .avatar {
    margin: 20px 4px 20px 0;
    color: #1890ff;
    background: hsla(0, 0%, 100%, .85);
    vertical-align: middle;
  }
  .curr-user {
    font-weight: 600;
    margin-left: 6px
  }
</style>
