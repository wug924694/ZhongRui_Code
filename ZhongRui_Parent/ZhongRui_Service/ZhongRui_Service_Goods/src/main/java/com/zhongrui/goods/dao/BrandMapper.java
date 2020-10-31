package com.zhongrui.goods.dao;

import com.zhongrui.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * dao 使用 Mapper 可以达到零SQl语句
 *      增加数据：调用Mapper.insert
 *      增加数据：调用Mapper.insertSelective() 忽略空值
 *
 *      修改数据：调用Mapper.update()
 *      修改数据：调用Mapper.updateByPrimaryKey(T)
 *
 *      查询数据：调用Mapper.select()
 *      查询数据：调用Mapper.selectByPrimaryKey(T)
 */
public interface BrandMapper extends Mapper<Brand> {
    /***
     * 查询分类对应的品牌集合
     */
    @Select("select tb.* from tb_category_brand tcb, tb_brand tb where tcb.category_id = #{categoryId} and tb.id = tcb.brand_id")
    List<Brand> findByCategory(Integer categoryId);

}
