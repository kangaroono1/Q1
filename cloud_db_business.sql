/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : cloud_db_business

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2024-06-10 18:52:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_shipment
-- ----------------------------
DROP TABLE IF EXISTS `tb_shipment`;
CREATE TABLE `tb_shipment` (
  `pk_id` bigint NOT NULL COMMENT '索引 id',
  `product_name` varchar(127) DEFAULT NULL COMMENT '商品名称',
  `product_code` varchar(63) NOT NULL COMMENT '商品编码',
  `shipment_date` datetime DEFAULT NULL COMMENT '出货日期',
  `quantity_shipped` bigint DEFAULT NULL COMMENT '出货数量',
  `unit_price` bigint unsigned DEFAULT NULL COMMENT '出货价格(单位：分)',
  `total_amount` bigint unsigned DEFAULT NULL COMMENT '金额合计(单位：分)',
  `paid_amount` bigint unsigned DEFAULT NULL COMMENT '已付金额(单位：分)',
  `balance_amount` bigint unsigned DEFAULT NULL COMMENT '待付金额(单位：分)',
  `shipper_name` varchar(31) DEFAULT NULL COMMENT '出货人姓名',
  `shipper_phone` bigint DEFAULT NULL COMMENT '出货人手机号',
  `shipper_email` varchar(63) DEFAULT NULL COMMENT '出货人邮箱',
  `shipment_notes` varchar(255) DEFAULT NULL COMMENT '出货备注',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后修改时间',
  `del_flag` tinyint NOT NULL COMMENT '标记删除 0: 删除 1: 回收站 2: 正常'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_shipment
-- ----------------------------
INSERT INTO `tb_shipment` VALUES ('1799298021960306690', 'X1', 'Name', '2024-06-07 00:00:00', '5', '18491', '92455', '50012', '42443', '张氏', '15102205071', '1006700796@qq.com', '锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤', '2024-06-08 12:30:52', '2024-06-08 12:30:52', '1');
INSERT INTO `tb_shipment` VALUES ('1799347087259856898', 'x3', '6666', '2024-06-01 00:00:00', '2', '193000', '386000', '50000', '336000', 'z', '6', 'kangaroono1@163.com', 'xzzxzxzxz', '2024-06-08 15:45:50', '2024-06-08 15:45:50', '2');
INSERT INTO `tb_shipment` VALUES ('1799468131840811009', 'Name', 'XX', '2024-06-29 00:00:00', '3', '200', '600', '600', '0', 'ZYD', '15102205071', '', '', '2024-06-08 23:46:50', '2024-06-08 23:46:50', '2');
INSERT INTO `tb_shipment` VALUES ('1799699798798897153', '锟斤拷', 'ABC', '2024-06-20 00:00:00', '2', '200', '400', '0', '400', 'ZD', '15102229999', '', '', '2024-06-09 15:07:23', '2024-06-09 15:07:23', '2');
INSERT INTO `tb_shipment` VALUES ('1800086125868605441', 'XN00', 'XN', '2024-06-12 00:00:00', '2', '99999', '199998', '122000', '77998', 'ZD', '15102200000', '', '', '2024-06-10 16:42:31', '2024-06-10 16:42:31', '2');
INSERT INTO `tb_shipment` VALUES ('1800086277853405186', 'liuxing', 'MA', '2024-06-10 00:00:00', '1', '50000', '50000', '49000', '1000', 'DD', '13900002222', '', '', '2024-06-10 16:43:07', '2024-06-10 16:43:07', '2');
