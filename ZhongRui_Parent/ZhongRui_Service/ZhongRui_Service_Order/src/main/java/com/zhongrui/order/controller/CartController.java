package com.zhongrui.order.controller;

import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.order.pojo.OrderItem;
import com.zhongrui.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //加入购物车
    @GetMapping("/add")
    public Result add(Integer num,String id){
        //令牌 --> 解析 --> username
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //加入购物车
        cartService.add(num,id,"tangseng");
        return new Result(true, StatusCode.OK,"加入购物车成功!!!");
    }

    //查询购物车
    @GetMapping("/list")
    public Result list(){
        String username = "tangseng";
        List<OrderItem> itemList = cartService.list(username);
        return new Result(true,StatusCode.OK,"查询购物车列表成功!",itemList);
    }
}
