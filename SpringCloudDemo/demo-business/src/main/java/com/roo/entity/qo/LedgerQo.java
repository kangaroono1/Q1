package com.roo.entity.qo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class LedgerQo {

    private String pkId;

    private String productName;

    private String productCode;

    private Date shipmentDate;

    private String quantityShipped;

    private BigDecimal unitPrice;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private BigDecimal balanceAmount;

    private String shipperName;

    private String shipperPhone;

    private String shipperEmail;

    private String shipmentNotes;
}
