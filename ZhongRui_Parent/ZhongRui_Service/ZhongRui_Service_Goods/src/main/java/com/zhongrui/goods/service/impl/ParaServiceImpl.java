package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.CategoryMapper;
import com.zhongrui.goods.dao.ParaMapper;
import com.zhongrui.goods.pojo.Category;
import com.zhongrui.goods.pojo.Para;
import com.zhongrui.goods.service.ParaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {

    //注入mapper
    @Resource
    private ParaMapper paraMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Para> findByCategoryId(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        Integer templateId = category.getTemplateId();
        Para para = new Para();
        para.setTemplateId(templateId);
        return paraMapper.select(para);
    }

    @Override
    public PageInfo<Para> findPage(Para para, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(para);
        List<Para> paras = paraMapper.selectByExample(example);
        return new PageInfo<>(paras);
    }

    @Override
    public PageInfo<Para> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Para> paras = paraMapper.selectAll();
        return new PageInfo<>(paras);
    }

    @Override
    public List<Para> findList(Para para) {
        Example example = createExample(para);
        return paraMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        paraMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
    }

    @Override
    public void add(Para para) {
        paraMapper.insertSelective(para);
    }

    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    //抽取条件查询
    public Example createExample(Para para) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        //判断条件
        if (para != null) {
            if (!StringUtils.isEmpty(para.getName())) {
                criteria.andLike("name", "%" + para.getName() + "%");
            }
        }
        return example;
    }
}
