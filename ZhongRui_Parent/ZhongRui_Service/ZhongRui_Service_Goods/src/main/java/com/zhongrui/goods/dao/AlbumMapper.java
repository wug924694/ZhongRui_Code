package com.zhongrui.goods.dao;

import com.zhongrui.goods.pojo.Album;
import tk.mybatis.mapper.common.Mapper;

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
public interface AlbumMapper extends Mapper<Album> {

}
