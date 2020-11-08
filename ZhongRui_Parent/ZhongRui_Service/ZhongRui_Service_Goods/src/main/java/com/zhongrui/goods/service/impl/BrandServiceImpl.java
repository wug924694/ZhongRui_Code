package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.BrandMapper;
import com.zhongrui.goods.pojo.Brand;
import com.zhongrui.goods.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    //注入mapper
    @Resource
    private BrandMapper brandMapper;

    /**
     * 查询所有品牌信息
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();//查询所有
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);//根据id查询
    }

    /**
     * 增加品牌
     *
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        /**
         * 携带了xxxive 表示开启自动忽略空值
         *
         *   insert into t_brand(name,image)values(?,?)
         */
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改品牌
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);//会选择性的进行忽略空值
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //在真正发起查询执行
        PageHelper.startPage(page,size);
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<Brand>(brands);
    }

    //抽取条件构建
    public Example createExample(Brand brand) {
        //构建一个查询器
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //判断条件
        if (brand != null) {
            if (!StringUtils.isEmpty(brand.getName())) {//名称有值
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            if (!StringUtils.isEmpty(brand.getLetter())) {//首字母有值
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }

        return example;
    }
}
