<template>
    <div class="add-ledger">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="auto" style="max-width: 800px">

        <el-row>
          <el-col :span="7">
            <el-form-item prop="productCode" label="商品编码">
              <el-input clearable v-model="form.productCode" />
            </el-form-item>
          </el-col><el-col :span="1"></el-col>
          <el-col :span="14">
            <el-form-item prop="productName" label="商品名称">
              <el-input clearable v-model="form.productName" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider />

        <el-row>
          <el-col :span="7">
            <el-form-item prop="unitPrice" label="出货单价">
              <el-input-number clearable v-model="form.unitPrice" :precision="2" :controls="false" :min="0.00"/>
            </el-form-item>
          </el-col><el-col :span="1"></el-col>
          <el-col :span="7">
            <el-form-item prop="quantityShipped" label="出货数量">
                <el-input-number clearable v-model="form.quantityShipped" :precision="0" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item prop="totalAmount" label="合计">
                <el-input-number v-model="form.totalAmount" disabled :precision="2" :controls="false"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="7">
            <el-form-item prop="paidAmount" label="已付金额">
              <el-input-number v-model="form.paidAmount" :precision="2" :controls="false" :min="0.00"/>
            </el-form-item>
          </el-col><el-col :span="1"></el-col>
          <el-col :span="7">
            <el-form-item prop="balanceAmount" label="待付金额">
              <el-input-number clearable v-model="form.balanceAmount" disabled :precision="2" :controls="false"/>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item prop="shipmentDate" label="出货日期">
              <el-col :span="24">
                <el-date-picker
                v-model="form.shipmentDate"
                type="date"
                placeholder="选择日期"
                :shortcuts="shortcuts"
                style="width: 100%"
                />
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider />

        <el-row>
          <el-col :span="7">
            <el-form-item prop="shipperName" label="出货人姓名">
              <el-input clearable v-model="form.shipperName" />
            </el-form-item>
          </el-col><el-col :span="1"></el-col>
          <el-col :span="14">
            <el-form-item prop="shipperPhone" label="出货人手机号">
              <el-input clearable v-model="form.shipperPhone" />
            </el-form-item>
          </el-col>
        </el-row>

    
        <el-form-item prop="shipperEmail" label="出货人邮箱">
          <el-input clearable v-model="form.shipperEmail" />
        </el-form-item>

        <el-divider />

        <el-form-item prop="shipmentNotes" label="备注">
          <el-input
            maxlength="50"
            show-word-limit
            v-model="form.shipmentNotes"
            type="textarea" 
          />
        </el-form-item>

        
        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">提交</el-button>
          <el-button @click="resetForm(formRef)">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
</template>
  
<script lang="ts" setup>
  import { ref, watch, computed } from 'vue'
  import { ElMessage } from 'element-plus'
  import axios from 'axios'
  import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
  import { isValidChinesePhoneNumber } from '@/utils/valid-cn-phone-detect'
  import { isValidEmail } from '@/utils/valid-email-detect'

  const formRef = ref<FormInstance>()

  interface LedgerFormType {
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

  const form = ref<LedgerFormType>({
    productName: '',       // 商品名称
    productCode: '',       // 商品编码
    shipmentDate: '',      // 出货日期
    unitPrice: 0.00,       // 单价
    quantityShipped: 0,    // 数量
    totalAmount: 0.00,     // 金额合计
    paidAmount: 0.00,      // 已付金额
    balanceAmount: 0.00,   // 待付金额
    shipperName: '',       // 出货人姓名
    shipperPhone: '',      // 出货人手机号
    shipperEmail: '',      // 出货人邮箱
    shipmentNotes: '',     // 出货备注
  })
  
  const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    if ((form.value.balanceAmount as number) < 0) {
      ElMessage({
        message: '待付金额不能为负数！',
        type: 'error',
      })
      return
    }
    await formEl.validate((valid, fields) => {
      if (valid) {
        createLedgerList()
      } else {
        console.log('error submit!', fields)
      }
    })
  }

  const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
  }

  async function createLedgerList() {
    try {
      let val = form.value
      let res = await axios.post(`/api/ledger/add`, val)
      ElMessage({
        message: res.data,
        type: 'success',
      })
    } catch (error) {
      ElMessage({
        message: '提交错误',
        type: 'error',
      })
      console.error(error)  
    }  
  }

  watch([
        () => form.value.unitPrice,
        () => form.value.quantityShipped,
        () => form.value.paidAmount
    ], () => {
        form.value.totalAmount = (form.value.unitPrice as number) * form.value.quantityShipped
        form.value.balanceAmount = form.value.totalAmount - (form.value.paidAmount as number)
    }
  )

  const shortcuts = [{
        text: '今日',
        value: new Date(),
    },{
        text: '昨日',
        value: () => {
        const date = new Date()
        date.setTime(date.getTime() - 3600 * 1000 * 24)
        return date
        },
    },{
        text: '一周前',
        value: () => {
        const date = new Date()
        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
        return date
        },
    },
  ]

  const rules = ref<FormRules<LedgerFormType>>({
    productCode: [
      { required: true, message: '请输入商品编码', trigger: 'blur' }
    ],
    productName: [
      { required: true, message: '请输入商品名' }
    ],
    shipmentDate: [
      { required: true, message: '请输入出货日期' }
    ],
    unitPrice: [
      { required: true },
    ],
    quantityShipped: [
      { required: true }
    ],
    shipperName: [
      { required: true, message: '请输入出货人姓名' },
    ],
    shipperPhone: [
      { required: true, validator: isValidChinesePhoneNumber, trigger: 'blur' }
    ],
    shipperEmail: [
      { validator: isValidEmail, trigger: 'blur' }
    ]
  })
</script>
  
<style scoped>
.img {
    width: 100%;
}
.add-ledger {
    margin-top: 30px;
    margin-left: 30px;
}
</style>