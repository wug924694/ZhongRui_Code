package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.goods.service.BrandService;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin    //同意跨域访问
public class BrandController {

    //注入service
    @Resource
    private BrandService brandService;

    @GetMapping("/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable("id") Integer categoryId) {
        List<Brand> brands = brandService.findByCategory(categoryId);
        return new Result<>(true, StatusCode.OK, "根据分类查询品牌成功", brands);
    }

    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK, "查询品牌集合成功!!!", brandList);
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        //int a = 10 / 0;
        Brand brand = brandService.findById(id);
        if (brand == null) {
            return new Result(false, StatusCode.ERROR, "该品牌不存在");
        }
        return new Result<Brand>(true, StatusCode.OK, "根据id查询品牌成功!!!");
    }

    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "增加品牌成功!!!");
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改品牌成功!!!");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除品牌成功!!!");
    }

    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand) {
        List<Brand> list = brandService.findList(brand);
        if (list == null && list.size() < 0) {
            return new Result(false, StatusCode.ERROR, "该品牌不存在");
        }
        return new Result<List<Brand>>(true, StatusCode.OK, "条件查询品牌成功!!!", list);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<List<Brand>> findPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        if (pageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<List<Brand>>(true, StatusCode.OK, "分页查询品牌成功!!!", pageInfo);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Brand>> findPage( @RequestBody(required = false) Brand brand,@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        if (pageInfo == null) {
            return new Result(false, StatusCode.ERROR, "查询不存在");
        }
        return new Result<List<Brand>>(true, StatusCode.OK, "分页查询品牌成功!!!", pageInfo);
    }


}
