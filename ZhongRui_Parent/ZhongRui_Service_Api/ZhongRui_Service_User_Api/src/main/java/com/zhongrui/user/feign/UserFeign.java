package com.zhongrui.user.feign;

import com.zhongrui.entity.Result;
import com.zhongrui.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "user")
@RequestMapping("/user")
public interface UserFeign {

    @GetMapping("/load/{id}")
    Result<User> findById(@PathVariable String id);
}
