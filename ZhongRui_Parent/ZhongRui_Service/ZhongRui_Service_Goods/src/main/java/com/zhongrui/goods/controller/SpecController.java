package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Spec;
import com.zhongrui.goods.service.SpecService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spec")
public class SpecController {

    //注入service
    @Resource
    private SpecService specService;

    @GetMapping("/category/{id}")
    public Result<List<Spec>> findByCategory(@PathVariable("id") Integer categoryId) {
        List<Spec> specs = specService.findByCategoryId(categoryId);
        if (specs == null) {
            return new Result<>(false, StatusCode.ERROR, "根据分类查询规格数据不存在");
        }
        return new Result<>(true, StatusCode.OK, "根据分类查询规格数据成功", specs);
    }

    @GetMapping
    public Result<List<Spec>> findAll() {
        List<Spec> specs = specService.findAll();
        if (specs == null) {
            return new Result<>(false, StatusCode.ERROR, "查询数据不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询数据成功", specs);
    }

    @GetMapping("/{id}")
    public Result<Spec> findById(@PathVariable("id") Integer id) {
        Spec spec = specService.findById(id);
        if (spec == null) {
            return new Result<>(false, StatusCode.ERROR, "查询数据不存在");
        }
        return new Result<>(true, StatusCode.OK, "根据id查询规格成功", spec);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Spec spec) {
        specService.add(spec);
        return new Result(true, StatusCode.OK, "添加规格成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Spec spec) {
        spec.setId(id);
        specService.update(spec);
        return new Result(true, StatusCode.OK, "修改规格数据成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        specService.delete(id);
        return new Result(true, StatusCode.OK, "删除规格数据成功");
    }

    @PostMapping("/serach")
    public Result<List<Spec>> findList(@RequestBody Spec spec) {
        List<Spec> specs = specService.findList(spec);
        if (specs == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件查询数据不存在");
        }
        return new Result<>(true, StatusCode.OK, "多条件查询规格数据成功", specs);
    }

    @GetMapping("/{page}/{size}")
    public Result<List<Spec>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询规格数据不存在", null);
        }
        return new Result<>(true, StatusCode.OK, "分页查询规格数据成功", pageInfo);
    }

    @PostMapping("/{page}/{size}")
    public Result<List<Spec>> findPage(@RequestBody Spec spec, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询规格数据不存在", null);
        }
        return new Result<>(true, StatusCode.OK, "分页查询规格数据成功", pageInfo);
    }
}
