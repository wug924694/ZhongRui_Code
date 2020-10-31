package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.CategoryMapper;
import com.zhongrui.goods.pojo.Brand;
import com.zhongrui.goods.pojo.Category;
import com.zhongrui.goods.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    //注入mapper
    @Resource
    private CategoryMapper categoryMapper;


    //多条件分页查询
    @Override
    public PageInfo<Category> findPage(Category category, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = creatExample(category);
        List<Category> categories = categoryMapper.selectByExample(example);
        return new PageInfo<>(categories);
    }

    //分页查询
    @Override
    public PageInfo<Category> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Category> categories = categoryMapper.selectAll();
        return new PageInfo<>(categories);
    }

    //条件查找分类
    @Override
    public List<Category> findList(Category category) {
        Example example = creatExample(category);
        return categoryMapper.selectByExample(example);
    }

    //修改分类
    @Override
    public void delete(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    //添加分类
    @Override
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    //根据Id查询
    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    //查询所有分类
    @Override
    public List<Category> findAll() {
        return categoryMapper.selectAll();
    }

    //抽取条件查询
    public Example creatExample(Category category) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        //判断条件
        if (category != null) {
            if (!StringUtils.isEmpty(category.getName())) {
                criteria.andLike("name", "%" + category.getName() + "%");
            }
        }
        return example;
    }
}
