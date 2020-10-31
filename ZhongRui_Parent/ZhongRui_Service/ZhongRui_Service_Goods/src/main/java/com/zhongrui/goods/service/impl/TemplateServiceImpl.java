package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.TemplateMapper;
import com.zhongrui.goods.pojo.Template;
import com.zhongrui.goods.service.TemplateService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    //注入mapper
    @Resource
    private TemplateMapper templateMapper;

    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        PageHelper.startPage(page, size);
        creatExample(template);
        List<Template> templates = templateMapper.selectByExample(template);
        return new PageInfo<>(templates);
    }

    @Override
    public PageInfo<Template> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Template> templates = templateMapper.selectAll();
        return new PageInfo<>(templates);
    }

    @Override
    public List<Template> findList(Template template) {
        Example example = creatExample(template);
        return templateMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public void add(Template template) {
        templateMapper.insertSelective(template);
    }

    //根据id查询模板
    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    //查询所有模板
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    //抽取条件查询
    public Example creatExample(Template template) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        //判断条件
        if (template != null) {
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
        }
        return example;
    }
}
