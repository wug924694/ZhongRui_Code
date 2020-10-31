package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Sku;
import com.zhongrui.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping("/status/{status}")
    public Result<List<Sku>> findByStatus(@PathVariable("status") String status) {
        List<Sku> skus = skuService.findByStatus(status);
        return new Result<>(true, StatusCode.OK, "根据状态查询Sku成功", skus);
    }


    @GetMapping
    public Result<List<Sku>> findAll() {
        List<Sku> skus = skuService.findAll();
        if (skus == null) {
            return new Result<>(false, StatusCode.ERROR, "查找Sku不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询Sku成功", skus);
    }

    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable("id") String id) {
        Sku sku = skuService.findById(id);
        if (sku == null) {
            return new Result<>(false, StatusCode.ERROR, "查询失败，不存在");
        }
        return new Result<>(true, StatusCode.OK, "根据id查询sku成功", sku);
    }

    @PostMapping
    public Result add(@RequestBody Sku sku) {
        skuService.add(sku);
        return new Result(true, StatusCode.OK, "添加Sku成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody Sku sku) {
        sku.setId(id);
        skuService.update(sku);
        return new Result(true, StatusCode.OK, "修改ku成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        skuService.delete(id);
        return new Result(true, StatusCode.OK, "删除Sku成功");
    }

    @PostMapping("/search")
    public Result<List<Sku>> findList(@RequestBody Sku sku) {
        List<Sku> skus = skuService.findList(sku);
        if (skus == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件查询失败");
        }
        return new Result<>(true, StatusCode.OK, "多条件查询sku成功", skus);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<List<Sku>> findList(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {

        PageInfo<Sku> pageInfo = skuService.findPage(page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询失败");
        }
        return new Result<>(true, StatusCode.OK, "分页查询sku成功", pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Sku>> findList(@RequestBody Sku sku, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Sku> pageInfo = skuService.findPage(sku, page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件分页查询失败");
        }
        return new Result<>(true, StatusCode.OK, "多条件分页查询sku成功", pageInfo);
    }
}
