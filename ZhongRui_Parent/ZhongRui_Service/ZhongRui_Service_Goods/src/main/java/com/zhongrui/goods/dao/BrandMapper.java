package com.zhongrui.goods.dao;

import com.zhongrui.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/**
 * dao使用mapper  可以达到0sql语句
 *    增加数据:  调用Mapper.insert()
 *    增加数据:  调用Mapper.insertSelectlive()  忽略空值
 *
 *    修改数据:    调用Mapper.update()
 *    修改数据:    调用Mapper.updateByPrimaryKey()
 *
 *    查询数据:    调用Mapper.Select(T)
 *    查询数据:    调用Mapper.SelectByPrimaryKey(T)
 */
public interface BrandMapper extends Mapper<Brand> {
}
