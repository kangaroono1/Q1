package com.roo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roo.entity.dao.LedgerInfo;
import com.roo.entity.qo.LedgerQo;
import com.roo.entity.vo.PageDataVo;
import com.roo.mapper.LedgerMapper;
import com.roo.service.LedgerService;
import com.roo.utils.LedgerQoDaoConverter;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerMapper ledgerMapper;

    /**
     * 返回台账分页结果
     * TODO： 时间范围查询、仅查询 Flag 为 2 的数据（正常数据）
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public ResponseEntity<Object> queryLedgerList(int currentPage, int pageSize, Date beginDate, Date endDate) {
        Page<LedgerInfo> pageInfo = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<LedgerInfo> queryWrapper = new LambdaQueryWrapper<>();

        // 规则1：仅查询del_flag为2的数据
        queryWrapper.eq(LedgerInfo::getDelFlag, 2);

        // 规则2：按给定时间范围查询
        if (beginDate != null) {
            queryWrapper.ge(LedgerInfo::getShipmentDate, beginDate);
        }
        if (endDate != null) {
            endDate = DateUtils.addDays(endDate, 1);
            queryWrapper.le(LedgerInfo::getShipmentDate, endDate);
        }

        // 规则3：按最近日期在前，若日期相同，按最大ID靠前返回
        // orderByDesc已经确保了ID降序，但日期部分需要确保createTime也是降序
        queryWrapper.orderByDesc(LedgerInfo::getShipmentDate);
        queryWrapper.orderByDesc(LedgerInfo::getPkId);

        // 执行分页查询
        IPage<LedgerInfo> ledgerPage = ledgerMapper.selectPage(pageInfo, queryWrapper);

        // 将查询结果转换为PageDataVo对象
        PageDataVo<LedgerQo> pageDataVo = new PageDataVo<>();
        pageDataVo.setCurrentPage(  Math.toIntExact(ledgerPage.getCurrent())    );
        pageDataVo.setPageSize(     Math.toIntExact(ledgerPage.getSize())       );
        pageDataVo.setTotalCount(   Math.toIntExact(ledgerPage.getTotal())      );
        pageDataVo.setTotalPages(   Math.toIntExact(ledgerPage.getPages())      );

        List<LedgerQo> qoList = ledgerPage.getRecords().stream()
                .map(dao -> LedgerQoDaoConverter.convertDao2Qo4SearchResult(dao, new LedgerQo()))
                .collect(Collectors.toList());
        pageDataVo.setDataList(qoList);

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(pageDataVo);
    }

    /**
     * 创建台账
     * @param ledgerQo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> createLedgerList(LedgerQo ledgerQo) {
//        System.out.println(ledgerQo.toString());
        LedgerInfo ledgerDao = LedgerQoDaoConverter
                .convertQo2Dao4Insert(ledgerQo, new LedgerInfo());

        int row = ledgerMapper.insert(ledgerDao);
        if (row > 0) {
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("创建台账成功");
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("Create LedgerList Failed");
        }
    }

    /**
     * 更新台账
     * TODO
     * @param pathMatch
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> updateLedgerList(List<String> pathMatch) {
        return null;
    }

    /**
     * 逻辑删除台账
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> dropLedgerList(Long id) {
        LambdaUpdateWrapper<LedgerInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LedgerInfo::getPkId, id);
        updateWrapper.set(LedgerInfo::getDelFlag, (short) 1);

        int row = ledgerMapper.update(null, updateWrapper);

        if (row > 0) {
            return ResponseEntity.status(HttpServletResponse.SC_OK).body("台账已放入回收站");
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("Drop LedgerList Failed");
        }
    }
}
