package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.SkuMapper;
import com.zhongrui.goods.pojo.Sku;
import com.zhongrui.goods.service.SkuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Override
    public List<Sku> findByStatus(String status) {
        Sku sku = new Sku();
        sku.setStatus(status);
        List<Sku> skus = skuMapper.select(sku);
        return skus;
    }

    @Override
    public PageInfo<Sku> findPage(Sku sku, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(sku);
        List<Sku> skus = skuMapper.selectByExample(example);
        return new PageInfo<>(skus);
    }

    @Override
    public PageInfo<Sku> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Sku> skus = skuMapper.selectAll();
        return new PageInfo<>(skus);
    }

    @Override
    public List<Sku> findList(Sku sku) {
        Example example = createExample(sku);
        return skuMapper.selectByExample(example);
    }

    @Override
    public void delete(String id) {
        skuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Sku sku) {
        skuMapper.updateByPrimaryKeySelective(sku);
    }

    @Override
    public void add(Sku sku) {
        skuMapper.insertSelective(sku);
    }

    @Override
    public Sku findById(String id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Sku> findAll() {
        return skuMapper.selectAll();
    }

    Example createExample(Sku sku) {
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        if (sku != null) {
            if (StringUtils.isEmpty(sku.getName())) {
                criteria.andLike("name", "%" + sku.getName() + "%");
            }
            if (StringUtils.isEmpty(sku.getBrandName())) {
                criteria.andLike("brandName", "%" + sku.getBrandName() + "%");
            }
            if (StringUtils.isEmpty(sku.getCategoryName())) {
                criteria.andLike("categoryName", "%" + sku.getCategoryName() + "%");
            }
        }
        return example;
    }
}
