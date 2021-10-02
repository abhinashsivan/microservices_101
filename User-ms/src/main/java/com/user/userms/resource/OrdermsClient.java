package com.user.userms.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "orderms")
public interface OrdermsClient {

    @GetMapping("/orders")
    Object getAllOrders();

}
