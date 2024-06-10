package com.roo.service;

import com.roo.entity.qo.LedgerQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

public interface LedgerService {

    /**
     * 查询表
     * @param page
     * @return
     */
    ResponseEntity<Object> queryLedgerList(int page, int size, Date beginDate, Date endDate);

    /**
     * 创建表
     * @param ledgerQo
     * @return
     */
    ResponseEntity<Object> createLedgerList(LedgerQo ledgerQo);

    /**
     * 更新表
     * TODO
     * @param pathMatch
     * @return
     */
    ResponseEntity<Object> updateLedgerList(List<String> pathMatch);

    /**
     * 删除表：仅标记删除，过期后真删除
     * @param id
     * @return
     */
    ResponseEntity<Object> dropLedgerList(Long id);


}
