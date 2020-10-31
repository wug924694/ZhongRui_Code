package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.CategoryMapper;
import com.zhongrui.goods.dao.SpecMapper;
import com.zhongrui.goods.pojo.Category;
import com.zhongrui.goods.pojo.Spec;
import com.zhongrui.goods.service.SpecService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    //注入mapper
    @Resource
    private SpecMapper specMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Spec> findByCategoryId(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        Integer templateId = category.getTemplateId();
        Spec spec = new Spec();
        spec.setTemplateId(templateId);
        return specMapper.select(spec);
    }

    @Override
    public PageInfo<Spec> findPage(Spec spec, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(spec);
        List<Spec> specs = specMapper.selectByExample(example);
        return new PageInfo<>(specs);
    }

    @Override
    public PageInfo<Spec> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Spec> specs = specMapper.selectAll();
        return new PageInfo<>(specs);
    }

    @Override
    public List<Spec> findList(Spec spec) {
        Example example = createExample(spec);
        return specMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKeySelective(spec);
    }

    @Override
    public void add(Spec spec) {
        specMapper.insertSelective(spec);
    }

    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    //抽取条件查询
    public Example createExample(Spec spec){
        Example example=new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();
        if(spec!=null){
            // ID
            if(!StringUtils.isEmpty(String.valueOf(spec.getId()))){
                criteria.andEqualTo("id",spec.getId());
            }
            // 名称
            if(!StringUtils.isEmpty(spec.getName())){
                criteria.andLike("name","%"+spec.getName()+"%");
            }
            // 规格选项
            if(!StringUtils.isEmpty(spec.getOptions())){
                criteria.andEqualTo("options",spec.getOptions());
            }
            // 模板ID
            if(!StringUtils.isEmpty(String.valueOf(spec.getTemplateId()))){
                criteria.andEqualTo("templateId",spec.getTemplateId());
            }
        }
        return example;
    }
}
