package com.yuseven.service.center.impl;

import com.github.pagehelper.PageInfo;
import com.yuseven.utils.PagedGridResult;

import java.util.List;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/4/1 0:08
 */
public class BaseService {

    public PagedGridResult setterPageGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
