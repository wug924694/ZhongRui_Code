package com.zhongrui.order.service.impl;

import com.zhongrui.entity.Result;
import com.zhongrui.goods.feign.SkuFeign;
import com.zhongrui.goods.feign.SpuFeign;
import com.zhongrui.goods.pojo.Sku;
import com.zhongrui.goods.pojo.Spu;
import com.zhongrui.order.pojo.OrderItem;
import com.zhongrui.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;
    /**
     * 加入购物车
     * @param num  购物车商品数量
     * @param id  商品的id
     */
    @Override
    public void add(Integer num, String id,String username) {
        //当添加的商品购买的数量小于等于了0  从购物车对象移除
        if(num <= 0){
            //从购物车移除
            redisTemplate.boundHashOps("Cart_"+username).delete(id);

            //如果此处购物车当中只剩下最后一个商品 那么这个数量没了之后 准备购物车对象也应该删除
            Long size = redisTemplate.boundHashOps("Cart_" + username).size();
            if(size == null || size <= 0){
                //将该用户的购物车删除
                redisTemplate.delete("Cart_" + username);
            }
            return;
        }
        //查询商品的详情
        //1.查询sku
        Result<Sku> skuResult = skuFeign.findById(id);
        Sku sku = skuResult.getData();
        //2.查询spu
        Result<Spu> spuResult = spuFeign.findById(sku.getSpuId());
        Spu spu = spuResult.getData();
        //将加入购物车的商品封装成orderitem对象
        OrderItem orderItem = setOrderItem(num, id, sku, spu);

        //将购物车数据存入redis --》 username
        redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
    }

    @Override
    public List<OrderItem> list(String username) {
        return redisTemplate.boundHashOps("Cart_"+username).values();
    }

    public OrderItem setOrderItem(Integer num, String id, Sku sku, Spu spu) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(id);
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num * orderItem.getPrice());//总价  数量 * 单价
        orderItem.setImage(spu.getImage());
        return orderItem;
    }
}
