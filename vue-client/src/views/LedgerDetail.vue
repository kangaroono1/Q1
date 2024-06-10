<template>
  <!-- View 页头：时间选择器 -->
  <div class="ledger-detail-header">
    日期：
    <el-select
      v-model="selecterValue"
      placeholder="请选择时间范围"
      default
      style="width: 240px"
    >
      <el-option
        v-for="item in timeRangeOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    从 
    <el-date-picker
      v-model="startDate"
      type="date"
      placeholder="选择开始日期"
    />
    至
    <el-date-picker
      v-model="endDate"
      type="date"
      placeholder="选择结束日期"
    /> 
    <el-button @click="onSearch" type="primary">查询</el-button>
    <el-button type="success">
      <a :href="getExcelURI()" style="text-decoration: none">导出Execl</a>
    </el-button>
  </div>
  <!-- 展示表格 -->
  <el-table :data="form" style="width: 100%" v-loading="isLoading">
    <el-table-column label="商品信息">
      <el-table-column prop="productCode" label="商品编码"/>
      <el-table-column prop="productName" label="商品名称"/>
    </el-table-column>
    <el-table-column label="金额">
      <el-table-column prop="unitPrice" label="单价"/>
      <el-table-column prop="quantityShipped" label="数量"/>
      <el-table-column prop="totalAmount" label="总价"/>
      <el-table-column prop="paidAmount" label="已付"/>
      <el-table-column prop="balanceAmount" label="待付"/>
    </el-table-column>
    <el-table-column label="出货信息">
      <el-table-column prop="shipperName" label="出货人"/>
      <el-table-column prop="shipmentDate" label="出货日期"/>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-popconfirm
          confirm-button-text="确认"
          cancel-button-text="取消"
          title="确定要删除该条台账吗?"
          @confirm="deleteRow(scope.$index, scope.row.pkId)"
        >
          <template #reference>
            <el-button
              link
              type="danger"
              size="small"
            >
              删除
            </el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <!-- 分页条 -->
  <div class="demo-pagination-block">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :background="background"
      layout="prev, pager, next, jumper"
      :total="totalCount"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts" setup>
  import { ref, watch, computed, onMounted } from 'vue'
  import { ElMessage } from 'element-plus'
  import axios from 'axios' 
  import { convertUtcToChinaDate } from '@/utils/timezone-converter'

  const selecterValue = ref('Option0')            /* 指定时间范围 */
  const startDate = ref('')                       /* 开始日期 */
  const endDate = ref('')                         /* 结束日期 */
  const isLoading = ref(true)                     /* 是否加载中 */
  const currentPage = ref(1)                      /* 当前页 */
  const pageSize = ref(3)                         /* 每页大小 */
  const background = ref(true)                    /* 分页条是否有背景框 */
  const totalCount = ref(0)                       /* 数据总条数 */
  const form = ref<Array<LedgerFormType>>([])     /* 台账表单 */

  const getExcelURI = () => {
    return `/api/ledger/get-excel?beginDate=${startDate.value}&endDate=${endDate.value}`
  }

  /* 获取当前出货时间范围的台账Execl */ // TODO
  const getExecl = async () => {
    try {
      await axios.get(`/api/ledger/get-excel?beginDate=${startDate.value}&endDate=${endDate.value}`)
    } catch (error) {
      console.log(error)
    }
  }

  /* 删除指定行数据 */
  const deleteRow = async (index: number, pkId: string) => {
    try {
      isLoading.value = true
      let res = await axios.delete(`/api/ledger/delete/${pkId}`)
      form.value.splice(index, 1)
      ElMessage({
        message: res.data,
        type: 'success',
      })
      isLoading.value = false
    } catch (error) {
      console.log(error)
      ElMessage({
        message: '删除失败',
        type: 'error',
      })
      isLoading.value = false
    }
  }


  /* Array 选择查询时间范围 */
  const timeRangeOptions = [
    {
      value: 'Option0',
      label: '查询全部',
    },
    {
      value: 'Option1',
      label: '最近一周',
    },
    {
      value: 'Option2',
      label: '最近一月',
    },
  ]

  /* 计算属性，用于根据下拉菜单的值动态设置开始日期 */
  const computedStartDate = computed(() => {  
    let date = new Date();
    if (selecterValue.value === 'Option1') {
      date.setDate(date.getDate() - 7);
    } else if (selecterValue.value === 'Option2') {  
      date.setMonth(date.getMonth() - 1);  
    }  
    return date;
  });

  /* 监听下拉菜单的值变化，并更新开始日期 */
  watch(selecterValue, (newValue) => {
    if (newValue === 'Option0') {
      startDate.value = ''
      endDate.value = ''
      return
    }
    if (newValue) {  
      startDate.value = computedStartDate.value.toDateString();  
      endDate.value = (new Date()).toDateString(); // 假设 formatDate 是你的日期格式化函数  
    }  
  });
  
  /* 点击查询后，按给定时间查询 */
  const onSearch = () => {
    getLedgerList(1, pageSize.value)
  }

  /**
   * 当前页变化时，获取新的分页台账
   * @param val 
   */
  const handleCurrentChange = (val: number) => {
    // console.log(`current page: ${val}`)
    getLedgerList(val, pageSize.value)
  }

  /**
   * interface 台账表单类型
   */
  interface LedgerFormType {
    pkId: string,                     // 台账ID
    productName: string,              // 商品名称
    productCode: string,              // 商品编码
    shipmentDate: string,             // 出货日期
    unitPrice: number | string,       // 单价
    quantityShipped: number,          // 数量
    totalAmount: number | string,     // 金额合计
    paidAmount: number | string,      // 已付金额
    balanceAmount: number | string,   // 待付金额
    shipperName: string,              // 出货人姓名
    shipperPhone: string,             // 出货人手机号
    shipperEmail: string,             // 出货人邮箱
    shipmentNotes: string,            // 出货备注
  }

  /**
   * interface 台账表单响应体
   */
  interface LedgerFormResponse {  
    currentPage: number,
    pageSize: number,
    totalCount: number,
    totalPages: number,
    dataList: Array<LedgerFormType>
  }

  /**
   * 挂载时，获取台账
   */
  onMounted(() => {
    getLedgerList(1, pageSize.value)
  })

  /**
   * 获取台账(分页)
   * @param page 当前页
   * @param size 请求页大小
   */
  async function getLedgerList(page: number, size: number) {
    try {
      isLoading.value = true
      let res: { data: LedgerFormResponse } = await axios.get(
        `/api/ledger/search?page=${page}&size=${size}&beginDate=${startDate.value}&endDate=${endDate.value}`
      )
      currentPage.value = res.data.currentPage
      totalCount.value = res.data.totalCount
      form.value = res.data.dataList.map(item => ({  
        ...item,
        unitPrice: (item.unitPrice as number).toFixed(2),
        totalAmount: (item.totalAmount as number).toFixed(2),
        paidAmount: (item.paidAmount as number).toFixed(2),
        balanceAmount: (item.balanceAmount as number).toFixed(2),
        shipmentDate: convertUtcToChinaDate(item.shipmentDate) // 转换日期  
      }))
      isLoading.value = false
    } catch (error) {
      console.error(error)
      isLoading.value = false
    }  
  }
</script>

<style scoped>
  .demo-pagination-block {
    display: flex;  
    justify-content: center;  
    margin-top: 10px;
  }

  .ledger-detail-header {
    display: flex;  
    justify-content: center;
    height: 50px;
  }
</style>