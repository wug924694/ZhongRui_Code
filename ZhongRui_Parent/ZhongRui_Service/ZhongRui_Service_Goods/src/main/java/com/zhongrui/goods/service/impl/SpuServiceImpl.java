package com.zhongrui.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.IdWorker;
import com.zhongrui.goods.dao.BrandMapper;
import com.zhongrui.goods.dao.CategoryMapper;
import com.zhongrui.goods.dao.SkuMapper;
import com.zhongrui.goods.dao.SpuMapper;
import com.zhongrui.goods.pojo.Brand;
import com.zhongrui.goods.pojo.Goods;
import com.zhongrui.goods.pojo.Sku;
import com.zhongrui.goods.pojo.Spu;
import com.zhongrui.goods.service.SpuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    //注入mapper
    @Resource
    private SpuMapper spuMapper;

    @Resource
    private IdWorker idWorker;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SkuMapper skuMapper;

    @Override
    public void delete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否逻辑删除
        if (!spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("此商品不能删除");
        }
        spuMapper.deleteByPrimaryKey(spuId);
    }

    @Override
    public void restore(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否是删除的商品
        if (!spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("此商品未删除");
        }
        //还原
        spu.setIsDelete("0");
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void logicDelete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查商品是否下架
        if (!spu.getIsMarketable().equalsIgnoreCase("0")) {
            throw new RuntimeException("必须先下架商品才能删除商品");
        }
        //逻辑删除
        spu.setIsDelete("1");
        //未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public int putMany(Long[] ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("1");//上架
        //批量
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        //下架
        criteria.andEqualTo("isMarketable", "0");
        //审核通过的
        criteria.andEqualTo("status", "1");
        //非删除的
        criteria.andEqualTo("isDelete", "0");
        return spuMapper.updateByExampleSelective(spu, criteria);
    }

    @Override
    public void put(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品已经被删除！");
        }
        if (!spu.getStatus().equalsIgnoreCase("1")) {
            throw new RuntimeException("未经过审核的商品不能上架！");
        }
        spu.setIsMarketable("1");//上架状态
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void pull(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品已经被删除！");
        }
        spu.setIsMarketable("0");//下架状态
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    //商品审核
    @Override
    public void audit(Long spuId) {
        //查询商品
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //判断商品是否已经删除
        if (spu.getIsDelete().equalsIgnoreCase("1")) {
            throw new RuntimeException("该商品已经被删除！");
        }
        //实现商品上架和审核
        spu.setStatus("1");//审核通过
        spu.setIsMarketable("1");//商品上架
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    //保存修改商品
    @Override
    public void saveGoods(Goods goods) {
        //增加Spu
        Spu spu = goods.getSpu();
        //如果有id，则为修改方法
        if (spu.getId() == null) {
            //增加
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else {
            //修改数据
            spuMapper.updateByPrimaryKeySelective(spu);
            //删除该Spu的sku
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        //增加Sku
        Date date = new Date();
        categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        //获取Sku集合
        List<Sku> skus = goods.getSkuList();
        //循环skus添加到数据库
        for (Sku sku : skus) {
            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");
            }
            String name = spu.getName();
            Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                name += " " + entry.getValue();
            }
            sku.setName(name);
            sku.setId(idWorker.nextId());
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setCategoryId(spu.getCategory3Id());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
    }

    @Override
    public Goods findGoodsById(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        //封装goods
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skus);
        return goods;
    }

    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(spu);
        List<Spu> spus = spuMapper.selectByExample(example);
        return new PageInfo<>(spus);
    }

    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Spu> spus = spuMapper.selectAll();
        return new PageInfo<>(spus);
    }

    @Override
    public List<Spu> findList(Spu spu) {
        Example example = createExample(spu);
        return spuMapper.selectByExample(example);
    }

    @Override
    public void delete(String id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void add(Spu spu) {
        spuMapper.insertSelective(spu);
    }

    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    //抽取条件查询
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (spu != null) {
            if (StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
        }
        return example;
    }
}
