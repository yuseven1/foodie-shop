package com.yuseven.mapper;

import com.yuseven.pojo.OrderStatus;
import com.yuseven.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/30 0:14
 */
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrders(@Param("paramMap")Map<String, Object> map);

    int getMyOrderStatusCounts(@Param("paramMap")Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramMap")Map<String, Object> map);
}
