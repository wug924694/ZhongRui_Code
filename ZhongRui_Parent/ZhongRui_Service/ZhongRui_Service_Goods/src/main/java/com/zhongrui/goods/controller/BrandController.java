package com.zhongrui.goods.controller;

import com.github.pagehelper.PageInfo;
import com.zhongrui.entity.Result;
import com.zhongrui.entity.StatusCode;
import com.zhongrui.goods.pojo.Brand;
import com.zhongrui.goods.service.BrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/brand")
@CrossOrigin  //同意跨域访问
public class BrandController {

    //注入service
    @Resource
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAll(){
        /*System.out.println("睡觉了:"+Thread.currentThread().getId());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("睡醒了:"+Thread.currentThread().getId());*/
        List<Brand> list = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询品牌集合成功!!!",list);
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable("id")Integer id){
        //int i = 10 /0;
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true, StatusCode.OK,"根据id查询品牌成功!!!",brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result<Brand>(true, StatusCode.OK,"增加品牌成功!!!");
    }

    @PostMapping("/{id}")
    public Result update(@PathVariable("id")Integer id,@RequestBody Brand brand){
        brand.setId(id);
        brandService.update(brand);
        return new Result<Brand>(true, StatusCode.OK,"修改品牌成功!!!");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        brandService.delete(id);
        return new Result<Brand>(true, StatusCode.OK,"删除品牌成功!!!");
    }

    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> list = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"条件查询品牌集合成功!!!",list);
    }

    @PostMapping("/search/{page}/{size}")
    public Result<List<Brand>> findPage(@PathVariable("page")Integer page,@PathVariable Integer size,@RequestBody Brand brand){
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page,size);
        return new Result<List<Brand>>(true, StatusCode.OK,"分页查询成功!!!",pageInfo);
    }

}
