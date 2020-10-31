package com.zhongrui.goods.dao;
import com.zhongrui.goods.pojo.Spec;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Spec的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface SpecMapper extends Mapper<Spec> {

/*    *//***
     * 根据分类ID查询规格列表
     *//*
    //@Select("select tbs.* from tb_category tbc, tb_spec tbs where tbc.template_id = tbs.template_id and tbc.id = #{categoryId}")
    @Select("select tbs.* from tb_spec tbs where template_id in (select template_id from tb_category tbc where tbc.id = #{categoryId)}")
    List<Spec> findByCategoryId(Integer categoryId);*/
}
