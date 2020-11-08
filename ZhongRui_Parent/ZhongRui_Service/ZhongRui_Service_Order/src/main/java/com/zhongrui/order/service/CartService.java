package com.zhongrui.order.service;

import com.zhongrui.order.pojo.OrderItem;

import java.util.List;

public interface CartService {

    //加入购物车
    void add(Integer num,String id,String username);

    //查询购物车列表
    List<OrderItem> list(String username);
}
