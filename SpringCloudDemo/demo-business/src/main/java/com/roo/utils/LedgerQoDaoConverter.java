package com.roo.utils;

import com.roo.entity.dao.LedgerInfo;
import com.roo.entity.qo.LedgerQo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

public class LedgerQoDaoConverter {
    /**
     * 用于创建台账时，将Qo转化为Dao存入数据库
     * @param qo
     * @param dao
     */
    public static LedgerInfo convertQo2Dao4Insert(LedgerQo qo, LedgerInfo dao) {
        // 1) ========================================== //
        dao.setProductName(qo.getProductName());
        dao.setProductCode(qo.getProductCode());
        // 2) ========================================== //
        dao.setQuantityShipped(Long.parseLong(qo.getQuantityShipped()));
        dao.setUnitPrice(       CentConverter.convertYuan2Cents(qo.getUnitPrice()));
        dao.setTotalAmount(     CentConverter.convertYuan2Cents(qo.getTotalAmount()));
        dao.setPaidAmount(      CentConverter.convertYuan2Cents(qo.getPaidAmount()));
        dao.setBalanceAmount(   CentConverter.convertYuan2Cents(qo.getBalanceAmount()));
        // 3) ========================================== //
        dao.setShipmentDate(qo.getShipmentDate());
        dao.setShipperName(qo.getShipperName());
        dao.setShipperPhone(Long.parseLong(qo.getShipperPhone()));
        dao.setShipperEmail(qo.getShipperEmail());
        dao.setShipmentNotes(qo.getShipmentNotes());
        // 4) ========================================== //
        dao.setGmtCreate(new Date());
        dao.setGmtModified(new Date());
        dao.setDelFlag((short) 2);
        // ============================================= //
        return dao;
    }

    /**
     * TODO: 用于查询后，将dao结果转化为qo返回给前端
     * @param dao
     * @param qo
     * @return
     */
    public static LedgerQo convertDao2Qo4SearchResult(LedgerInfo dao, LedgerQo qo) {
        // 0) ========================================== //
        qo.setPkId(dao.getPkId().toString());
        // 1) ========================================== //
        qo.setProductName(dao.getProductName());
        qo.setProductCode(dao.getProductCode());
        // 2) ========================================== //
        qo.setQuantityShipped(dao.getQuantityShipped().toString());
        qo.setUnitPrice(       CentConverter.convertCents2Yuan(dao.getUnitPrice()));
        qo.setTotalAmount(     CentConverter.convertCents2Yuan(dao.getTotalAmount()));
        qo.setPaidAmount(      CentConverter.convertCents2Yuan(dao.getPaidAmount()));
        qo.setBalanceAmount(   CentConverter.convertCents2Yuan(dao.getBalanceAmount()));
        // 3) ========================================== //
        qo.setShipmentDate(dao.getShipmentDate());
        qo.setShipperName(dao.getShipperName());
        qo.setShipperPhone(dao.getShipperPhone().toString());
        qo.setShipperEmail(dao.getShipperEmail());
        qo.setShipmentNotes(dao.getShipmentNotes());
        // ============================================= //
        return qo;
    }

    /**
     * TODO：用于更新
     * @param qo
     * @param dao
     * @return
     */
    public static boolean convertQo2Dao4Update(LedgerQo qo, LedgerInfo dao) {
//        dao.setGmtCreate(null);
        dao.setGmtModified(new Date());
        return true;
    }

}
