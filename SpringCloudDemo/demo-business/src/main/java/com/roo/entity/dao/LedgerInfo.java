package com.roo.entity.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * CREATE TABLE `tb_shipment` (
 *   `pk_id` bigint NOT NULL COMMENT '索引 id',
 *   `product_name` varchar(127) DEFAULT NULL COMMENT '商品名称',
 *   `product_code` varchar(63) NOT NULL COMMENT '商品编码',
 *   `shipment_date` datetime DEFAULT NULL COMMENT '出货日期',
 *   `quantity_shipped` bigint DEFAULT NULL COMMENT '出货数量',
 *   `unit_price` bigint unsigned DEFAULT NULL COMMENT '出货价格(单位：分)',
 *   `total_amount` bigint unsigned DEFAULT NULL COMMENT '金额合计(单位：分)',
 *   `paid_amount` bigint unsigned DEFAULT NULL COMMENT '已付金额(单位：分)',
 *   `balance_amount` bigint unsigned DEFAULT NULL COMMENT '待付金额(单位：分)',
 *   `shipper_name` varchar(31) DEFAULT NULL COMMENT '出货人姓名',
 *   `shipper_phone` bigint DEFAULT NULL COMMENT '出货人手机号',
 *   `shipper_email` varchar(63) DEFAULT NULL COMMENT '出货人邮箱',
 *   `shipment_notes` varchar(255) DEFAULT NULL COMMENT '出货备注',
 *   `gmt_create` datetime NOT NULL COMMENT '创建时间',
 *   `gmt_modified` datetime NOT NULL COMMENT '最后修改时间',
 *   `del_flag` tinyint NOT NULL COMMENT '标记删除 0: 删除 1: 回收站 2: 正常'
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@TableName(value = "tb_shipment")
public class LedgerInfo {

    @TableId
    @TableField("pk_id")
    private Long pkId;

    @TableField("product_name")
    private String productName;

    @TableField("product_code")
    private String productCode;

    @TableField("shipment_date")
    private Date shipmentDate;

    @TableField("quantity_shipped")
    private Long quantityShipped;

    @TableField("unit_price")
    private Long unitPrice;

    @TableField("total_amount")
    private Long totalAmount;

    @TableField("paid_amount")
    private Long paidAmount;

    @TableField("balance_amount")
    private Long balanceAmount;

    @TableField("shipper_name")
    private String shipperName;

    @TableField("shipper_phone")
    private Long shipperPhone;

    @TableField("shipper_email")
    private String shipperEmail;

    @TableField("shipment_notes")
    private String shipmentNotes;

    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;

    @TableField("del_flag")
    private short delFlag;
}

