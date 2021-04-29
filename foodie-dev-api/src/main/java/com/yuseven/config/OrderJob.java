package com.yuseven.config;

import com.yuseven.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/27 22:20
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        orderService.closeOrder();
    }
}
