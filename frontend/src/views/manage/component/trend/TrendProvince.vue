<template>
  <a-card hoverable class="visit-count">
    <a-skeleton active v-if="loading" />
    <apexchart ref="count" type=bar height=300 :options="chartOptions" :series="series"/>
  </a-card>
</template>

<script>
export default {
  name: 'TrendProvince',
  data () {
    return {
      loading: false,
      series: [{
        data: []
      }],
      chartOptions: {
        chart: {
          height: 350,
          type: 'bar',
        },
        plotOptions: {
          bar: {
            borderRadius: 10,
            dataLabels: {
              position: 'top',
            },
          }
        },
        dataLabels: {
          enabled: true,
          formatter: function (val) {
            return val + "元";
          },
          offsetY: -20,
        },
        xaxis: {
          categories: [],
          position: 'top',
          axisBorder: {
            show: false
          },
          axisTicks: {
            show: false
          },
          tooltip: {
            enabled: true,
          }
        },
        yaxis: {
          axisBorder: {
            show: false
          },
          axisTicks: {
            show: false,
          },
          labels: {
            show: false,
            formatter: function (val) {
              return val + "元";
            }
          }
        }
      },
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    fetch () {
      this.loading = true
      this.$get('/cos/house-price-trend/trend/province/').then((r) => {
        if (r.data == null || r.data.length === 0) {
          this.loading = false
          return false
        }
        let label = []
        let value = []
        r.data.forEach(item => {
          label.push(item.name)
          value.push(item.trend)
        })
        this.series[0].data = value
        this.chartOptions.xaxis.categories = label
        setTimeout(() => {
          this.loading = false
        }, 200)
      })
    }
  }
}
</script>

<style scoped>

</style>
