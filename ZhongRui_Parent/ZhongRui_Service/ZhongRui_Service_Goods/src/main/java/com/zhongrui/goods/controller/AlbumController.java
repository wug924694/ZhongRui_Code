package com.zhongrui.goods.controller;


import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Album;
import com.zhongrui.goods.service.AlbumService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/album")
@CrossOrigin    //同意跨域访问
public class AlbumController {

    @Resource
    private AlbumService albumService;

    @GetMapping
    public Result<List<Album>> findAll() {
        List<Album> albums = albumService.findAll();
        return new Result<List<Album>>(true, StatusCode.OK, "查询相册成功", albums);
    }

    @GetMapping("/search")
    public Result<List<Album>> findList(@RequestBody Album album) {
        List<Album> list = albumService.findList(album);
        if (list == null && list.size() < 0) {
            return new Result(false, StatusCode.ERROR, "该相册不存在");
        }
        return new Result<List<Album>>(true, StatusCode.OK, "条件查询相册成功!!!", list);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<List<Album>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        if (pageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<List<Album>>(true, StatusCode.OK, "查询相册普通分页成功", pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Album>> findPage(@RequestBody(required = false) Album album, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        if (pageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<List<Album>>(true, StatusCode.OK, "查询相册多条件分页成功", pageInfo);
    }

    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable("id") Long id) {
        Album album = albumService.findById(id);
        return new Result<Album>(true, StatusCode.OK, "根据id查询相册成功", album);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "添加相册成功");
    }

    @DeleteMapping("/delete")
    public Result delete(@PathVariable("id") Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除相册成功");
    }

    @PutMapping("/update/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody Album album) {
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK, "修改相册成功");
    }
}
