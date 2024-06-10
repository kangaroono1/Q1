package com.roo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageDataVo<T> {
    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPages;
    private List<T> dataList;
}
