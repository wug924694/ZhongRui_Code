package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Para;
import com.zhongrui.goods.service.ParaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/para")
public class ParaController {

    //注入service
    @Resource
    private ParaService paraService;

    @GetMapping("/category/{id}")
    public Result<List<Para>> findByCategoryId(@PathVariable("id") Integer categoryId) {
        List<Para> paras = paraService.findByCategoryId(categoryId);
        if (paras == null) {
            return new Result<>(false, StatusCode.ERROR, "根据分类查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "根据分类查询参数列表成功", paras);
    }


    @GetMapping
    public Result<List<Para>> findAll() {
        List<Para> paras = paraService.findAll();
        if (paras == null) {
            return new Result<>(false, StatusCode.ERROR, "查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询参数列表成功", paras);
    }

    @GetMapping("/{id}")
    public Result<Para> findById(@PathVariable("id") Integer id) {
        Para para = paraService.findById(id);
        if (para == null) {
            return new Result<>(false, StatusCode.ERROR, "根据id查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "查询参数列表成功", para);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Para para) {
        paraService.add(para);
        return new Result(true, StatusCode.OK, "添加参数列表成功");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Para para) {
        para.setId(id);
        paraService.update(para);
        return new Result(true, StatusCode.OK, "修改参数列表成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        paraService.delete(id);
        return new Result(true, StatusCode.OK, "根据id删除参数列表成功");
    }

    @PostMapping("/search")
    public Result<List<Para>> findList(@RequestBody Para para) {
        List<Para> paras = paraService.findList(para);
        if (paras == null) {
            return new Result<>(false, StatusCode.ERROR, "多条件查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "多条件查询参数列表成功", paras);
    }

    @GetMapping("/{page}/{size}")
    public Result<List<Para>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "分页查询参数列表成功", pageInfo);
    }

    @PostMapping("/{page}/{size}")
    public Result<List<Para>> findPage(@RequestBody Para para, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        if (pageInfo == null) {
            return new Result<>(false, StatusCode.ERROR, "分页查询参数列表不存在");
        }
        return new Result<>(true, StatusCode.OK, "分页查询参数列表成功", pageInfo);
    }
}
