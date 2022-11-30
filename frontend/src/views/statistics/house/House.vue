<template>
  <div style="width: 100%">
    <a-row style="margin-top: 25px">
      <a-col :span="8">
        <a-radio-group button-style="solid" style="width: 100%">
          <a-radio-button value="1" style="width: 25%;text-align: center">
            房价统计
          </a-radio-button>
          <a-radio-button value="2" style="width: 25%;text-align: center">
            房源状态
          </a-radio-button>
          <a-radio-button value="3" style="width: 25%;text-align: center">
            员工任务
          </a-radio-button>
        </a-radio-group>
      </a-col>
      <a-col :span="24" style="margin-top: 25px">
        <a-row :gutter="15">
          <a-col :span="24">
            <apexchart type="radar" height="350" :options="chartOptions" :series="series"></apexchart>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'House',
  data () {
    return {
      series: [{
        name: 'Series 1',
        data: [80, 50, 30, 40, 100, 20],
      }],
      chartOptions: {
        chart: {
          height: 350,
          type: 'radar',
        },
        title: {
          text: 'Basic Radar Chart'
        },
        xaxis: {
          categories: ['January', 'February', 'March', 'April', 'May', 'June']
        }
      },
    }
  }
}
</script>

<style scoped>

</style>
