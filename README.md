#### 基于SpringBoot的租房平台

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

协同消息，公告信息，小区管理，房屋管理，房价走势，发帖记录，房屋出租，消息回复，城市管理，用户管理，地图看板，综合统计，消息模板，发送记录，员工管理，消息通知，缴费记录，交付审批，我的任务，房屋出租，已售房屋，用户收藏，房屋收藏，小区收藏，用户评价，租房评价，员工评价


#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 后端启动方式

1.首先启动redis，进入redis目录终端。输入redis-server回车
2.导入sql文件，修改数据库与redis连接配置
3.idea中启动后端项目

#### 默认后台账户密码
[管理员]
system
1234qwer
