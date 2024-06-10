package com.roo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roo.entity.dao.LedgerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LedgerMapper extends BaseMapper<LedgerInfo> {
}
