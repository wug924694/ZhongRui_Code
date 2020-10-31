package com.zhongrui.goods.service;

import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.pojo.Brand;

import java.util.List;

public interface BrandService {

    //查询所有品牌信息
    List<Brand> findAll();

    //根据id查询
    Brand findById(Integer id);

    //增加品牌
    void add(Brand brand);

    //修改品牌
    void update(Brand brand);

    //删除品牌
    void delete(Integer id);

    //根据条件查询
    List<Brand> findList(Brand brand);

    //分页查询
    PageInfo<Brand> findPage(Integer page, Integer size);

    //条件分页查询
    PageInfo<Brand> findPage(Brand brand,Integer page, Integer size);

    //根据分类查询对应品牌集合
    List<Brand> findByCategory(Integer categoryId);
}
