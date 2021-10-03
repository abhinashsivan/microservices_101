package com.user.userms.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "orderms8")
public interface OrdermsClient {

    @GetMapping("/orders")
    Object getAllOrders();

}
