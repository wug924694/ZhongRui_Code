package com.zhongrui.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.dao.AlbumMapper;
import com.zhongrui.goods.pojo.Album;
import com.zhongrui.goods.service.AlbumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    //注入Mapper
    @Resource
    private AlbumMapper albumMapper;
    /**
     * 查询所有相册信息
     * @return
     */
    @Override
    public List<Album> findAll() {
        List<Album> albums = albumMapper.selectAll();
        return albums;
    }

    /**
     * 普通分页
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Album> albums = albumMapper.selectAll();
        return new PageInfo<Album>(albums);
    }

    /**
     * 根据信息分页
     * @param album
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(Album album, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(album);
        List<Album> albums = albumMapper.selectByExample(example);
        return new PageInfo<Album>(albums);
    }



    /**
     * 新建相册
     * @param album
     */
    @Override
    public void add(Album album) {
        albumMapper.insertSelective(album);
    }
    /**
     * 查询当前相册的图片列表
     * @param album
     * @return
     */
    @Override
    public List<Album> findList(Album album) {
        Example example = createExample(album);
        return albumMapper.selectByExample(example);
    }

    /**
     *删除相册
     * @param id
     */
    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改相册信息
     * @param album
     */
    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }


    /**
     * 根据ID查询Album
     * @param id
     * @return
     */
    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    //抽取条件查询
    public Example createExample(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        //判断条件
        if(album!=null){
            // 编号
            if(!StringUtils.isEmpty(String.valueOf(album.getId()))){
                criteria.andEqualTo("id",album.getId());
            }
            // 相册名称
            if(!StringUtils.isEmpty(album.getTitle())){
                criteria.andLike("title","%"+album.getTitle()+"%");
            }
            // 相册封面
            if(!StringUtils.isEmpty(album.getImage())){
                criteria.andEqualTo("image",album.getImage());
            }
            // 图片列表
            if(!StringUtils.isEmpty(album.getImageItems())){
                criteria.andEqualTo("imageItems",album.getImageItems());
            }
        }
        return example;
    }

}
