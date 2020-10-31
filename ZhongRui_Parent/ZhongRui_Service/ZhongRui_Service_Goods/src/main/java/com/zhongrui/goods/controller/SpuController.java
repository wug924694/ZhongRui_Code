package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Goods;
import com.zhongrui.goods.pojo.Spu;
import com.zhongrui.goods.service.SpuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {

    //注入service
    @Resource
    private SpuService spuService;

    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable("id") Long id) {
        spuService.restore(id);
        return new Result(true, StatusCode.OK, "商品还原成功");
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "逻辑删除商品成功");
    }

    @DeleteMapping("/logic/delete/{id}")
    public Result logicDelete(@PathVariable("id") Long id) {
        spuService.logicDelete(id);
        return new Result(true, StatusCode.OK, "逻辑删除商品成功");
    }


    @PutMapping("/put/many")
    public Result putMany(@RequestBody Long[] ids){
        int count = spuService.putMany(ids);
        return new Result(true,StatusCode.OK,"上架"+count+"个商品成功");
    }

    @PutMapping("/put/{id}")
    public Result put(@PathVariable Long id){
        spuService.put(id);
        return new Result(true,StatusCode.OK,"上架成功");
    }

    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable Long id){
        spuService.pull(id);
        return new Result(true,StatusCode.OK,"下架成功");
    }

    @PutMapping("/audit/{id}")
    public Result audit(@PathVariable Long id){
        spuService.audit(id);
        return new Result(true,StatusCode.OK,"审核成功");
    }

    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @GetMapping("/goods/{id}")
    public Result<List<Goods>> findGoodsById(@PathVariable("id") Long id) {
        Goods goods = spuService.findGoodsById(id);
        return new Result<>(true, StatusCode.OK, "根据id查找goods成功", goods);
    }

    @GetMapping
    public Result<List<Spu>> findAll() {
        List<Spu> spus = spuService.findAll();
        if (spus == null) {
            return new Result<>(false, StatusCode.ERROR, "查询Spu不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询Spu成功", spus);
    }

    @GetMapping("/{id}")
    public Result<Spu> findById(@PathVariable("id") String id) {
        Spu spu = spuService.findById(id);
        if (spu == null) {
            return new Result<>(false, StatusCode.ERROR, "根据id查询Spu不存在");
        }
        return new Result<>(true, StatusCode.OK, "根据id查询Spu成功", spu);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Spu spu) {
        spuService.add(spu);
        return new Result(true, StatusCode.OK, "新增Spu成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody Spu spu) {
        spu.setId(id);
        spuService.update(spu);
        return new Result(true, StatusCode.OK, "新增Spu成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "根据Id删除Spu成功");
    }

    @PostMapping("/search")
    public Result<List<Spu>> findList(@RequestBody Spu spu) {
        List<Spu> spus = spuService.findList(spu);
        if (spus == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件查询Spu不存在");
        }
        return new Result<>(true, StatusCode.OK, "多条件查询Spu成功:", spu);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<List<Spu>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Spu> pageInfo = spuService.findPage(page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询Spu不存在");
        }
        return new Result<>(true, StatusCode.OK, "分页查询Spu成功:", pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Spu>> findPage(@RequestBody Spu spu,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Spu> pageInfo = spuService.findPage(spu, page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件分页查询Spu不存在");
        }
        return new Result<>(true, StatusCode.OK, "多条件分页查询Spu成功:", pageInfo);
    }
}
