package com.zhongrui.goods.controller;


import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Category;
import com.zhongrui.goods.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin    //同意跨域访问
public class CategoryController {

    //注入Service
    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        return new Result<>(true, StatusCode.OK, "查询所有分类成功", categories);
    }

    @GetMapping("/{id}")
    public Result<Category> findById(@PathVariable("id") Integer id) {
        Category category = categoryService.findById(id);
        return new Result<>(true, StatusCode.OK, "根据id查询分类成功", category);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return new Result(true, StatusCode.OK, "添加分类成功");
    }

    @PutMapping("/update")
    public Result update(@PathVariable("id") Integer id, @RequestBody Category category) {
        category.setId(id);
        categoryService.update(category);
        return new Result(true, StatusCode.OK, "修改分类成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return new Result(true, StatusCode.OK, "删除分类成功");
    }

    @GetMapping("/search")
    public Result<List<Category>> findList(@RequestBody Category category) {
        List<Category> categories = categoryService.findList(category);
        return new Result<>(true, StatusCode.OK, "多条件查询分类数据成功", categories);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<List<Category>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Category> categoryPageInfo = categoryService.findPage(page, size);
        if (categoryPageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<>(true, StatusCode.OK, "分页查询分类数据成功", categoryPageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Category>> findPage(@RequestBody(required = false) Category category, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Category> categoryPageInfo = categoryService.findPage(category, page, size);
        if (categoryPageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<>(true, StatusCode.OK, "分页查询分类数据成功", categoryPageInfo);
    }

}
